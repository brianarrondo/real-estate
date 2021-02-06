package com.realstate.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.realstate.domains.RentalBill;

@Repository
public interface RentalBillRepository extends MongoRepository<RentalBill, ObjectId> {
	public List<RentalBill> findAllByLeaseId(String leaseId);
}
