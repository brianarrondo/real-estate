package com.realstate.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.realstate.domains.RentalBill;

@SpringBootTest
class RentalBillTests {
	
	@Autowired
	private RentalBillService rentalBillService;
	
	@Test
	void test_rentall_bill_creation() {
		Date date = new Date();
		String leaseId = "1";
		float amount = 10500;
		RentalBill newRentalBill = rentalBillService.getNew(leaseId, date, amount);
		
		Optional<RentalBill> optionalRentalBill = rentalBillService.findById(newRentalBill.getRentalBillId());
		assertEquals(newRentalBill, optionalRentalBill.get());
	}

}
