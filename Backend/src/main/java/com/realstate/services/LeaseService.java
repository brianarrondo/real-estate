package com.realstate.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.dto.LeaseCreationDto;
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
	
	public Lease create(LeaseCreationDto leaseDto) throws TenantDoesNotExistException, ApartmentDoesNotExistException, InvalidParametersException, ParseException {
		List<Tenant> tenants = leaseDto.tenants;
		String endDateString = leaseDto.endDate;
		String description = leaseDto.description;
		String apartmentId = leaseDto.apartmentId;
		String startDateString = leaseDto.startDate;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date endDate = formatter.parse(endDateString);
		Date startDate = formatter.parse(startDateString);

		if (tenants.size() == 0 || apartmentId == null || startDate == null || endDate == null) {
			throw new InvalidParametersException("Parametros invalidos");
		}
		
		Apartment apartment = apartmentService.findById(apartmentId);
		
		for (ListIterator<Tenant> iterator = tenants.listIterator(); iterator.hasNext();) {
			Tenant tenant = (Tenant) iterator.next();
			String tenantId = tenant.getTenantId();
			if (tenantId == null || tenantId.length() == 0 || !tenantService.existById(tenant.getTenantId())) {
				iterator.set(tenantService.create(tenant.getFullName(), tenant.getDni(), tenant.getPhone(), tenant.getDescription()));
			}
		}

		Lease lease = new Lease(null, leaseDto.name, tenants, apartment, startDate, endDate, true, description);
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
		boolean tenantsDoesNotExist = false;
		for (Tenant t : newLease.getTenants()) {
			if (!tenantService.existById(t.getTenantId())) tenantsDoesNotExist = true;
		}

		if (tenantsDoesNotExist) {
			throw new TenantDoesNotExistException();
		} else if (!apartmentService.existById(newLease.getApartment().getApartmentId())) {
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
