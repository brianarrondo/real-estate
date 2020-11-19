package com.realstate.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.realstate.domains.RentalBill;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.RentalBillDoesNotExistException;
import com.realstate.services.RentalBillService;
import com.realstate.utils.Utils;

@RestController
@RequestMapping("billmanager")
public class BillManagerController {
	
	@Autowired
	private RentalBillService rentalBillService;
	
	@GetMapping(value = "{rentalBillId}")
	public ResponseEntity<String> findById(@PathVariable("rentalBillId") String rentalBillId) {
		try {
			return ResponseEntity.ok(Utils.objToJson(rentalBillService.findById(rentalBillId)));
		} catch (RentalBillDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
	
	@GetMapping("all")
	public ResponseEntity<String> findAll() {
		try {
			return ResponseEntity.ok(Utils.objToJson(rentalBillService.findAll()));
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
	
	@PostMapping
	public ResponseEntity<String> generateRentalBill(@RequestBody Map<String, String> parsedJson) {
		if (!parsedJson.containsKey("leaseId") || !parsedJson.containsKey("amount")) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'msg': 'Los parametros son invalidos.'}");
		}
		
		String leaseId = parsedJson.get("leaseId");
		Date date = new Date();
		float amount = Float.parseFloat(parsedJson.get("amount"));
		
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(Utils.objToJson(rentalBillService.generateRentalBill(leaseId, date, amount)));
		} catch (LeaseDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
	
	@PostMapping("all")
	public ResponseEntity<String> insertAll(@RequestBody List<RentalBill> newRentalBillList) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(Utils.objToJson(rentalBillService.insertAll(newRentalBillList)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Utils.getResponseMsg(e));
		}
	}
	
	@PutMapping
	public ResponseEntity<String> update(@RequestBody RentalBill rentalBill) {
		try {
			return ResponseEntity.ok(Utils.objToJson(rentalBillService.update(rentalBill)));
		} catch (RentalBillDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
	
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestBody RentalBill rentalBill) {
		try {
			rentalBillService.delete(rentalBill);
			return ResponseEntity.ok().build();
		} catch (RentalBillDoesNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getResponseMsg(e));
		}
	}
}