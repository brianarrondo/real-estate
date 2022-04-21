package com.realstate.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.realstate.exceptions.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.entities.Lease;
import com.realstate.entities.Tenant;
import com.realstate.repositories.LeaseRepository;
import com.realstate.repositories.TenantRepository;

@Service
public class TenantService {
	
	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	private LeaseRepository leaseRepo;
	
	public Tenant create(String fullName, String dni, String phone, String description) {
		Tenant tenant = new Tenant(fullName, dni, phone, description);
		return tenantRepository.save(tenant);
	}
	
	public List<Tenant> findAll() {
		return tenantRepository.findAll();
	}
	
	public List<Tenant> findAllWithoutLease() {
		Date currentTime = new Date();
		List<Lease> activeLeases = leaseRepo.findAll()
				.stream()
				.filter(l -> ((l.getEndDate().after(currentTime) || l.getEndDate().equals(currentTime)) && l.isActive() && l.getTenants() != null))
				.collect(Collectors.toList());
		
		List<Long> tenantsIdsWithoutLease = new ArrayList<Long>();
		for (Lease lease : activeLeases) {
			lease.getTenants().forEach(t -> tenantsIdsWithoutLease.add(t.getId()));
		}

		return tenantRepository.findAll()
				.stream()
				.filter(a -> (!tenantsIdsWithoutLease.contains(a.getId())))
				.collect(Collectors.toList());
	}
	
	public Tenant findById(long tenantId) throws EntityNotFoundException {
		Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
		if (optionalTenant.isPresent()) {
			return optionalTenant.get();
		} else {
			throw new EntityNotFoundException("El inquilino con el ID especificado no existe.");
		}
	}
	
	public List<Tenant> findByDni(String tenantDni) {
		return tenantRepository.findAllByDni(tenantDni);
	}
	
	public boolean existById(long tenantId) {
		return tenantRepository.existsById(tenantId);
	}
	
	public Tenant insert(Tenant newTenant) {
		return tenantRepository.save(newTenant);
	}
	
	public Tenant update(Tenant tenant) throws EntityNotFoundException {
		if (tenantRepository.existsById(tenant.getId())) {
			return tenantRepository.save(tenant);
		} else {
			throw new EntityNotFoundException("El inquilino con el ID especificado no existe.");
		}
	}
	
	public void delete(Tenant tenant) throws EntityNotFoundException {
		if (tenantRepository.existsById(tenant.getId())) {
			tenantRepository.delete(tenant);
		} else {
			throw new EntityNotFoundException("El inquilino con el ID especificado no existe.");
		}
	}
}
