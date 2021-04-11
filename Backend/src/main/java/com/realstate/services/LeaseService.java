package com.realstate.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.entities.Apartment;
import com.realstate.entities.Lease;
import com.realstate.entities.Tenant;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.exceptions.InvalidParametersException;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.TenantDoesNotExistException;
import com.realstate.repositories.LeaseRepository;

@Service
public class LeaseService {

	@Autowired
	private TenantService tenantService;
	@Autowired
	private LeaseRepository leaseRepository;
	@Autowired
	private ApartmentService apartmentService;
	
	public Lease getNew(String tenantId, String apartmentId, Date startDate, Date endDate, boolean active,
			String description) throws TenantDoesNotExistException, ApartmentDoesNotExistException, InvalidParametersException {
		if (tenantId == null || apartmentId == null || startDate == null || endDate == null) {
			throw new InvalidParametersException();
		}
		Tenant tenant = tenantService.findById(tenantId);
		Apartment apartment = apartmentService.findById(apartmentId);
		Lease lease = new Lease(null, tenant, apartment, startDate, endDate, active, description);
		return insert(lease);
	}
		
	public List<Lease> findAll() {
		return leaseRepository.findAll();
	}
	
	public Lease findById(String leaseId) throws LeaseDoesNotExistException {
		Optional<Lease> optionalLease = leaseRepository.findById(new ObjectId(leaseId));
		if (optionalLease.isPresent()) {
			return optionalLease.get();
		} else {
			throw new LeaseDoesNotExistException();
		}
	}
	
	public boolean existsById(String leaseId) {
		return leaseRepository.existsById(new ObjectId(leaseId));
	}
	
	public Lease insert(Lease newLease) throws TenantDoesNotExistException, ApartmentDoesNotExistException {
		if (!tenantService.existById(newLease.getTenant().getTenantId())) {
			throw new TenantDoesNotExistException();
		} else if (!apartmentService.existById(newLease.getApartment().getApartamentId())) {
			throw new ApartmentDoesNotExistException();
		} else {
			return leaseRepository.insert(newLease);
		}
	}
	
	public Lease update(Lease lease) throws LeaseDoesNotExistException {
		if (leaseRepository.existsById(new ObjectId(lease.getLeaseId()))) {
			return leaseRepository.save(lease);
		} else {
			throw new LeaseDoesNotExistException();
		}
	}
	
	public void delete(Lease lease) throws LeaseDoesNotExistException {
		if (leaseRepository.existsById(new ObjectId(lease.getLeaseId()))) {
			leaseRepository.delete(lease); 
		} else {
			throw new LeaseDoesNotExistException();
		}
		
	}
}
