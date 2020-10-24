package com.realstate.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.Lease;
import com.realstate.repositories.LeaseRepository;

@Service
public class LeaseService {

	@Autowired
	private LeaseRepository leaseRepository;
	
	public List<Lease> findAll() {
		return leaseRepository.findAll();
	}
	
	public Optional<Lease> findById(String leaseId) {
		return leaseRepository.findById(new ObjectId(leaseId));
	}
	
	public Lease insert(Lease newLease) {
		return leaseRepository.insert(newLease);
	}
	
	public Lease update(Lease lease) {
		return leaseRepository.save(lease);
	}
	
	public void delete(Lease lease) {
		leaseRepository.delete(lease);
	}
}
