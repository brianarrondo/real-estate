package com.realstate.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.realstate.domains.RentalBill;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.TenantDoesNotExistException;

@SpringBootTest
class RentalBillServiceTests {
	
	@Autowired
	private RentalBillService rentalBillService;
	
	@Test
	void exceptionIsThrownWhenRentalBillCreatingWithoutLeaseTest() {
		Date date = new Date();
		String leaseId = (new ObjectId()).toHexString();
		float amount = 10500;
		Exception exception = assertThrows(LeaseDoesNotExistException.class, () -> {
			rentalBillService.getNew(leaseId, date, amount);
		});
		
		assertEquals(exception.getClass(), LeaseDoesNotExistException.class);
	}

}
