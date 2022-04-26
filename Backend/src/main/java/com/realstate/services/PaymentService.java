package com.realstate.services;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.entities.Payment;
import com.realstate.entities.RentalBill;
import com.realstate.entities.User;
import com.realstate.exceptions.PaymentDoesNotExistException;
import com.realstate.repositories.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private RentalBillService rentalBillService;
	
	public Payment getNew(float amount, RentalBill rentalBill, User user, Date date) throws EntityNotFoundException {
		Payment newPayment = new Payment(0, amount, rentalBill, user, date);
		return insert(newPayment);
	}
		
	public Payment findById(long paymentId) throws PaymentDoesNotExistException {
		Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
		if (optionalPayment.isPresent()) {
			return optionalPayment.get();
		} else {
			throw new PaymentDoesNotExistException();
		}
	}
	
	public boolean existsById(long paymentId) {
		return paymentRepository.existsById(paymentId);
	}
	
	public Payment insert(Payment payment) throws EntityNotFoundException {
		if (!rentalBillService.existsById(payment.getRentalBill().getId())) {
			throw new EntityNotFoundException("La factura con el ID especificado no existe.");
		}
		return paymentRepository.save(payment);
	}
	
	public void delete(Payment payment) throws PaymentDoesNotExistException {
		if (!paymentRepository.existsById(payment.getId())) {
			throw new PaymentDoesNotExistException();
		}
		paymentRepository.delete(payment);
	}
}
