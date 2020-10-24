package com.realstate.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.realstate.domains.Apartament;

@Repository
public interface ApartamentRepository extends MongoRepository<Apartament, ObjectId>{
	public List<Apartament>findAllByEstateId(int estateId);
}
