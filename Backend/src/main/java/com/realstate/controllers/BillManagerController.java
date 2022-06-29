package com.realstate.controllers;

import java.util.Date;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.realstate.dto.payment.PaymentCreationDto;
import com.realstate.entities.RentalBill;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.services.RentalBillService;
import com.realstate.utils.Utils;

@RestController
@RequestMapping("billmanager")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class BillManagerController {
	
	@Autowired
	private RentalBillService rentalBillService;
	
	@GetMapping(value = "{leaseId}")
	public ResponseEntity<String> findById(@PathVariable("leaseId") long rentalBillId) {
		try {
			return ResponseEntity.ok(Utils.objToJson(rentalBillService.findById(rentalBillId)));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getExceptionResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getExceptionResponseMsg(e));
		}
	}
	
	@GetMapping("all")
	public ResponseEntity<String> findAll() {
		try {
			return ResponseEntity.ok(Utils.objToJson(rentalBillService.findAll()));
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getExceptionResponseMsg(e));
		}
	}
	
	/*
	@PostMapping("generate_bill")
	public ResponseEntity<String> generateRentalBill(@RequestBody Map<String, String> parsedJson) {
		try {
			String dateString = parsedJson.get("date");
			long leaseId = Long.parseLong(parsedJson.get("leaseId"));
			float amount = Float.parseFloat(parsedJson.get("amount"));
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
			Date date = formatter.parse(dateString);
			return ResponseEntity.status(HttpStatus.CREATED).body(Utils.objToJson(rentalBillService.generateRentalBill(leaseId, date, amount)));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getExceptionResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getExceptionResponseMsg(e));
		}
	}*/
	
	@PostMapping("generate_payment")
	public ResponseEntity<?> generatePayment(@RequestBody PaymentCreationDto dto) {
		try {
			/*
			String dateString = parsedJson.get("date");
			long leaseId = Long.parseLong(parsedJson.get("leaseId"));
			float amountToPay = Float.parseFloat(parsedJson.get("amount"));

			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
			Date date = formatter.parse(dateString);*/
			Date date = dto.date;
			long rentalBillId = dto.rentalBillId;
			float amountToPay = dto.amount;
			long userId = dto.userId;
			return ResponseEntity.ok(rentalBillService.generatePayment(rentalBillId, amountToPay, date, userId));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("all")
	public ResponseEntity<String> insertAll(@RequestBody List<RentalBill> newRentalBillList) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(Utils.objToJson(rentalBillService.insertAll(newRentalBillList)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(Utils.getExceptionResponseMsg(e));
		}
	}
	
	
	@PutMapping
	public ResponseEntity<String> update(@RequestBody RentalBill rentalBill) {
		try {
			return ResponseEntity.ok(Utils.objToJson(rentalBillService.update(rentalBill)));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getExceptionResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getExceptionResponseMsg(e));
		}
	}
	
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestBody RentalBill rentalBill) {
		try {
			rentalBillService.delete(rentalBill);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Utils.getExceptionResponseMsg(e));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.getExceptionResponseMsg(e));
		}
	}
}