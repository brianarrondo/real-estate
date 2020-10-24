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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realstate.domains.Apartament;
import com.realstate.services.ApartamentService;

@RestController
@RequestMapping("apartament")
public class ApartamentController {
	
	@Autowired
	private ApartamentService apartamentService;
	
	@GetMapping("all")
	public ResponseEntity<List<Apartament>> findAll() {
		return ResponseEntity.ok(apartamentService.findAll());
	}
	
	@GetMapping(value = "{apartamentId}")
	public ResponseEntity<Apartament> findById(@PathVariable(value = "apartamentId") String apartamentId) {
		Optional<Apartament> optionalApartament = apartamentService.findById(apartamentId);
		if (optionalApartament.isPresent()) {
			return ResponseEntity.ok(optionalApartament.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Apartament>> findAllByEstateId(@RequestParam(value = "estateId") int estateId) {
		return ResponseEntity.ok(apartamentService.findAllByEstate(estateId));
	}
	
	@PostMapping
	public ResponseEntity<Apartament> insert(@RequestBody Apartament newApartament) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(apartamentService.insert(newApartament));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PutMapping
	public ResponseEntity<Apartament> update(@RequestBody Apartament apartament) {
		Optional<Apartament> optionalApartament = apartamentService.findById(apartament.getApartamentId());
		if (optionalApartament.isPresent()) {
			return ResponseEntity.ok(apartamentService.update(apartament));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();		
		} 
	}
	
	@DeleteMapping
	public ResponseEntity<Apartament> delete(@RequestBody Apartament apartament) {
		Optional<Apartament> optionalApartament = apartamentService.findById(apartament.getApartamentId());
		if (optionalApartament.isPresent()) {
			apartamentService.delete(apartament);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
