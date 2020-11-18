package com.realstate.services;

import java.util.Date;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.Payment;
import com.realstate.exceptions.PaymentDoesNotExistException;
import com.realstate.exceptions.RentalBillDoesNotExistException;
import com.realstate.repositories.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private RentalBillService rentalBillService;
	
	public Payment getNew(float amount, String rentalBillId, Date date) throws RentalBillDoesNotExistException {
		Payment newPayment = new Payment(null, amount, rentalBillId, date);
		return insert(newPayment);
	}
		
	public Payment findById(String paymentId) throws PaymentDoesNotExistException {
		Optional<Payment> optionalPayment = paymentRepository.findById(new ObjectId(paymentId));
		if (optionalPayment.isPresent()) {
			return optionalPayment.get();
		} else {
			throw new PaymentDoesNotExistException();
		}
	}
	
	public boolean existsById(String paymentId) {
		return paymentRepository.existsById(new ObjectId(paymentId));
	}
	
	public Payment insert(Payment payment) throws RentalBillDoesNotExistException {
		if (!rentalBillService.existsById(payment.getRentalBillId())) {
			throw new RentalBillDoesNotExistException();
		}
		return paymentRepository.insert(payment);
	}
	
	public void delete(Payment payment) throws PaymentDoesNotExistException {
		if (!paymentRepository.existsById(new ObjectId(payment.getPaymentId()))) {
			throw new PaymentDoesNotExistException();
		}
		paymentRepository.delete(payment);
	}
}
