package com.realstate.controllers;

import java.util.List;
import java.util.Optional;

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

import com.realstate.domains.Estate;
import com.realstate.services.EstateService;

@RestController
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
		Optional<Estate> optionalEstate = estateService.findById(estateId);
		if (optionalEstate.isPresent()) {
			return ResponseEntity.ok(optionalEstate.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Estate> insert(@RequestBody Estate newEstate) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(estateService.insert(newEstate));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PutMapping
	public ResponseEntity<Estate> update(@RequestBody Estate estate) {
		Optional<Estate> optionalEstate = estateService.findById(estate.getEstateId());
		if (optionalEstate.isPresent()) {
			return ResponseEntity.ok(estateService.update(estate));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping
	public ResponseEntity<Estate> delete(@RequestBody Estate estate) {
		Optional<Estate> optionalEstate = estateService.findById(estate.getEstateId());
		if (optionalEstate.isPresent()) {
			estateService.delete(estate);
			return  ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
