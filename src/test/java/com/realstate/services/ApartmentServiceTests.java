package com.realstate.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.realstate.domains.Apartment;
import com.realstate.domains.Estate;

@SpringBootTest
class ApartmentServiceTests {
	
	@Autowired
	private ApartmentService apartmentService;

	@Test
	void test_apartment_creation() {
		
		Estate estate = new Estate(null, "Propiedad 1", "Calle falsa 123", "Propiedad muy alta");
	
		int rooms = 5;
		String name = "Departamento 1";
		String description = "Departamento amplio";
		Apartment newApartment = apartmentService.getNew(estate, rooms, name, description);

		Optional<Apartment> optionalApartment = apartmentService.findById(newApartment.getApartamentId());
		assertEquals(newApartment, optionalApartment.get());
	}

}
