package com.realstate.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.domains.Apartament;
import com.realstate.repositories.ApartamentRepository;


@Service
public class ApartamentService {

	@Autowired
	private ApartamentRepository apartamentRepository;
	
	public List<Apartament> findAll() {
		return apartamentRepository.findAll();
	}
	
	public Optional<Apartament> findById(String apartamentId) {
		return apartamentRepository.findById(new ObjectId(apartamentId));
	}
	
	public List<Apartament> findAllByEstate(int estateId) {
		return apartamentRepository.findAllByEstateId(estateId);
	}
	
	public Apartament insert(Apartament newApartament) {
		return apartamentRepository.insert(newApartament);
	}
	
	public Apartament update(Apartament apartament) {
		return apartamentRepository.save(apartament);
	}
	
	public void delete(Apartament apartament) {
		apartamentRepository.delete(apartament);
	}
}
