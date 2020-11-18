package com.realstate.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.Apartment;
import com.realstate.domains.Lease;
import com.realstate.domains.Payment;
import com.realstate.domains.RentalBill;
import com.realstate.domains.Tenant;
import com.realstate.exceptions.AmountPaymentHigherThanRentalBillException;
import com.realstate.exceptions.AmountToPaidIsZeroException;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.exceptions.LeaseDoesNotExistException;
import com.realstate.exceptions.LeaseIsNotActiveException;
import com.realstate.exceptions.RentalBillDoesNotExistException;
import com.realstate.exceptions.RentalBillHasAlreadyBeenPaidException;
import com.realstate.exceptions.TenantDoesNotExistException;
import com.realstate.repositories.LeaseRepository;

@Service
public class LeaseService {

	@Autowired
	private TenantService tenantService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private LeaseRepository leaseRepository;
	@Autowired
	private ApartmentService apartmentService;
	@Autowired
	private RentalBillService rentalBillService;
	
	public Lease getNew(Tenant tenant, Apartment apartment, Date startDate, Date endDate, boolean active,
			String description) throws TenantDoesNotExistException, ApartmentDoesNotExistException {
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
