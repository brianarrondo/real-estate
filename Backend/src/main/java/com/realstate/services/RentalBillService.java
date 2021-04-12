package com.realstate.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.entities.Lease;
import com.realstate.entities.Payment;
import com.realstate.entities.RentalBill;
import com.realstate.exceptions.AmountPaymentHigherThanRentalBillException;
import com.realstate.exceptions.AmountToPaidIsZeroException;
import com.realstate.exceptions.InvalidParametersException;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.LeaseIsNotActiveException;
import com.realstate.exceptions.RentalBillDateIsOutOfLeaseDateException;
import com.realstate.exceptions.RentalBillDoesNotExistException;
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
	
	public RentalBill getNew(String leaseId, Date date, float amount) throws LeaseDoesNotExistException {
		RentalBill newRentalBill = new RentalBill(null, leaseId, date, amount);
		return insert(newRentalBill);
	}
	
	public RentalBill findById(String rentalBillId) throws RentalBillDoesNotExistException {
		Optional<RentalBill> optionalBill = rentalBillRepository.findById(new ObjectId(rentalBillId));
		if (optionalBill.isPresent()) {
			return optionalBill.get();
		} else {
			throw new RentalBillDoesNotExistException();
		}
	}
	
	public List<RentalBill> findAll() {
		return rentalBillRepository.findAll();
	}
	
	public boolean existsById(String rentalBillId) {
		return rentalBillRepository.existsById(new ObjectId(rentalBillId));
	}
	
	public RentalBill insert(RentalBill newRentalBill) throws LeaseDoesNotExistException {
		if (!leaseService.existsById(newRentalBill.getLeaseId())) {
			throw new LeaseDoesNotExistException();
		}
		return rentalBillRepository.insert(newRentalBill);
	}
	
	public List<RentalBill> insertAll(List<RentalBill> rentalBillList) {
		return rentalBillRepository.insert(rentalBillList);
	}
	
	public RentalBill update(RentalBill rentalBill) throws RentalBillDoesNotExistException {
		if (!rentalBillRepository.existsById(new ObjectId(rentalBill.getRentalBillId()))) {
			throw new RentalBillDoesNotExistException();
		}
		return rentalBillRepository.save(rentalBill);
	}
	
	public void delete(RentalBill rentalBill) throws RentalBillDoesNotExistException {
		if (!rentalBillRepository.existsById(new ObjectId(rentalBill.getRentalBillId()))) {
			throw new RentalBillDoesNotExistException();
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
		
	public boolean existRentalBillInMonth(String leaseId, Date dateNewRentalBill) {
		List<RentalBill> rentalBillsForLease = rentalBillRepository.findAllByLeaseId(leaseId);
		for (Iterator<RentalBill> iterator = rentalBillsForLease.iterator(); iterator.hasNext();) {
			RentalBill rentalBill = (RentalBill) iterator.next();
			if (Utils.sameMonthAndYear(dateNewRentalBill, rentalBill.getDate())) {
				return true;
			}
		}
		return false;
	}
	
	public RentalBill generateRentalBill(String leaseId, Date date, float amount) throws LeaseDoesNotExistException, LeaseIsNotActiveException, ThereIsAlreadyARentalBillInMonthException, InvalidParametersException, RentalBillDateIsOutOfLeaseDateException {
		if (leaseId == null || date == null) { throw new InvalidParametersException(); }
		Lease lease = leaseService.findById(leaseId);
		if (lease.getStartDate().compareTo(date) > 0 || lease.getEndDate().compareTo(date) < 0) { throw new RentalBillDateIsOutOfLeaseDateException(); }
		if (!lease.isActive()) { throw new LeaseIsNotActiveException(); }
		if (existRentalBillInMonth(leaseId, date)) { throw new ThereIsAlreadyARentalBillInMonthException(); }
		RentalBill rentalBill = getNew(leaseId, date, amount);
		return rentalBill;
	}
	
	public Payment generatePayment(String rentalBillId, float amountToPay, Date date) throws RentalBillDoesNotExistException, AmountPaymentHigherThanRentalBillException, AmountToPaidIsZeroException, RentalBillHasAlreadyBeenPaidException, LeaseDoesNotExistException, InvalidParametersException {
		if (rentalBillId == null || date == null) { throw new InvalidParametersException(); }
		RentalBill rentalBill = findById(rentalBillId);
		
		if(rentalBillIsPaid(rentalBill)) {
			throw new RentalBillHasAlreadyBeenPaidException();
		} else if ((amountToPay > rentalBill.getAmount()) || (totalPaid(rentalBill) + amountToPay > rentalBill.getAmount())) {
			throw new AmountPaymentHigherThanRentalBillException();
		} else if (rentalBill.getAmount() == 0) {
			throw new AmountToPaidIsZeroException();
		}
		
		Payment newPayment = paymentService.getNew(amountToPay, rentalBillId, date);
		rentalBill.getPayments().add(newPayment);
		update(rentalBill);
		return newPayment;
	}
}
