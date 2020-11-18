package com.realstate.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.realstate.domains.Apartment;
import com.realstate.domains.Estate;
import com.realstate.domains.Lease;
import com.realstate.domains.RentalBill;
import com.realstate.domains.Tenant;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.LeaseIsNotActiveException;
import com.realstate.exceptions.TenantDoesNotExistException;
import com.realstate.repositories.LeaseRepository;

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

	public Lease getValidLease() throws TenantDoesNotExistException, ApartmentDoesNotExistException {
		Tenant tenant = tenantService.getNew("John Connor", "35111222", "4444-5555", "Altura 1.80 - Peso 80Kg - Edad: 50");
		Estate estate = estateService.getNew("Propiedad 1", "Calle Falsa 123 - Localidad San Martin", "Propiedad amplia con patio");
		Apartment apartment = apartmentService.getNew(estate, 3, "Departamento 1", "Departamento con baño, dormitorio y cocina. Muy pequeño");
		
		Date startDate = new Date();
		Date endDate = new Date();
		String desc = "Descripcion de contrato";
		Lease newLease = leaseService.getNew(tenant, apartment, startDate, endDate, true, desc);
		
		return newLease;
	}
	
	public RentalBill setRentalBill(Lease lease, float amount) throws LeaseDoesNotExistException, LeaseIsNotActiveException {
		Date date = new Date();
		RentalBill newRentallBill = rentalBillService.generateRentalBill(lease.getLeaseId(), date, amount);
		return newRentallBill;
	}
}
