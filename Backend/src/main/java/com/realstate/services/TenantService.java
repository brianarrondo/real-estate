package com.realstate.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.entities.Tenant;
import com.realstate.exceptions.TenantDoesNotExistException;
import com.realstate.repositories.TenantRepository;

@Service
public class TenantService {
	
	@Autowired
	private TenantRepository tenantRepository;
	
	public Tenant create(String fullName, String dni, String phone, String description) {
		Tenant tenant = new Tenant(null, fullName, dni, phone, description);
		return tenantRepository.insert(tenant);
	}
	
	public List<Tenant> findAll() {
		return tenantRepository.findAll();
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
