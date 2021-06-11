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
	
	public Estate createEstate(Estate newEstate) throws ApartmentDoesNotExistException, EstateDoesNotExistException {
		// Insertamos el estate sin los apartments
		List<Apartment> apartments = newEstate.getApartments();
		newEstate.setApartments(null);
		Estate estateInserted = insert(newEstate);

		// Insertamos los apartments
		for (Apartment apartment : apartments) {
			apartment.setEstateId(estateInserted.getEstateId());
			apartmentService.insert(apartment);
		}
		
		// Le insertamos los apartments creados al estate
		newEstate.setApartments(apartments);
		update(newEstate);

		return estateInserted;
	}

	public Estate insert(Estate newEstate) {
		return estateRepository.insert(newEstate);
	}

	public Estate update(Estate estate) throws EstateDoesNotExistException, ApartmentDoesNotExistException {
		if (estateRepository.existsById(new ObjectId(estate.getEstateId()))) {
			for (Apartment apartment : estate.getApartments()) {
				apartment.setEstateId(estate.getEstateId());
				apartmentService.update(apartment);
			}
			return estateRepository.save(estate);
		} else {
			throw new EstateDoesNotExistException();
		}
	}

	public void delete(Estate estate) throws EstateDoesNotExistException, ApartmentDoesNotExistException {
		if (estateRepository.existsById(new ObjectId(estate.getEstateId()))) {
			for (Apartment apartment : estate.getApartments()) {
				apartmentService.delete(apartment);
			}
			estateRepository.delete(estate);
		} else {
			throw new EstateDoesNotExistException();
		}
	}
}
