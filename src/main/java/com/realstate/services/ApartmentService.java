package com.realstate.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.Apartment;
import com.realstate.domains.Estate;
import com.realstate.repositories.ApartmentRepository;

@Service
public class ApartmentService {

	@Autowired
	private ApartmentRepository apartmentRepository;
	
	public Apartment getNew(Estate estate, int rooms, String name, String description) {
		Apartment apartment = new Apartment(null, estate, rooms, name, description);
		return apartmentRepository.insert(apartment);
	}
	
	public List<Apartment> findAll() {
		return apartmentRepository.findAll();
	}
	
	public Optional<Apartment> findById(String apartmentId) {
		return apartmentRepository.findById(new ObjectId(apartmentId));
	}
		
	public Apartment insert(Apartment newApartment) {
		return apartmentRepository.insert(newApartment);
	}
	
	public Apartment update(Apartment apartment) {
		return apartmentRepository.save(apartment);
	}
	
	public void delete(Apartment apartment) {
		apartmentRepository.delete(apartment);
	}
}
