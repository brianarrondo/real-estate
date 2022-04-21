package com.realstate.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.entities.Lease;
import com.realstate.entities.Payment;
import com.realstate.entities.RentalBill;
import com.realstate.exceptions.AmountPaymentHigherThanRentalBillException;
import com.realstate.exceptions.AmountToPaidIsZeroException;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.exceptions.InvalidParametersException;
import com.realstate.exceptions.LeaseIsNotActiveException;
import com.realstate.exceptions.RentalBillDateIsOutOfLeaseDateException;
import com.realstate.exceptions.RentalBillHasAlreadyBeenPaidException;
import com.realstate.exceptions.ThereIsAlreadyARentalBillInMonthException;
import com.realstate.repositories.RentalBillRepository;
import com.realstate.utils.Utils;

@Service
public class RentalBillService {
	
	@Autowired
	private RentalBillRepository rentalBillRepository;
	@Autowired
	private LeaseService leaseService;
	@Autowired
	private PaymentService paymentService;
	
	public RentalBill getNew(Lease lease, Date date, float amount) throws EntityNotFoundException {
		RentalBill newRentalBill = new RentalBill(0, lease, date, amount);
		return insert(newRentalBill);
	}
	
	public RentalBill findById(long rentalBillId) throws EntityNotFoundException {
		Optional<RentalBill> optionalBill = rentalBillRepository.findById(rentalBillId);
		if (optionalBill.isPresent()) {
			return optionalBill.get();
		} else {
			throw new EntityNotFoundException("La factura con el ID especificado no existe.");
		}
	}
	
	public List<RentalBill> findAll() {
		return rentalBillRepository.findAll();
	}
	
	public boolean existsById(long rentalBillId) {
		return rentalBillRepository.existsById(rentalBillId);
	}
	
	public RentalBill insert(RentalBill newRentalBill) throws EntityNotFoundException {
		if (!leaseService.existsById(newRentalBill.getLease().getId())) {
			throw new EntityNotFoundException("El contrato de alquiler con el ID especificado no existe.");
		}
		return rentalBillRepository.save(newRentalBill);
	}
	
	public List<RentalBill> insertAll(List<RentalBill> rentalBillList) {
		return rentalBillRepository.saveAll(rentalBillList);
	}
	
	public RentalBill update(RentalBill rentalBill) throws EntityNotFoundException {
		if (!rentalBillRepository.existsById(rentalBill.getId())) {
			throw new EntityNotFoundException("La factura con el ID especificado no existe.");
		}
		return rentalBillRepository.save(rentalBill);
	}
	
	public void delete(RentalBill rentalBill) throws EntityNotFoundException {
		if (!rentalBillRepository.existsById(rentalBill.getId())) {
			throw new EntityNotFoundException("La factura con el ID especificado no existe.");
		}
		rentalBillRepository.delete(rentalBill);
	}
	
	public float totalPaid(RentalBill rentalBill) {
		List<Payment> payments = rentalBill.getPayments();
		float totalPaid = 0;
		for (Iterator<Payment> iterator = payments.iterator(); iterator.hasNext();) {
			Payment payment = (Payment) iterator.next();
			totalPaid += payment.getAmount();
		}
		return totalPaid;
	}
	
	public boolean rentalBillIsPaid(RentalBill rentalBill) {
		return totalPaid(rentalBill) >= rentalBill.getAmount();
	}
		
	public boolean existRentalBillInMonth(long leaseId, Date dateNewRentalBill) {
		List<RentalBill> rentalBillsForLease = rentalBillRepository.findAllByLeaseId(leaseId);
		for (Iterator<RentalBill> iterator = rentalBillsForLease.iterator(); iterator.hasNext();) {
			RentalBill rentalBill = (RentalBill) iterator.next();
			if (Utils.sameMonthAndYear(dateNewRentalBill, rentalBill.getDate())) {
				return true;
			}
		}
		return false;
	}
	
	public RentalBill generateRentalBill(long leaseId, Date date, float amount) throws LeaseIsNotActiveException, ThereIsAlreadyARentalBillInMonthException, InvalidParametersException, RentalBillDateIsOutOfLeaseDateException, EntityNotFoundException {
		if (leaseId == 0 || date == null) { throw new InvalidParametersException("Parametros invalidos"); }
		Lease lease = leaseService.findById(leaseId);
		if (lease.getStartDate().compareTo(date) > 0 || lease.getEndDate().compareTo(date) < 0) { throw new RentalBillDateIsOutOfLeaseDateException(); }
		if (!lease.isActive()) { throw new LeaseIsNotActiveException(); }
		if (existRentalBillInMonth(leaseId, date)) { throw new ThereIsAlreadyARentalBillInMonthException(); }
		RentalBill rentalBill = getNew(lease, date, amount);
		return rentalBill;
	}
	
	public Payment generatePayment(long rentalBillId, float amountToPay, Date date) throws AmountPaymentHigherThanRentalBillException, AmountToPaidIsZeroException, RentalBillHasAlreadyBeenPaidException, InvalidParametersException, EntityNotFoundException {
		if (rentalBillId == 0 || date == null) { throw new InvalidParametersException("Parametros invalidos"); }
		RentalBill rentalBill = findById(rentalBillId);
		
		if(rentalBillIsPaid(rentalBill)) {
			throw new RentalBillHasAlreadyBeenPaidException();
		} else if ((amountToPay > rentalBill.getAmount()) || (totalPaid(rentalBill) + amountToPay > rentalBill.getAmount())) {
			throw new AmountPaymentHigherThanRentalBillException();
		} else if (rentalBill.getAmount() == 0) {
			throw new AmountToPaidIsZeroException();
		}
		
		Payment newPayment = paymentService.getNew(amountToPay, rentalBill, date);
		rentalBill.getPayments().add(newPayment);
		update(rentalBill);
		return newPayment;
	}
}
