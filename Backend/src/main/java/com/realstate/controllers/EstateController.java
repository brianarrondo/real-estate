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

import com.realstate.dto.EstateDto;
import com.realstate.entities.Estate;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.services.EstateService;

@RestController
@CrossOrigin(origins = "https://real-estate-adm-frontend.herokuapp.com", maxAge = 3600)
@RequestMapping("estate")
public class EstateController {

	@Autowired
	private EstateService estateService;
	
	@GetMapping("all")
	public ResponseEntity<List<EstateDto>> findAll() {
		return ResponseEntity.ok(estateService.findAll());
	}
	
	@GetMapping(value = "{estateId}")
	public ResponseEntity<?> findById(@PathVariable("estateId") long estateId) {
		try {
			return ResponseEntity.ok(estateService.findById(estateId));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody EstateDto newEstateDto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(estateService.createEstate(newEstateDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody EstateDto estateDto) {
		try {
			return ResponseEntity.ok(estateService.update(estateDto));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestBody EstateDto estateDto) {
		try {
			estateService.delete(estateDto);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
