package com.realstate.controllers;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realstate.dto.lease.LeaseDto;
import com.realstate.entities.Lease;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.exceptions.RealEstateException;
import com.realstate.services.LeaseService;
import com.realstate.utils.Utils;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("lease")
public class LeaseController {
	
	@Autowired
	private LeaseService leaseService;
	
	@GetMapping("all")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(leaseService.findAll());
	}
	
	@GetMapping(value = "{leaseId}")
	public ResponseEntity<String> findById(@PathVariable(value = "leaseId") long leaseId) {
		try {
			return ResponseEntity.ok(Utils.objToJson(leaseService.findById(leaseId)));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getExceptionResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getExceptionResponseMsg(e));
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createNewLease(@RequestBody LeaseDto leaseDto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(leaseService.create(leaseDto));
		} catch (RealEstateException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<String> update(@RequestBody Lease lease) {
		try {
			return ResponseEntity.ok(Utils.objToJson(leaseService.update(lease)));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getExceptionResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getExceptionResponseMsg(e));
		}
	}
	
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestBody Lease lease) {
		try {
			leaseService.delete(lease);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getExceptionResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getExceptionResponseMsg(e));
		}
	}
}
