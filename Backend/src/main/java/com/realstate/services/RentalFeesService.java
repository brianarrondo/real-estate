package com.realstate.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realstate.dto.RentalFeesDto;
import com.realstate.entities.Lease;
import com.realstate.entities.RentalFees;
import com.realstate.repositories.RentalFeesRepository;
import com.realstate.utils.Utils;

@Service
@Transactional
public class RentalFeesService {
	@Autowired
	private RentalFeesRepository rentalFeeRepo;
	
	public List<RentalFees> InsertAll(List<RentalFeesDto> dtos, Lease lease) {
		List<RentalFees> rentalFees = dtos.stream()
				.map(s -> new RentalFees(0, s.getFee(), s.getStartDate(), s.getEndDate(), lease))
				.collect(Collectors.toList());
		return rentalFeeRepo.saveAll(rentalFees);
	}
}
