package com.realstate.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.realstate.entities.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, ObjectId> {

}
