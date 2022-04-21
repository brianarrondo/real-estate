package com.realstate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realstate.entities.RentalBill;

@Repository
public interface RentalBillRepository extends JpaRepository<RentalBill, Long> {
	public List<RentalBill> findAllByLeaseId(long leaseId);
}
