package com.realstate.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.realstate.domains.Estate;

@SpringBootTest
public class EstateServiceTests {
	
	@Autowired
	private EstateService estateService;

	@Test
	void test_estate_creation() {
		String name = "propiedad 1";
		String address = "Calle falsa 123";
		String description = "Casa muy amplia";
		Estate newEstate = estateService.getNew(name, address, description);
		
		Optional<Estate> optionalEstate = estateService.findById(newEstate.getEstateId());
		assertEquals(newEstate, optionalEstate.get());
	}
}
