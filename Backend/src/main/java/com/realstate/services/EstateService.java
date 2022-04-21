package com.realstate.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realstate.dto.ApartmentDto;
import com.realstate.dto.EstateDto;
import com.realstate.entities.Apartment;
import com.realstate.entities.Estate;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.repositories.EstateRepository;

@Service
@Transactional
public class EstateService {
	
	@Autowired
	private EstateRepository estateRepository;
	@Autowired
	private ApartmentService apartmentService;
	@Autowired
    private ModelMapper modelMapper;
	
	public Estate getNew(String name, String address, String description) {
		Estate newEstate = new Estate(name, address, description, null, true);
		return estateRepository.save(newEstate);
	}

	public List<EstateDto> findAll() {
		return estateRepository.findAll().stream().map(e -> modelMapper.map(e, EstateDto.class)).collect(Collectors.toList());
	}

	public Estate findById(long estateId) throws EntityNotFoundException {
		Optional<Estate> optionalEstate = estateRepository.findById(estateId);
		if (optionalEstate.isPresent()) {
			return optionalEstate.get();
		} else {
			throw new EntityNotFoundException("La propiedad con el ID especificado no existe.");
		}
	}
	
	public boolean existsById(long apartmentId) {
		return estateRepository.existsById(apartmentId);
	}
	
	public EstateDto createEstate(EstateDto newEstate) throws EntityNotFoundException {
		// Insertamos el estate sin los apartments
		List<ApartmentDto> apartments = newEstate.getApartments();
		Estate estate = new Estate(newEstate.getName(), newEstate.getAddress(), newEstate.getDescription(), null, newEstate.isActive());
		Estate estateInserted = insert(estate); 
		
		// Insertamos los apartments
		apartmentService.insertAll(apartments.stream().map(a -> new Apartment(estateInserted, a.getRooms(), a.getName(), a.getName(), a.isActive()))
				.collect(Collectors.toList()));

		return modelMapper.map(estateInserted, EstateDto.class);
	}

	public Estate insert(Estate newEstate) {
		return estateRepository.save(newEstate);
	}

	public EstateDto update(EstateDto estateDto) throws EntityNotFoundException {
		Estate estate = findById(estateDto.getId());
		estate.setActive(estateDto.isActive());
		estate.setAddress(estateDto.getAddress());
		estate.setDescription(estateDto.getDescription());
		estate.setName(estateDto.getName());
		for (ApartmentDto apartmentDto : estateDto.getApartments()) {
			Apartment apartment = new Apartment(estate, apartmentDto.getRooms(), apartmentDto.getName(), apartmentDto.getDescription(), apartmentDto.isActive());
			apartment.setId(apartmentDto.getId());
			if (apartment.getId() == 0) {
				apartment = apartmentService.insert(apartment);
			} else {
				apartmentService.update(apartment);
			}
		}
		return modelMapper.map(estateRepository.save(estate), EstateDto.class);
	}

	public void delete(EstateDto estateDto) throws EntityNotFoundException {
		if (estateRepository.existsById(estateDto.getId())) {
			List<ApartmentDto> apartments = estateDto.getApartments();
			if (apartments != null && apartments.size() > 0) {
				for (ApartmentDto a : apartments) {
					apartmentService.delete(a);
				}
			}
			estateRepository.deleteById(estateDto.getId());
		} else {
			throw new EntityNotFoundException("La propiedad con el ID especificado no existe.");
		}
	}
}
