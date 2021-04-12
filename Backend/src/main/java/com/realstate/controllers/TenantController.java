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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realstate.entities.Tenant;
import com.realstate.exceptions.TenantDoesNotExistException;
import com.realstate.services.TenantService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("tenant")
public class TenantController {
	
	@Autowired
	private TenantService tenantService;
	
	@GetMapping("all")
	public ResponseEntity<List<Tenant>> findAll() {
		return ResponseEntity.ok(tenantService.findAll());
	}
	
	@GetMapping(value = "{tenantId}")
	public ResponseEntity<Tenant> findById(@PathVariable("tenantId") String tenantId) {
		try {
			return ResponseEntity.ok(tenantService.findById(tenantId));
		} catch (TenantDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Tenant>> findByDni(@RequestParam(value = "dni") String tenantDni) {
		return ResponseEntity.ok(tenantService.findByDni(tenantDni));
	}
	
	@PostMapping
	public ResponseEntity<Tenant> insert(@RequestBody Tenant newTenant) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(tenantService.insert(newTenant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PutMapping
	public ResponseEntity<Tenant> update(@RequestBody Tenant tenant) {
		try {
			return ResponseEntity.ok(tenantService.update(tenant));
		} catch (TenantDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Tenant> delete(@RequestBody Tenant tenant) {
		try {
			tenantService.delete(tenant);
			return ResponseEntity.ok().build();
		} catch (TenantDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}	
}
