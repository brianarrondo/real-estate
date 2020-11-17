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

import com.realstate.domains.RentalBill;
import com.realstate.exceptions.RentalBillDoesNotExistException;
import com.realstate.services.LeaseService;
import com.realstate.services.RentalBillService;

@RestController
@RequestMapping("billmanager")
public class BillManagerController {
	
	@Autowired
	private RentalBillService rentalBillService;
	@Autowired
	private LeaseService leaseService;
	
	@GetMapping(value = "{rentalBillId}")
	public ResponseEntity<RentalBill> findById(@PathVariable("rentalBillId") String rentalBillId) {
		try {
			return ResponseEntity.ok(rentalBillService.findById(rentalBillId));
		} catch (RentalBillDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("all")
	public ResponseEntity<List<RentalBill>> findAll() {
		return ResponseEntity.ok(rentalBillService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<RentalBill> generateBill(@RequestBody RentalBill newRentalBill) {
		boolean leaseExist = leaseService.existsById(newRentalBill.getLeaseId());
		if (leaseExist) {
			try {
				return ResponseEntity.status(HttpStatus.CREATED).body(rentalBillService.insert(newRentalBill));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
		
	@PostMapping("all")
	public ResponseEntity<List<RentalBill>> insertAll(@RequestBody List<RentalBill> newRentalBillList) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(rentalBillService.insertAll(newRentalBillList));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PutMapping
	public ResponseEntity<RentalBill> update(@RequestBody RentalBill rentalBill) {
		return rentalBillService.existsById(rentalBill.getRentalBillId()) ? ResponseEntity.ok(rentalBillService.update(rentalBill)) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping
	public ResponseEntity<RentalBill> delete(@RequestBody RentalBill rentalBill) {
		if (rentalBillService.existsById(rentalBill.getRentalBillId())) {
			rentalBillService.delete(rentalBill);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}