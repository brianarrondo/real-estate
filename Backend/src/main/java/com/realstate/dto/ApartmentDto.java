package com.realstate.dto;

import java.io.Serializable;

public class ApartmentDto implements Serializable{
	private long id;
	private int rooms;
	private String name;
	private String description;
	private boolean active;
	private long estateId;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public long getEstateId() {
		return estateId;
	}
	public void setEstateId(long estateId) {
		this.estateId = estateId;
	}
}
