package com.realstate.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.Estate;
import com.realstate.repositories.EstateRepository;

@Service
public class EstateService {
	
	@Autowired
	private EstateRepository estateRepository;
	
	public List<Estate> findAll() {
		return estateRepository.findAll();
	}

	public Optional<Estate> findById(String estateId) {
		return estateRepository.findById(new ObjectId(estateId));
	}

	public Estate insert(Estate newEstate) {
		return estateRepository.insert(newEstate);
	}

	public Estate update(Estate estate) {
		return estateRepository.save(estate);
	}

	public void delete(Estate estate) {
		estateRepository.delete(estate);
	}
}
