package com.realstate.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.realstate.entities.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
	public List<User> findByNameAndPasswordAndActiveTrue(String name, String password);
}
