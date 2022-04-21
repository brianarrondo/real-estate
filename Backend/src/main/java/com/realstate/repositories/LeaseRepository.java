package com.realstate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realstate.entities.Lease;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {

}
