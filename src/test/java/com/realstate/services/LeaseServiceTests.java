package com.realstate.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.realstate.domains.Apartment;
import com.realstate.domains.Estate;
import com.realstate.domains.Lease;
import com.realstate.domains.Tenant;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.exceptions.TenantDoesNotExistException;

@SpringBootTest
class LeaseServiceTests extends BaseServiceTests {
	
	@Test
	void leaseCreationTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException {
		Tenant tenant = tenantService.getNew("John Connor", "35111222", "4444-5555", "Altura 1.80 - Peso 80Kg - Edad: 50");
		Estate estate = estateService.getNew("Propiedad 1", "Calle Falsa 123 - Localidad San Martin", "Propiedad amplia con patio");
		Apartment apartment = apartmentService.getNew(estate, 3, "Departamento 1", "Departamento con baño, dormitorio y cocina. Muy pequeño");
		
		Date startDate = new Date();
		Date endDate = new Date();
		String desc = "Descripcion de contrato";
		Lease newLease = leaseService.getNew(tenant, apartment, startDate, endDate, true, desc);
		
		Optional<Lease> optionalLeaseFromDb = leaseRepository.findById(new ObjectId(newLease.getLeaseId()));
		assertTrue(optionalLeaseFromDb.isPresent());
		Lease leaseFromDb = optionalLeaseFromDb.get();
		
		assertEquals(leaseFromDb.getTenant(), tenant);
		assertEquals(leaseFromDb.getApartment(), apartment);
		assertEquals(leaseFromDb.getStartDate(), startDate);
		assertEquals(leaseFromDb.getEndDate(), endDate);
		assertEquals(leaseFromDb.getRentalFees().size(), 0);
		assertTrue(leaseFromDb.isActive());
		assertEquals(leaseFromDb.getDescription(), desc);
		
		try { tenantService.delete(tenant); } catch (Exception e) {}
		try { estateService.delete(estate); } catch (Exception e) {}
		try { apartmentService.delete(apartment); } catch (Exception e) {}
		try { leaseService.delete(newLease); } catch (Exception e) {}
		
	}
	
	@Test
	void exceptionIsThrownWhenLeaseCreatingWithoutTenantTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException {
		Tenant tenant = tenantService.getNew("John Connor", "35111222", "4444-5555", "Altura 1.80 - Peso 80Kg - Edad: 50");
		Estate estate = estateService.getNew("Propiedad 1", "Calle Falsa 123 - Localidad San Martin", "Propiedad amplia con patio");
		Apartment apartment = apartmentService.getNew(estate, 3, "Departamento 1", "Departamento con baño, dormitorio y cocina. Muy pequeño");
		try { tenantService.delete(tenant); } catch (Exception e) {}
		
		Date startDate = new Date();
		Date endDate = new Date();
		String desc = "Descripcion de contrato";
		Exception exception = assertThrows(TenantDoesNotExistException.class, () -> {
			leaseService.getNew(tenant, apartment, startDate, endDate, true, desc);
		});
		
		assertEquals(exception.getClass(), TenantDoesNotExistException.class);
		
		try { estateService.delete(estate); } catch (Exception e) {}
		try { apartmentService.delete(apartment); } catch (Exception e) {}
	}
	
	@Test
	void exceptionIsThrownWhenLeaseCreatingWithoutApartmentTest() throws TenantDoesNotExistException, ApartmentDoesNotExistException {
		Tenant tenant = tenantService.getNew("John Connor", "35111222", "4444-5555", "Altura 1.80 - Peso 80Kg - Edad: 50");
		Estate estate = estateService.getNew("Propiedad 1", "Calle Falsa 123 - Localidad San Martin", "Propiedad amplia con patio");
		Apartment apartment = apartmentService.getNew(estate, 3, "Departamento 1", "Departamento con baño, dormitorio y cocina. Muy pequeño");
		try { apartmentService.delete(apartment); } catch (Exception e) {}
		
		Date startDate = new Date();
		Date endDate = new Date();
		String desc = "Descripcion de contrato";
		Exception exception = assertThrows(ApartmentDoesNotExistException.class, () -> {
			leaseService.getNew(tenant, apartment, startDate, endDate, true, desc);
		});
		
		assertEquals(exception.getClass(), ApartmentDoesNotExistException.class);
		
		try { estateService.delete(estate); } catch (Exception e) {}
		try { tenantService.delete(tenant); } catch (Exception e) {}
	}
}
