package com.realstate.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.realstate.entities.Apartment;
import com.realstate.entities.Estate;
import com.realstate.entities.Lease;
import com.realstate.entities.Tenant;
import com.realstate.exceptions.InvalidParametersException;

@SpringBootTest
class LeaseServiceTests extends BaseServiceTests {
	/*
	@Test
	void leaseCreationTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, EstateDoesNotExistException, LeaseDoesNotExistException, InvalidParametersException {
		
		Tenant tenant = tenantService.create("John Connor", "35111222", "4444-5555", "Altura 1.80 - Peso 80Kg - Edad: 50");
		Estate estate = estateService.getNew("Propiedad 1", "Calle Falsa 123 - Localidad San Martin", "Propiedad amplia con patio");
		Apartment apartment = apartmentService.getNew(estate.getEstateId(), 3, "Departamento 1", "Departamento con baño, dormitorio y cocina. Muy pequeño");
		
		Date startDate = new Date();
		Date endDate = new Date();
		String desc = "Descripcion de contrato";
		Lease newLease = leaseService.create(tenant.getTenantId(), apartment.getApartmentId(), startDate, endDate, true, desc);
		
		Optional<Lease> optionalLeaseFromDb = leaseRepository.findById(new ObjectId(newLease.getLeaseId()));
		assertTrue(optionalLeaseFromDb.isPresent());
		Lease leaseFromDb = optionalLeaseFromDb.get();
		
		assertEquals(leaseFromDb.getTenants(), tenant);
		assertEquals(leaseFromDb.getApartment(), apartment);
		assertEquals(leaseFromDb.getStartDate(), startDate);
		assertEquals(leaseFromDb.getEndDate(), endDate);
		assertEquals(leaseFromDb.getRentalFees().size(), 0);
		assertTrue(leaseFromDb.isActive());
		assertEquals(leaseFromDb.getDescription(), desc);
		
		tenantService.delete(tenant);
		estateService.delete(estate);
		apartmentService.delete(apartment);
		leaseService.delete(newLease);
		
	}
	
	@Test
	void exceptionIsThrownWhenLeaseCreatingWithoutTenantTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, EstateDoesNotExistException {
		
		Tenant tenant = tenantService.create("John Connor", "35111222", "4444-5555", "Altura 1.80 - Peso 80Kg - Edad: 50");
		Estate estate = estateService.getNew("Propiedad 1", "Calle Falsa 123 - Localidad San Martin", "Propiedad amplia con patio");
		Apartment apartment = apartmentService.getNew(estate.getEstateId(), 3, "Departamento 1", "Departamento con baño, dormitorio y cocina. Muy pequeño");
		tenantService.delete(tenant);
		
		Date startDate = new Date();
		Date endDate = new Date();
		String desc = "Descripcion de contrato";
		Exception exception = assertThrows(TenantDoesNotExistException.class, () -> {
			leaseService.create(tenant.getTenantId(), apartment.getApartmentId(), startDate, endDate, true, desc);
		});
		
		assertEquals(exception.getClass(), TenantDoesNotExistException.class);
		
		estateService.delete(estate);
		apartmentService.delete(apartment);
	}
	
	@Test
	void exceptionIsThrownWhenLeaseCreatingWithoutApartmentTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException, EstateDoesNotExistException {
		
		Tenant tenant = tenantService.create("John Connor", "35111222", "4444-5555", "Altura 1.80 - Peso 80Kg - Edad: 50");
		Estate estate = estateService.getNew("Propiedad 1", "Calle Falsa 123 - Localidad San Martin", "Propiedad amplia con patio");
		Apartment apartment = apartmentService.getNew(estate.getEstateId(), 3, "Departamento 1", "Departamento con baño, dormitorio y cocina. Muy pequeño");
		apartmentService.delete(apartment);
		
		Date startDate = new Date();
		Date endDate = new Date();
		String desc = "Descripcion de contrato";
		Exception exception = assertThrows(ApartmentDoesNotExistException.class, () -> {
			leaseService.create(tenant.getTenantId(), apartment.getApartmentId(), startDate, endDate, true, desc);
		});
		
		assertEquals(exception.getClass(), ApartmentDoesNotExistException.class);
		
		estateService.delete(estate);
		tenantService.delete(tenant);
	}*/
}
