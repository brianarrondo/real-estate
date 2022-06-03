package com.realstate.services;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realstate.dto.lease.LeaseDto;
import com.realstate.dto.lease.LeaseDtoResponse;
import com.realstate.entities.Apartment;
import com.realstate.entities.Lease;
import com.realstate.entities.RentalFees;
import com.realstate.entities.Tenant;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.exceptions.InvalidParametersException;
import com.realstate.exceptions.RealEstateException;
import com.realstate.repositories.LeaseRepository;
import com.realstate.utils.Utils;

@Service
@Transactional
public class LeaseService {

	@Autowired
	private TenantService tenantService;
	@Autowired
	private LeaseRepository leaseRepository;
	@Autowired
	private ApartmentService apartmentService;
	@Autowired
	private RentalBillService rentalBillService;
	@Autowired
	private RentalFeesService rentalFeesService;
	@Autowired
    private ModelMapper modelMapper;

	public LeaseDto create(LeaseDto leaseDto) throws EntityNotFoundException, InvalidParametersException, ParseException, RealEstateException {
		List<Tenant> tenants = leaseDto.tenants;
		
		String description = leaseDto.description;
		long apartmentId = leaseDto.apartmentId;
		Date endDate = leaseDto.endDate;
		Date startDate = leaseDto.startDate;

		if (tenants.size() == 0 || apartmentId == 0 || startDate == null || endDate == null) {
			throw new InvalidParametersException("Parametros invalidos");
		}
		
		Apartment apartment = apartmentService.findById(apartmentId);
		
		for (ListIterator<Tenant> iterator = tenants.listIterator(); iterator.hasNext();) {
			Tenant tenant = (Tenant) iterator.next();
			long tenantId = tenant.getId();
			if (tenantId == 0 || !tenantService.existById(tenant.getId())) {
				iterator.set(tenantService.create(tenant.getFullName(), tenant.getDni(), tenant.getPhone(), tenant.getDescription()));
			}
		}

		Lease lease = new Lease(0, leaseDto.name, tenants, apartment, startDate, endDate, true, description, leaseDto.baseAmount);
		List<RentalFees> fees = rentalFeesService.InsertAll(leaseDto.getRentalFees(), lease);
		rentalBillService.generateRentalBills(lease, fees);

		return modelMapper.map(insert(lease), LeaseDto.class);
	}

	public List<LeaseDtoResponse> findAll() {
		return leaseRepository.findAll().stream()
				.map(e -> new LeaseDtoResponse(e, rentalBillService.findAllByLease(e.getId())))
				.collect(Collectors.toList());
	}
	
	public Lease findById(long leaseId) throws EntityNotFoundException {
		Optional<Lease> optionalLease = leaseRepository.findById(leaseId);
		if (optionalLease.isPresent()) {
			return optionalLease.get();
		} else {
			throw new EntityNotFoundException("El contrato de alquiler con el ID especificado no existe.");
		}
	}
	
	public boolean existsById(long leaseId) {
		return leaseRepository.existsById(leaseId);
	}
	
	public Lease insert(Lease newLease) throws EntityNotFoundException {
		boolean tenantsDoesNotExist = false;
		for (Tenant t : newLease.getTenants()) {
			if (!tenantService.existById(t.getId())) tenantsDoesNotExist = true;
		}

		if (tenantsDoesNotExist) {
			throw new EntityNotFoundException("El inquilino con el ID especificado no existe.");
		} else if (!apartmentService.existById(newLease.getApartment().getId())) {
			throw new EntityNotFoundException("El departamento con el ID especificado no existe.");
		} else {
			return leaseRepository.save(newLease);
		}
	}
	
	public Lease update(Lease lease) throws EntityNotFoundException {
		if (leaseRepository.existsById(lease.getId())) {
			return leaseRepository.save(lease);
		} else {
			throw new EntityNotFoundException("El contrato de alquiler con el ID especificado no existe.");
		}
	}
	
	public void delete(Lease lease) throws EntityNotFoundException {
		if (leaseRepository.existsById(lease.getId())) {
			leaseRepository.delete(lease); 
		} else {
			throw new EntityNotFoundException("El contrato de alquiler con el ID especificado no existe.");
		}
	}
}
