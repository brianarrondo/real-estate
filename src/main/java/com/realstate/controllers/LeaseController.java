package com.realstate.controllers;

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
import com.realstate.domains.Lease;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.TenantDoesNotExistException;
import com.realstate.services.LeaseService;
import com.realstate.utils.Utils;

@RestController
@RequestMapping("lease")
public class LeaseController {
	
	@Autowired
	private LeaseService leaseService;
	
	@GetMapping("all")
	public ResponseEntity<String> findAll() {
		try {
			return ResponseEntity.ok(Utils.objToJson(leaseService.findAll()));
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
	
	@GetMapping(value = "{leaseId}")
	public ResponseEntity<String> findById(@PathVariable(value = "leaseId") String leaseId) {
		try {
			return ResponseEntity.ok(Utils.objToJson(leaseService.findById(leaseId)));
		} catch (LeaseDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
	
	@PostMapping
	public ResponseEntity<String> createNewLease(@RequestBody Lease newLease) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(Utils.objToJson(leaseService.insert(newLease)));
		} catch (TenantDoesNotExistException | ApartmentDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
	
	@PutMapping
	public ResponseEntity<String> update(@RequestBody Lease lease) {
		try {
			return ResponseEntity.ok(Utils.objToJson(leaseService.update(lease)));
		} catch (LeaseDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
	
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestBody Lease lease) {
		try {
			leaseService.delete(lease);
			return ResponseEntity.ok().build();
		} catch (LeaseDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
}
