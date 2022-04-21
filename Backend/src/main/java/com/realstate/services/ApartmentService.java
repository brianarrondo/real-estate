package com.realstate.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.realstate.dto.ApartmentDto;
import com.realstate.entities.Apartment;
import com.realstate.entities.Estate;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.repositories.ApartmentRepository;
import com.realstate.repositories.LeaseRepository;

@Service
public class ApartmentService {

	@Autowired
	private ApartmentRepository apartmentRepository;
	@Autowired
	private LeaseRepository leaseRepo;
	@Autowired
    private ModelMapper modelMapper;
	
	public Apartment getNew(Estate estate, int rooms, String name, String description) {
		Apartment apartment = new Apartment(estate, rooms, name, description, true);
		return apartmentRepository.save(apartment);
	}
	
	public List<ApartmentDto> findAll() {
		return apartmentRepository.findAll().stream().map(a -> modelMapper.map(a, ApartmentDto.class)).collect(Collectors.toList());
	}
	
	public List<ApartmentDto> findAllWithoutLease() {
		Date currentTime = new Date();
		List<Long> apartmentsIdsWithoutLease = leaseRepo.findAll()
				.stream()
				.filter(l -> ((l.getEndDate() .after(currentTime) || l.getEndDate().equals(currentTime)) && l.isActive() && l.getApartment().getId() != 0))
				.map(l -> (l.getApartment().getId()))
				.collect(Collectors.toList());

		return apartmentRepository.findAll()
				.stream()
				.filter(a -> (!apartmentsIdsWithoutLease.contains(a.getId())))
				.map(a -> modelMapper.map(a, ApartmentDto.class))
				.collect(Collectors.toList());
	}

	public List<ApartmentDto> findAllNoEstateAssigned() {
		return apartmentRepository.findByEstateIdIsNull().stream().map(a -> modelMapper.map(a, ApartmentDto.class)).collect(Collectors.toList());
	}
	
	public Apartment findById(long apartmentId) throws EntityNotFoundException {
		Optional<Apartment> optionalApartment = apartmentRepository.findById(apartmentId);
		if (optionalApartment.isPresent()) {
			return optionalApartment.get();
		} else {
			throw new EntityNotFoundException("El departamento con el ID especificado no existe.");
		}
	}
	
	public boolean existById(long apartmentId) {
		return apartmentRepository.existsById(apartmentId);
	}
		
	public Apartment insert(Apartment newApartment) {
		newApartment.setActive(true);
		return apartmentRepository.save(newApartment);
	}
	
	public List<Apartment> insertAll(List<Apartment> newApartments) {
		return apartmentRepository.saveAll(newApartments);
	}
	
	public Apartment update(Apartment apartment) throws EntityNotFoundException {
		if (apartmentRepository.existsById(apartment.getId())) {
			return apartmentRepository.save(apartment);
		} else {
			throw new EntityNotFoundException("El departamento con el ID especificado no existe.");
		}
	}
	
	public void delete(ApartmentDto a) throws EntityNotFoundException {
		long id = a.getId();
		if (apartmentRepository.existsById(id)) {
			apartmentRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("El departamento con el ID especificado no existe.");
		}
	}
}
