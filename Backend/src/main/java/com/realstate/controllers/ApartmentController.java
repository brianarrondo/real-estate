package com.realstate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realstate.dto.ApartmentDto;
import com.realstate.entities.Apartment;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.exceptions.RealEstateException;
import com.realstate.services.ApartmentService;
import com.realstate.utils.Utils;

@RestController
@CrossOrigin(origins = "https://real-estate-adm-frontend.herokuapp.com", maxAge = 3600)
@RequestMapping("apartment")
public class ApartmentController {
	
	@Autowired
	private ApartmentService apartmentService;
	
	@GetMapping("all")
	public ResponseEntity<List<ApartmentDto>> findAll() {
		return ResponseEntity.ok(apartmentService.findAll());
	}
	
	@GetMapping("allWithoutLease")
	public ResponseEntity<List<ApartmentDto>> findAllWithoutLease() {
		return ResponseEntity.ok(apartmentService.findAllWithoutLease());
	}
	
	@GetMapping("allWithNoEstateAssigned")
	public ResponseEntity<List<ApartmentDto>> findAllNoEstateAssigned() {
		return ResponseEntity.ok(apartmentService.findAllNoEstateAssigned());
	}
	
	@GetMapping(value = "{apartmentId}")
	public ResponseEntity<?> findById(@PathVariable(value = "apartmentId") long apartmentId) {
		try {
			return ResponseEntity.ok(apartmentService.findById(apartmentId));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody Apartment newApartment) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.insert(newApartment));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody Apartment apartment) {	
		try {
			return ResponseEntity.ok(apartmentService.update(apartment));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());	
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody ApartmentDto apartment) {
		try {
			apartmentService.delete(apartment);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
