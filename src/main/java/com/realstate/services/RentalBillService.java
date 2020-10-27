package com.realstate.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.RentalBill;
import com.realstate.repositories.RentalBillRepository;

@Service
public class RentalBillService {
	
	@Autowired
	private RentalBillRepository rentalBillRepository;
	
	public RentalBill getNew(String leaseId, Date date, float amount) {
		RentalBill newRentalBill = new RentalBill(leaseId, date, amount);
		System.out.println(newRentalBill.toString());
		return rentalBillRepository.insert(newRentalBill);
	}
	
	public Optional<RentalBill> findById(String rentalBillId) {
		return rentalBillRepository.findById(new ObjectId(rentalBillId));
	}
	
	public List<RentalBill> findAll() {
		return rentalBillRepository.findAll();
	}
	
	public boolean existById(String rentalBillId) {
		return rentalBillRepository.existsById(new ObjectId(rentalBillId));
	}
	
	public RentalBill insert(RentalBill newRentalBill) {
		return rentalBillRepository.insert(newRentalBill);
	}
	
	public List<RentalBill> insertAll(List<RentalBill> rentalBillList) {
		return rentalBillRepository.insert(rentalBillList);
	}
	
	public RentalBill update(RentalBill rentalBill) {
		return rentalBillRepository.save(rentalBill);
	}
	
	public void delete(RentalBill rentalBill) {
		rentalBillRepository.delete(rentalBill);
	}
}
