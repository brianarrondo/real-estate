package com.realstate.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realstate.dto.payment.PaymentDto;
import com.realstate.entities.Lease;
import com.realstate.entities.Payment;
import com.realstate.entities.RentalBill;
import com.realstate.entities.User;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.exceptions.InvalidParametersException;
import com.realstate.exceptions.RealEstateException;
import com.realstate.exceptions.RentalBillPaymentException;
import com.realstate.repositories.RentalBillRepository;
import com.realstate.repositories.UserRepository;
import com.realstate.utils.Utils;

@Transactional
@Service
public class RentalBillService {
	
	@Autowired
	private RentalBillRepository rentalBillRepository;
	@Autowired
	private LeaseService leaseService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private UserRepository userRepo;
	@Autowired
    private ModelMapper modelMapper;
	
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
	
	private float totalPaid(RentalBill rentalBill) {
		List<Payment> payments = rentalBill.getPayments();
		float totalPaid = 0;
		for (Iterator<Payment> iterator = payments.iterator(); iterator.hasNext();) {
			Payment payment = (Payment) iterator.next();
			totalPaid += payment.getAmount();
		}
		return totalPaid;
	}
	
	private boolean rentalBillIsPaid(RentalBill rentalBill) {
		return totalPaid(rentalBill) >= rentalBill.getAmount();
	}
		
	private boolean existRentalBillInMonth(long leaseId, Date dateNewRentalBill) {
		List<RentalBill> rentalBillsForLease = rentalBillRepository.findAllByLeaseId(leaseId);
		return rentalBillsForLease.stream().anyMatch(r -> Utils.sameMonthAndYear(dateNewRentalBill, r.getDate()));
	}
	
	private RentalBill getRentalBillForMonth(long leaseId, Date date) throws EntityNotFoundException {
		List<RentalBill> rentalBillsForLease = rentalBillRepository.findAllByLeaseId(leaseId);
		RentalBill rentalBill = rentalBillsForLease.stream().filter(r -> Utils.sameMonthAndYear(date, r.getDate()))
				.findAny().orElse(null);
		if (rentalBill != null) {
			return rentalBill;
		} else {
			throw new EntityNotFoundException("La factura con el ID especificado no existe.");
		}
	}
	
	public RentalBill generateRentalBill(long leaseId, Date date, float amount) throws InvalidParametersException, EntityNotFoundException, RentalBillPaymentException, RealEstateException {
		if (leaseId == 0 || date == null) { throw new InvalidParametersException("Parametros invalidos"); }
		Lease lease = leaseService.findById(leaseId);
		if (lease.getStartDate().compareTo(date) > 0 || lease.getEndDate().compareTo(date) < 0) { throw new RentalBillPaymentException("La fecha de la factura esta por fuera de las fechas del contrato."); }
		if (!lease.isActive()) { throw new RealEstateException("El contrato de alquiler no se encuentra activo."); }
		if (existRentalBillInMonth(leaseId, date)) { throw new RentalBillPaymentException("Ya existe una factura en el mes"); }
		RentalBill rentalBill = getNew(lease, date, amount);
		return rentalBill;
	}
	
	public PaymentDto generatePayment(long leaseId, float amountToPay, Date date, long userId) throws InvalidParametersException, EntityNotFoundException, RentalBillPaymentException, RealEstateException {
		if (date == null || amountToPay <= 0 || leaseId <= 0 || userId <= 0) { throw new InvalidParametersException("Parametros invalidos"); }
		leaseService.findById(leaseId);
		User user = userRepo.findById(userId).orElse(null);

		RentalBill rentalBill;
		if (!existRentalBillInMonth(leaseId, date)) {
			rentalBill = generateRentalBill(leaseId, date, 5000);
		} else {
			rentalBill = getRentalBillForMonth(leaseId, date);
		}
		
		if(rentalBillIsPaid(rentalBill)) {
			throw new RentalBillPaymentException("La factura ya fue pagada.");
		} else if ((amountToPay > rentalBill.getAmount()) || (totalPaid(rentalBill) + amountToPay > rentalBill.getAmount())) {
			throw new RentalBillPaymentException("El monto a pagar es superior al de la factura.");
		} else if (rentalBill.getAmount() == 0) {
			throw new RentalBillPaymentException("El monto a pagar es 0.");
		}

		Payment newPayment = paymentService.getNew(amountToPay, rentalBill, user, date);
		return modelMapper.map(paymentService.insert(newPayment), PaymentDto.class);
	}
}
