package com.realstate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realstate.entities.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>{
	public List<Apartment> findByEstateIdIsNull();
}
