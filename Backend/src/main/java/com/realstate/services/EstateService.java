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
import com.realstate.repositories.EstateRepository;

@Service
public class EstateService {
	
	@Autowired
	private EstateRepository estateRepository;
	@Autowired
	private ApartmentService apartmentService;
	
	public Estate getNew(String name, String address, String description) {
		Estate newEstate = new Estate(null, name, address, description, null);
		return estateRepository.insert(newEstate);
	}

	public List<Estate> findAll() {
		return estateRepository.findAll();
	}

	public Estate findById(String estateId) throws EstateDoesNotExistException {
		Optional<Estate> optionalEstate = estateRepository.findById(new ObjectId(estateId));
		if (optionalEstate.isPresent()) {
			return optionalEstate.get();
		} else {
			throw new EstateDoesNotExistException();
		}
	}
	
	public boolean existsById(String apartmentId) {
		return estateRepository.existsById(new ObjectId(apartmentId));
	}
	
	public Estate createEstate(Estate newEstate) throws ApartmentDoesNotExistException {
		Estate estateInserted = insert(newEstate);
		List<Apartment> apartments = estateInserted.getApartments();
		for (int i = 0; i < apartments.size(); i++) {
			Apartment a = apartments.get(i);
			a.setEstateId(estateInserted.getEstateId());
			apartmentService.update(a);
		}
		return estateInserted;
	}

	public Estate insert(Estate newEstate) {
		return estateRepository.insert(newEstate);
	}

	public Estate update(Estate estate) throws EstateDoesNotExistException {
		if (estateRepository.existsById(new ObjectId(estate.getEstateId()))) {
			return estateRepository.save(estate);
		} else {
			throw new EstateDoesNotExistException();
		}
	}

	public void delete(Estate estate) throws EstateDoesNotExistException {
		if (estateRepository.existsById(new ObjectId(estate.getEstateId()))) {
			estateRepository.delete(estate);
		} else {
			throw new EstateDoesNotExistException();
		}
	}
}
