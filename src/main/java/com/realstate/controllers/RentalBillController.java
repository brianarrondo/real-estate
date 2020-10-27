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
import com.realstate.services.RentalBillService;

@RestController
@RequestMapping("rentalbill")
public class RentalBillController {
	
	@Autowired
	private RentalBillService rentalBillService;
	
	@GetMapping(value = "{rentalBillId}")
	public ResponseEntity<RentalBill> findById(@PathVariable("rentalBillId") String rentalBillId) {
		Optional<RentalBill> optionalRentalBill = rentalBillService.findById(rentalBillId);
		return optionalRentalBill.isPresent() ? ResponseEntity.ok(optionalRentalBill.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping("all")
	public ResponseEntity<List<RentalBill>> findAll() {
		return ResponseEntity.ok(rentalBillService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<RentalBill> insert(@RequestBody RentalBill newRentalBill) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(rentalBillService.insert(newRentalBill));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
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
		return rentalBillService.existById(rentalBill.getRentalBillId()) ? ResponseEntity.ok(rentalBillService.update(rentalBill)) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping
	public ResponseEntity<RentalBill> delete(@RequestBody RentalBill rentalBill) {
		if (rentalBillService.existById(rentalBill.getRentalBillId())) {
			rentalBillService.delete(rentalBill);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}