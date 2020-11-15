package com.realstate.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.mapper.Mapper;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realstate.domains.Lease;
import com.realstate.services.LeaseService;

@RestController
@RequestMapping("lease")
public class LeaseController {
	
	@Autowired
	private LeaseService leaseService;
	ObjectMapper mapper = new ObjectMapper();
	
	@GetMapping("all")
	public ResponseEntity<List<Lease>> findAll() {
		return ResponseEntity.ok(leaseService.findAll());
	}
	
	@GetMapping(value = "{leaseId}")
	public ResponseEntity<Lease> findById(@PathVariable(value = "leaseId") String leaseId) {
		Optional<Lease> optionalLease = leaseService.findById(leaseId);
		if (optionalLease.isPresent()) {
			return ResponseEntity.ok(optionalLease.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping
	public ResponseEntity<String> createNewLease(@RequestBody Lease newLease) {
		try {
			String newLeaseAsJson = mapper.writeValueAsString(leaseService.insert(newLease));
			return ResponseEntity.status(HttpStatus.CREATED).body(newLeaseAsJson);
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'msg': '"+ e.getMessage() +"'}");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'msg': '"+ e.getMessage() +"'}");
		}
	}
	
	@PutMapping
	public ResponseEntity<Lease> update(@RequestBody Lease lease) {
		Optional<Lease> optionalLease = leaseService.findById(lease.getLeaseId());
		if (optionalLease.isPresent()) {
			return ResponseEntity.ok(leaseService.update(lease));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Lease> delete(@RequestBody Lease lease) {
		Optional<Lease> optionalLease = leaseService.findById(lease.getLeaseId());
		if (optionalLease.isPresent()) {
			leaseService.delete(lease);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
