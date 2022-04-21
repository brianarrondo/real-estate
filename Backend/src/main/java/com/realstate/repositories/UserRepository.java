package com.realstate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realstate.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findByNameAndPasswordAndActiveTrue(String name, String password);
}
