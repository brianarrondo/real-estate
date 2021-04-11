package com.realstate.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.entities.Apartment;
import com.realstate.entities.Estate;
import com.realstate.exceptions.ApartmentDoesNotExistException;
import com.realstate.exceptions.EstateDoesNotExistException;
import com.realstate.repositories.ApartmentRepository;

@Service
public class ApartmentService {

	@Autowired
	private ApartmentRepository apartmentRepository;
	@Autowired
	private EstateService estateService;
	
	public Apartment getNew(Estate estate, int rooms, String name, String description) {
		Apartment apartment = new Apartment(null, estate, rooms, name, description);
		return apartmentRepository.insert(apartment);
	}
	
	public List<Apartment> findAll() {
		return apartmentRepository.findAll();
	}
	
	public Apartment findById(String apartmentId) throws ApartmentDoesNotExistException {
		Optional<Apartment> optionalApartment = apartmentRepository.findById(new ObjectId(apartmentId));
		if (optionalApartment.isPresent()) {
			return optionalApartment.get();
		} else {
			throw new ApartmentDoesNotExistException();
		}
	}
	
	public boolean existById(String apartmentId) {
		return apartmentRepository.existsById(new ObjectId(apartmentId));
	}
		
	public Apartment insert(Apartment newApartment) throws EstateDoesNotExistException {
		if (estateService.existsById(newApartment.getEstate().getEstateId())) {
			return apartmentRepository.insert(newApartment);
		} else {
			throw new EstateDoesNotExistException();
		}
	}
	
	public Apartment update(Apartment apartment) throws ApartmentDoesNotExistException {
		if (apartmentRepository.existsById(new ObjectId(apartment.getApartamentId()))) {
			return apartmentRepository.save(apartment);
		} else {
			throw new ApartmentDoesNotExistException();
		}
	}
	
	public void delete(Apartment apartment) throws ApartmentDoesNotExistException {
		if (apartmentRepository.existsById(new ObjectId(apartment.getApartamentId()))) {
			apartmentRepository.delete(apartment);
		} else {
			throw new ApartmentDoesNotExistException();
		}
	}
}
