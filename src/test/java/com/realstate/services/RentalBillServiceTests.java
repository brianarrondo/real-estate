package com.realstate.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.realstate.domains.Lease;
import com.realstate.domains.Payment;
import com.realstate.domains.RentalBill;
import com.realstate.exceptions.AmountPaymentHigherThanRentalBillException;
import com.realstate.exceptions.AmountToPaidIsZeroException;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.exceptions.EstateDoesNotExistException;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.LeaseIsNotActiveException;
import com.realstate.exceptions.PaymentDoesNotExistException;
import com.realstate.exceptions.RentalBillDoesNotExistException;
import com.realstate.exceptions.RentalBillHasAlreadyBeenPaidException;
import com.realstate.exceptions.TenantDoesNotExistException;

@SpringBootTest
class RentalBillServiceTests extends BaseServiceTests {
		
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

	@Test
	void rentalBillCreationTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, LeaseDoesNotExistException, RentalBillDoesNotExistException, LeaseIsNotActiveException, EstateDoesNotExistException {
		Lease lease =  getValidLease();
		Date date = new Date();
		float amount = 10.500f;
		RentalBill newRentallBill = rentalBillService.generateRentalBill(lease.getLeaseId(), date, amount);
		
		RentalBill rentallBillFromDb = rentalBillService.findById(newRentallBill.getRentalBillId());
		assertEquals(rentallBillFromDb.getAmount(), amount);
		assertEquals(rentallBillFromDb.getDate(), date);
		assertEquals(rentallBillFromDb.getLeaseId(), lease.getLeaseId());
		assertEquals(rentallBillFromDb.getPayments().size(), 0);
		
		leaseService.delete(lease);
		estateService.delete(lease.getApartment().getEstate());
		apartmentService.delete(lease.getApartment());
		tenantService.delete(lease.getTenant());
	}
	
	@Test
	void payARentalBillWithOnePaymentTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, LeaseDoesNotExistException, LeaseIsNotActiveException, RentalBillDoesNotExistException, AmountPaymentHigherThanRentalBillException, AmountToPaidIsZeroException, RentalBillHasAlreadyBeenPaidException, PaymentDoesNotExistException, EstateDoesNotExistException {
		Lease lease = getValidLease();
		float rentalBillAmount = 10000f;
		RentalBill rentalBill = setRentalBill(lease, rentalBillAmount);
		
		float amountToPay = 10000f;
		Date date = new Date();
		Payment newPayment = rentalBillService.generatePayment(rentalBill.getRentalBillId(), amountToPay, date);
		
		rentalBill = rentalBillService.findById(newPayment.getRentalBillId());
		
		assertTrue(rentalBillService.rentalBillIsPaid(rentalBill));
		assertEquals(rentalBill.getPayments().size(), 1);
		assertEquals(rentalBill.getPayments().get(0), newPayment);
		
		Payment paymentFromDb = paymentService.findById(newPayment.getPaymentId());
		assertEquals(paymentFromDb.getDate(), date);
		assertEquals(paymentFromDb.getAmount(), amountToPay);
		assertEquals(paymentFromDb.getRentalBillId(), rentalBill.getRentalBillId());
		
		leaseService.delete(lease);
		estateService.delete(lease.getApartment().getEstate());
		apartmentService.delete(lease.getApartment());
		tenantService.delete(lease.getTenant());
		rentalBillService.delete(rentalBill);
		paymentService.delete(newPayment);
	}
}
