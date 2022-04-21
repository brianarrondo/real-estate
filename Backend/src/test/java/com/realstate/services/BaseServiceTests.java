package com.realstate.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.realstate.entities.Apartment;
import com.realstate.entities.Estate;
import com.realstate.entities.Lease;
import com.realstate.entities.RentalBill;
import com.realstate.entities.Tenant;
import com.realstate.exceptions.InvalidParametersException;
import com.realstate.exceptions.LeaseIsNotActiveException;
import com.realstate.exceptions.RentalBillDateIsOutOfLeaseDateException;
import com.realstate.exceptions.ThereIsAlreadyARentalBillInMonthException;
import com.realstate.repositories.LeaseRepository;
import com.realstate.repositories.RentalBillRepository;

public class BaseServiceTests {
	
	@Autowired
	public LeaseService leaseService;
	@Autowired
	public TenantService tenantService;
	@Autowired
	public EstateService estateService;
	@Autowired
	public PaymentService paymentService;
	@Autowired
	public LeaseRepository leaseRepository;
	@Autowired
	public ApartmentService apartmentService;
	@Autowired
	public RentalBillService rentalBillService;
	@Autowired
	public RentalBillRepository rentalBillRepository;
/*
	public Lease getValidLease() throws TenantDoesNotExistException, ApartmentDoesNotExistException, InvalidParametersException {
		
		Tenant tenant = tenantService.create("John Connor", "35111222", "4444-5555", "Altura 1.80 - Peso 80Kg - Edad: 50");
		Estate estate = estateService.getNew("Propiedad 1", "Calle Falsa 123 - Localidad San Martin", "Propiedad amplia con patio");
		Apartment apartment = apartmentService.getNew(estate.getEstateId(), 3, "Departamento 1", "Departamento con baño, dormitorio y cocina. Muy pequeño");
		
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
		Date startDate = calendar.getTime();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 2);
		Date endDate = calendar.getTime();
		String desc = "Descripcion de contrato";
		Lease newLease = leaseService.create(tenant.getTenantId(), apartment.getApartmentId(), startDate, endDate, true, desc);
		
		return newLease;
		Lease newLease = leaseService.create();
	}
	
	public RentalBill setRentalBill(Lease lease, float amount) throws LeaseDoesNotExistException, LeaseIsNotActiveException, ThereIsAlreadyARentalBillInMonthException, InvalidParametersException, RentalBillDateIsOutOfLeaseDateException {
		Date date = new Date();
		RentalBill newRentallBill = rentalBillService.generateRentalBill(lease.getLeaseId(), date, amount);
		return newRentallBill;
	}*/
}
