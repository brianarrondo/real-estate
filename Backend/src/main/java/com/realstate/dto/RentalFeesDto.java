package com.realstate.dto;

import java.util.Date;

import com.realstate.entities.Lease;

public class RentalFeesDto {
	private long id;
	private float fee;
	private Date startDate;
	private Date endDate;
	private long leaseId;
}
