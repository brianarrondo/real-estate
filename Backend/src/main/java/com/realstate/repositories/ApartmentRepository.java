package com.realstate.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.realstate.entities.Apartment;

@Repository
public interface ApartmentRepository extends MongoRepository<Apartment, ObjectId>{
	public List<Apartment> findByEstateIdIsNull();
}
