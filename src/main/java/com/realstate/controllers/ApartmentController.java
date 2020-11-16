package com.realstate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realstate.domains.Apartment;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.services.ApartmentService;

@RestController
@RequestMapping("apartment")
public class ApartmentController {
	
	@Autowired
	private ApartmentService apartmentService;
	
	@GetMapping("all")
	public ResponseEntity<List<Apartment>> findAll() {
		return ResponseEntity.ok(apartmentService.findAll());
	}
	
	@GetMapping(value = "{apartmentId}")
	public ResponseEntity<Apartment> findById(@PathVariable(value = "apartmentId") String apartmentId) {
		try {
			return ResponseEntity.ok(apartmentService.findById(apartmentId));
		} catch (ApartmentDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Apartment> insert(@RequestBody Apartment newApartment) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.insert(newApartment));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PutMapping
	public ResponseEntity<Apartment> update(@RequestBody Apartment apartment) {	
		try {
			return ResponseEntity.ok(apartmentService.update(apartment));
		} catch (ApartmentDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Apartment> delete(@RequestBody Apartment apartment) {
		try {
			apartmentService.delete(apartment);
			return ResponseEntity.ok().build();
		} catch (ApartmentDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
