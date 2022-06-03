package com.realstate.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realstate.dto.RentalBillDto;
import com.realstate.dto.payment.PaymentDto;
import com.realstate.entities.Lease;
import com.realstate.entities.Payment;
import com.realstate.entities.RentalBill;
import com.realstate.entities.RentalFees;
import com.realstate.entities.User;
import com.realstate.exceptions.EntityNotFoundException;
import com.realstate.exceptions.InvalidParametersException;
import com.realstate.exceptions.RealEstateException;
import com.realstate.exceptions.RentalBillPaymentException;
import com.realstate.repositories.RentalBillRepository;
import com.realstate.repositories.UserRepository;
import com.realstate.utils.Utils;

@Transactional
@Service
public class RentalBillService {
	
	@Autowired
	private RentalBillRepository rentalBillRepository;
	@Autowired
	private LeaseService leaseService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private UserRepository userRepo;
	@Autowired
    private ModelMapper modelMapper;
	
	public RentalBill getNew(Lease lease, Date startDate, Date endDate, float amount) throws EntityNotFoundException {
		RentalBill newRentalBill = new RentalBill(0, lease, startDate, endDate, amount);
		return insert(newRentalBill);
	}
	
	public RentalBill findById(long rentalBillId) throws EntityNotFoundException {
		Optional<RentalBill> optionalBill = rentalBillRepository.findById(rentalBillId);
		if (optionalBill.isPresent()) {
			return optionalBill.get();
		} else {
			throw new EntityNotFoundException("La factura con el ID especificado no existe.");
		}
	}
	
	public List<RentalBill> findAll() {
		return rentalBillRepository.findAll();
	}
	
	public List<RentalBillDto> findAllByLease(long leaseId) {
		return rentalBillRepository.findAllByLeaseId(leaseId).stream().map(s -> modelMapper.map(s, RentalBillDto.class)).collect(Collectors.toList());
	}
	
	public boolean existsById(long rentalBillId) {
		return rentalBillRepository.existsById(rentalBillId);
	}
	
	public RentalBill insert(RentalBill newRentalBill) throws EntityNotFoundException {
		if (!leaseService.existsById(newRentalBill.getLease().getId())) {
			throw new EntityNotFoundException("El contrato de alquiler con el ID especificado no existe.");
		}
		return rentalBillRepository.save(newRentalBill);
	}
	
	public List<RentalBill> insertAll(List<RentalBill> rentalBillList) {
		return rentalBillRepository.saveAll(rentalBillList);
	}
	
	public RentalBill update(RentalBill rentalBill) throws EntityNotFoundException {
		if (!rentalBillRepository.existsById(rentalBill.getId())) {
			throw new EntityNotFoundException("La factura con el ID especificado no existe.");
		}
		return rentalBillRepository.save(rentalBill);
	}
	
	public void delete(RentalBill rentalBill) throws EntityNotFoundException {
		if (!rentalBillRepository.existsById(rentalBill.getId())) {
			throw new EntityNotFoundException("La factura con el ID especificado no existe.");
		}
		rentalBillRepository.delete(rentalBill);
	}
	
	private float totalPaid(RentalBill rentalBill) {
		List<Payment> payments = rentalBill.getPayments();
		float totalPaid = 0;
		for (Iterator<Payment> iterator = payments.iterator(); iterator.hasNext();) {
			Payment payment = (Payment) iterator.next();
			totalPaid += payment.getAmount();
		}
		return totalPaid;
	}
	
	private boolean rentalBillIsPaid(RentalBill rentalBill) {
		return totalPaid(rentalBill) >= rentalBill.getAmount();
	}
	
	private RentalBill getRentalBillForMonth(long leaseId, Date date) throws EntityNotFoundException {
		List<RentalBill> rentalBillsForLease = rentalBillRepository.findAllByLeaseId(leaseId);
		
		RentalBill rentalBill = rentalBillsForLease.stream().filter(r -> (date.after(r.getStartDate()) || date.equals(r.getStartDate())) && date.before(r.getEndDate()))
				.findAny().orElse(null);
		if (rentalBill != null) {
			return rentalBill;
		} else {
			throw new EntityNotFoundException(".");
		}
	}
	
	public PaymentDto generatePayment(long leaseId, float amountToPay, Date date, long userId) throws InvalidParametersException, EntityNotFoundException, RentalBillPaymentException, RealEstateException {
		if (date == null || amountToPay <= 0 || leaseId <= 0 || userId <= 0) { throw new InvalidParametersException("Parametros invalidos"); }
		leaseService.findById(leaseId);
		User user = userRepo.findById(userId).orElse(null);

		RentalBill rentalBill = getRentalBillForMonth(leaseId, date);
		
		if(rentalBillIsPaid(rentalBill)) {
			throw new RentalBillPaymentException("La factura ya fue pagada.");
		} else if ((amountToPay > rentalBill.getAmount()) || (totalPaid(rentalBill) + amountToPay > rentalBill.getAmount())) {
			throw new RentalBillPaymentException("El monto a pagar es superior al de la factura.");
		} else if (rentalBill.getAmount() == 0) {
			throw new RentalBillPaymentException("El monto a pagar es 0.");
		}

		Payment newPayment = paymentService.getNew(amountToPay, rentalBill, user, date);
		return modelMapper.map(paymentService.insert(newPayment), PaymentDto.class);
	}

	public void generateRentalBills(Lease lease, List<RentalFees> fees) {
		int startYear = Utils.getYear(lease.getStartDate());
		int startMonth = Utils.getMonth(lease.getStartDate());
		int startDay = Utils.getDay(lease.getStartDate());
		int startHours = Utils.getHours(lease.getStartDate());
		int startMinutes = Utils.getMinutes(lease.getStartDate());
		int startSeconds = Utils.getSeconds(lease.getStartDate());

		int endYear = Utils.getYear(lease.getEndDate());
		int endMonth = Utils.getMonth(lease.getEndDate());
		int endDay = Utils.getDay(lease.getEndDate());
		int endHours = Utils.getHours(lease.getEndDate());
		int endMinutes = Utils.getMinutes(lease.getEndDate());
		int endSeconds = Utils.getSeconds(lease.getEndDate());
		
		for (int year = startYear; year <= endYear; year++) {
			int firstMonth = startYear == year ? startMonth : 0;
			int finalMonth = endYear == year ? endMonth : 12;
			for (int month = firstMonth; month < finalMonth; month++) {
				Date startDate = Utils.getDate(startDay, month, year, startHours, startMinutes, startSeconds);
				Date endDate = getBillEndDate(month, year, endYear, endMonth, endDay, startDay, endHours, endMinutes, endSeconds);
				float amount = getAmount(lease, fees, startDate, endDate);
				RentalBill rentalBill = new RentalBill(0, lease, startDate, endDate, amount);
				rentalBillRepository.save(rentalBill);
			}
		}
	}
	
	private float getAmount(Lease lease, List<RentalFees> fees, Date startDate, Date endDate) {
		RentalFees fee = fees.stream().filter(e -> (e.getStartDate().after(startDate) || e.getStartDate().equals(startDate))
				&& (e.getEndDate().before(endDate) || e.getEndDate().equals(endDate))).findAny().orElse(null);
		return lease.getBaseAmount() * (1 + (fee == null ? 0 : fee.getFee()));
	}
	
	private Date getBillEndDate(int month, int year, int endYear, int endMonth, int endDay, int startDay, int hours, int minutes, int seconds) {
		boolean isLastMonth = month == 11;
		int nextMonth = month + 1;
		return Utils.getDate(endYear == year && nextMonth  == endMonth? endDay : startDay, isLastMonth ? 0 : nextMonth, isLastMonth ? year + 1 : year, hours, minutes, seconds);
	}
}
