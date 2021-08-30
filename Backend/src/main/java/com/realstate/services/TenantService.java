package com.realstate.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.entities.Lease;
import com.realstate.entities.Tenant;
import com.realstate.exceptions.TenantDoesNotExistException;
import com.realstate.repositories.TenantRepository;

@Service
public class TenantService {
	
	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	private LeaseService leaseService;
	
	public Tenant create(String fullName, String dni, String phone, String description) {
		Tenant tenant = new Tenant(null, fullName, dni, phone, description);
		return tenantRepository.insert(tenant);
	}
	
	public List<Tenant> findAll() {
		return tenantRepository.findAll();
	}
	
	public List<Tenant> findAllWithoutLease() {
		Date currentTime = new Date();
		List<Lease> activeLeases = leaseService.findAll()
				.stream()
				.filter(l -> ((l.getEndDate().after(currentTime) || l.getEndDate().equals(currentTime)) && l.isActive() && l.getTenants() != null))
				.collect(Collectors.toList());
		
		List<String> tenantsIdsWithoutLease = new ArrayList<String>();
		for (Lease lease : activeLeases) {
			lease.getTenants().forEach(t -> tenantsIdsWithoutLease.add(t.getTenantId()));
		}

		return tenantRepository.findAll()
				.stream()
				.filter(a -> (!tenantsIdsWithoutLease.contains(a.getTenantId())))
				.collect(Collectors.toList());
	}
	
	public Tenant findById(String tenantId) throws TenantDoesNotExistException {
		Optional<Tenant> optionalTenant = tenantRepository.findById(new ObjectId(tenantId));
		if (optionalTenant.isPresent()) {
			return optionalTenant.get();
		} else {
			throw new TenantDoesNotExistException();
		}
	}
	
	public List<Tenant> findByDni(String tenantDni) {
		return tenantRepository.findAllByDni(tenantDni);
	}
	
	public boolean existById(String tenantId) {
		return tenantRepository.existsById(new ObjectId(tenantId));
	}
	
	public Tenant insert(Tenant newTenant) {
		return tenantRepository.insert(newTenant);
	}
	
	public Tenant update(Tenant tenant) throws TenantDoesNotExistException {
		if (tenantRepository.existsById(new ObjectId(tenant.getTenantId()))) {
			return tenantRepository.save(tenant);
		} else {
			throw new TenantDoesNotExistException();
		}
	}
	
	public void delete(Tenant tenant) throws TenantDoesNotExistException {
		if (tenantRepository.existsById(new ObjectId(tenant.getTenantId()))) {
			tenantRepository.delete(tenant);
		} else {
			throw new TenantDoesNotExistException();
		}
	}
}
