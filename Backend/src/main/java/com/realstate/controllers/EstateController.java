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

import com.realstate.entities.Estate;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.exceptions.EstateDoesNotExistException;
import com.realstate.services.EstateService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("estate")
public class EstateController {

	@Autowired
	private EstateService estateService;
	
	@GetMapping("all")
	public ResponseEntity<List<Estate>> findAll() {
		return ResponseEntity.ok(estateService.findAll());
	}
	
	@GetMapping(value = "{estateId}")
	public ResponseEntity<Estate> findById(@PathVariable("estateId") String estateId) {
		try {
			return ResponseEntity.ok(estateService.findById(estateId));
		} catch (EstateDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Estate> insert(@RequestBody Estate newEstate) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(estateService.createEstate(newEstate));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PutMapping
	public ResponseEntity<Estate> update(@RequestBody Estate estate) {
		try {
			return ResponseEntity.ok(estateService.update(estate));
		} catch (EstateDoesNotExistException | ApartmentDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping
	public ResponseEntity<Estate> delete(@RequestBody Estate estate) {
		try {
			estateService.delete(estate);
			return ResponseEntity.ok().build();
		} catch (EstateDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (ApartmentDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
