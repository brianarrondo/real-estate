package com.realstate.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.realstate.domains.Tenant;

@Repository
public interface TenantRepository extends MongoRepository<Tenant, ObjectId> {
	// Solo basta definir la funcion en la interfaz y spring boot lo implementara
	public List<Tenant> findAllByDni(String tenantDni);
}
