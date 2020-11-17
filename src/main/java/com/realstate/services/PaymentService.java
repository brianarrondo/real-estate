package com.realstate.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.Payment;
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
	
	public Payment insert(Payment payment) throws RentalBillDoesNotExistException {
		if (!rentalBillService.existsById(payment.getRentalBillId())) {
			throw new RentalBillDoesNotExistException();
		}
		return paymentRepository.insert(payment);
	}
}
