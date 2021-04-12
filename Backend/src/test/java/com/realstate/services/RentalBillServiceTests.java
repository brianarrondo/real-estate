package com.realstate.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.realstate.entities.Lease;
import com.realstate.entities.Payment;
import com.realstate.entities.RentalBill;
import com.realstate.exceptions.AmountPaymentHigherThanRentalBillException;
import com.realstate.exceptions.AmountToPaidIsZeroException;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.exceptions.EstateDoesNotExistException;
import com.realstate.exceptions.InvalidParametersException;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.LeaseIsNotActiveException;
import com.realstate.exceptions.PaymentDoesNotExistException;
import com.realstate.exceptions.RentalBillDateIsOutOfLeaseDateException;
import com.realstate.exceptions.RentalBillDoesNotExistException;
import com.realstate.exceptions.RentalBillHasAlreadyBeenPaidException;
import com.realstate.exceptions.TenantDoesNotExistException;
import com.realstate.exceptions.ThereIsAlreadyARentalBillInMonthException;

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
	void rentalBillCreationTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, LeaseDoesNotExistException, RentalBillDoesNotExistException, LeaseIsNotActiveException, EstateDoesNotExistException, ThereIsAlreadyARentalBillInMonthException, InvalidParametersException, RentalBillDateIsOutOfLeaseDateException {
		Lease lease =  getValidLease();
		Date date = new Date();
		float amount = 10.500f;
		RentalBill newRentalBill = rentalBillService.generateRentalBill(lease.getLeaseId(), date, amount);
		
		RentalBill rentallBillFromDb = rentalBillService.findById(newRentalBill.getRentalBillId());
		assertEquals(rentallBillFromDb.getAmount(), amount);
		assertEquals(rentallBillFromDb.getDate(), date);
		assertEquals(rentallBillFromDb.getLeaseId(), lease.getLeaseId());
		assertEquals(rentallBillFromDb.getPayments().size(), 0);
		
		leaseService.delete(lease);
		estateService.delete(lease.getApartment().getEstate());
		apartmentService.delete(lease.getApartment());
		tenantService.delete(lease.getTenant());
		rentalBillService.delete(newRentalBill);
	}
	
	@Test
	void rentalBillIsNotCreatedWhenAlreadyExistsInMonthTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, LeaseDoesNotExistException, RentalBillDoesNotExistException, LeaseIsNotActiveException, EstateDoesNotExistException, ThereIsAlreadyARentalBillInMonthException, InvalidParametersException, RentalBillDateIsOutOfLeaseDateException {
		Lease lease =  getValidLease();
		String leaseId = lease.getLeaseId();
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		float amount = 10.500f;
		
		RentalBill newRentalBill = rentalBillService.generateRentalBill(leaseId, calendar.getTime(), amount);
		calendar.set(Calendar.DAY_OF_MONTH, 15);
		
		Exception exception = assertThrows(ThereIsAlreadyARentalBillInMonthException.class, () -> {
			rentalBillService.generateRentalBill(leaseId, calendar.getTime(), amount);
		});
		
		List<RentalBill> rentalBillsForLease = rentalBillRepository.findAllByLeaseId(leaseId);
		assertEquals(rentalBillsForLease.size(), 1);
		assertEquals(rentalBillsForLease.get(0), newRentalBill);
		assertEquals(exception.getClass(), ThereIsAlreadyARentalBillInMonthException.class);
		
		leaseService.delete(lease);
		estateService.delete(lease.getApartment().getEstate());
		apartmentService.delete(lease.getApartment());
		tenantService.delete(lease.getTenant());
		rentalBillService.delete(newRentalBill);
	}
	
	@Test
	void twoRentalBillsWithDifferentMonthsAreCreatedTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, LeaseDoesNotExistException, RentalBillDoesNotExistException, LeaseIsNotActiveException, EstateDoesNotExistException, ThereIsAlreadyARentalBillInMonthException, InvalidParametersException, RentalBillDateIsOutOfLeaseDateException {
		Lease lease =  getValidLease();
		String leaseId = lease.getLeaseId();
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		float amount = 10.500f;
		
		RentalBill newRentalBill1 = rentalBillService.generateRentalBill(leaseId, calendar.getTime(), amount);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		RentalBill newRentalBill2 = rentalBillService.generateRentalBill(leaseId, calendar.getTime(), amount);
		
		List<RentalBill> rentalBillsForLease = rentalBillRepository.findAllByLeaseId(leaseId);
		assertEquals(rentalBillsForLease.size(), 2);
		assertEquals(rentalBillsForLease.get(0), newRentalBill1);
		assertEquals(rentalBillsForLease.get(1), newRentalBill2);
		
		leaseService.delete(lease);
		estateService.delete(lease.getApartment().getEstate());
		apartmentService.delete(lease.getApartment());
		tenantService.delete(lease.getTenant());
		rentalBillService.delete(newRentalBill1);
		rentalBillService.delete(newRentalBill2);
	}
	
	@Test
	void payARentalBillWithOnePaymentTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, LeaseDoesNotExistException, LeaseIsNotActiveException, RentalBillDoesNotExistException, AmountPaymentHigherThanRentalBillException, AmountToPaidIsZeroException, RentalBillHasAlreadyBeenPaidException, PaymentDoesNotExistException, EstateDoesNotExistException, ThereIsAlreadyARentalBillInMonthException, InvalidParametersException, RentalBillDateIsOutOfLeaseDateException {
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
	
	@Test
	void payARentalBillWithTwoPaymentTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, LeaseDoesNotExistException, LeaseIsNotActiveException, RentalBillDoesNotExistException, AmountPaymentHigherThanRentalBillException, AmountToPaidIsZeroException, RentalBillHasAlreadyBeenPaidException, PaymentDoesNotExistException, EstateDoesNotExistException, ThereIsAlreadyARentalBillInMonthException, InvalidParametersException, RentalBillDateIsOutOfLeaseDateException {
		Lease lease = getValidLease();
		float rentalBillAmount = 10000f;
		RentalBill rentalBill = setRentalBill(lease, rentalBillAmount);
		
		Date date = new Date();
		Payment newPayment1 = rentalBillService.generatePayment(rentalBill.getRentalBillId(), 5000f, date);
		Payment newPayment2 = rentalBillService.generatePayment(rentalBill.getRentalBillId(), 5000f, date);
		
		rentalBill = rentalBillService.findById(newPayment1.getRentalBillId());
		
		assertTrue(rentalBillService.rentalBillIsPaid(rentalBill));
		assertEquals(rentalBill.getPayments().size(), 2);
		assertEquals(rentalBill.getPayments().get(0), newPayment1);
		assertEquals(rentalBill.getPayments().get(1), newPayment2);
		
		Payment paymentFromDb1 = paymentService.findById(newPayment1.getPaymentId());
		assertEquals(paymentFromDb1.getDate(), date);
		assertEquals(paymentFromDb1.getAmount(), 5000f);
		assertEquals(paymentFromDb1.getRentalBillId(), rentalBill.getRentalBillId());
		
		Payment paymentFromDb2 = paymentService.findById(newPayment2.getPaymentId());
		assertEquals(paymentFromDb2.getDate(), date);
		assertEquals(paymentFromDb2.getAmount(), 5000f);
		assertEquals(paymentFromDb2.getRentalBillId(), rentalBill.getRentalBillId());
		
		leaseService.delete(lease);
		estateService.delete(lease.getApartment().getEstate());
		apartmentService.delete(lease.getApartment());
		tenantService.delete(lease.getTenant());
		rentalBillService.delete(rentalBill);
		paymentService.delete(newPayment1);
		paymentService.delete(newPayment2);
	}
	
	@Test
	void exceptionIsThrownWhenPaymentAmountIsHighThanRentalBillAmountTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, LeaseDoesNotExistException, LeaseIsNotActiveException, RentalBillDoesNotExistException, AmountPaymentHigherThanRentalBillException, AmountToPaidIsZeroException, RentalBillHasAlreadyBeenPaidException, PaymentDoesNotExistException, EstateDoesNotExistException, ThereIsAlreadyARentalBillInMonthException, InvalidParametersException, RentalBillDateIsOutOfLeaseDateException {
		Lease lease = getValidLease();
		float rentalBillAmount = 10000f;
		RentalBill rentalBill = setRentalBill(lease, rentalBillAmount);
		String rentalBillId = rentalBill.getRentalBillId();
		
		Date date = new Date();
		Payment newPayment1 = rentalBillService.generatePayment(rentalBill.getRentalBillId(), rentalBillAmount / 2, date);
		Exception exception = assertThrows(AmountPaymentHigherThanRentalBillException.class, () -> {
			rentalBillService.generatePayment(rentalBillId, (rentalBillAmount / 2) + 1, date);
		});
		
		rentalBill = rentalBillService.findById(newPayment1.getRentalBillId());
		
		assertFalse(rentalBillService.rentalBillIsPaid(rentalBill));
		assertEquals(rentalBill.getPayments().size(), 1);
		assertEquals(rentalBill.getPayments().get(0), newPayment1);
		
		Payment paymentFromDb1 = paymentService.findById(newPayment1.getPaymentId());
		assertEquals(paymentFromDb1.getDate(), date);
		assertEquals(paymentFromDb1.getAmount(), rentalBillAmount / 2);
		assertEquals(paymentFromDb1.getRentalBillId(), rentalBill.getRentalBillId());
		assertEquals(exception.getClass(), AmountPaymentHigherThanRentalBillException.class);
		
		leaseService.delete(lease);
		estateService.delete(lease.getApartment().getEstate());
		apartmentService.delete(lease.getApartment());
		tenantService.delete(lease.getTenant());
		rentalBillService.delete(rentalBill);
		paymentService.delete(newPayment1);
	}
}
