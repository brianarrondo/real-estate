package com.realstate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realstate.entities.Estate;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Long>{

}
