package com.realstate.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.realstate.domains.Estate;

@Repository
public interface EstateRepository extends MongoRepository<Estate, ObjectId>{

}
