package com.realstate.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.Tenant;
import com.realstate.repositories.TenantRepository;

@Service
public class TenantService {
	
	@Autowired
	private TenantRepository tenantRepository;

	public List<Tenant> findAll() {
		return tenantRepository.findAll();
	}
	
	public Optional<Tenant> findById(String tenantId) {
		return tenantRepository.findById(new ObjectId(tenantId));
	}
	
	public List<Tenant> findByDni(String tenantDni) {
		return tenantRepository.findAllByDni(tenantDni);
	}
	
	public Tenant insert(Tenant newTenant) {
		return tenantRepository.insert(newTenant);
	}
	
	public Tenant update(Tenant tenant) {
		return tenantRepository.save(tenant);
	}
	
	public void delete(Tenant tenant) {
		tenantRepository.delete(tenant);
	}
}
