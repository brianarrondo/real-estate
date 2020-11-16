package com.realstate.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.realstate.domains.Apartment;
import com.realstate.domains.Estate;
import com.realstate.domains.Lease;
import com.realstate.domains.Tenant;

class LeaseServiceTests {
	
	@Autowired
	private LeaseService leaseService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private EstateService estateService;
	@Autowired
	private ApartmentService apartmentService;

	@Test
	void test_lease_creation() {
		Tenant tenant = tenantService.getNew("John Connor", "35111222", "4444-5555", "Altura 1.80 - Peso 80Kg - Edad: 50");
		Estate estate = estateService.getNew("Propiedad 1", "Calle Falsa 123 - Localidad San Martin", "Propiedad amplia con patio");
		Apartment apartment = apartmentService.getNew(estate, 3, "Departamento 1", "Departamento con baño, dormitorio y cocina. Muy pequeño");
	}

}
