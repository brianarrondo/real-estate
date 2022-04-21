package com.realstate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realstate.entities.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
	// Solo basta definir la funcion en la interfaz y spring boot lo implementara
	public List<Tenant> findAllByDni(String tenantDni);
}
