package com.realstate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realstate.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
