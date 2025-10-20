package com.goldenleaf.shop.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User{
	
	
	public Admin() {}
	
	public Admin(String login, String passwordHash, String name, LocalDate lastActivity)
	{
		super(login, passwordHash, name, lastActivity);
	}
	
	

	
}