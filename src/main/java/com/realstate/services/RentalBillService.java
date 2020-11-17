package com.realstate.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.RentalBill;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.RentalBillDoesNotExistException;
import com.realstate.repositories.RentalBillRepository;

@Service
public class RentalBillService {
	
	@Autowired
	private RentalBillRepository rentalBillRepository;
	@Autowired
	private LeaseService leaseService;
	
	public RentalBill getNew(String leaseId, Date date, float amount) throws LeaseDoesNotExistException {
		RentalBill newRentalBill = new RentalBill(null, leaseId, date, amount);
		return insert(newRentalBill);
	}
	
	public RentalBill findById(String rentalBillId) throws RentalBillDoesNotExistException {
		Optional<RentalBill> optionalBill = rentalBillRepository.findById(new ObjectId(rentalBillId));
		if (optionalBill.isPresent()) {
			return optionalBill.get();
		} else {
			throw new RentalBillDoesNotExistException();
		}
	}
	
	public List<RentalBill> findAll() {
		return rentalBillRepository.findAll();
	}
	
	public boolean existsById(String rentalBillId) {
		return rentalBillRepository.existsById(new ObjectId(rentalBillId));
	}
	
	public RentalBill insert(RentalBill newRentalBill) throws LeaseDoesNotExistException {
		if (!leaseService.existsById(newRentalBill.getLeaseId())) {
			throw new LeaseDoesNotExistException();
		}
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
