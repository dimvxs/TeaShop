package com.goldenleaf.shop.model;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
	@SequenceGenerator(name = "review_seq", sequenceName = "REVIEW_SEQ", allocationSize = 1)
	private int id;
	private String login;
	private String passwordHash;
	private String name;
	private LocalDate lastActivity;
	
	
    public User() {}
    
    public User(String login, String passwordHash, String name, LocalDate lastActivity)
    {
    	this.login = login;
    	this.passwordHash = passwordHash;
    	this.name = name;
    	this.lastActivity = lastActivity;
    	
    }
	
	
    public int getId() {
        return id;
    }
	
	
	public String getLogin()
	
	{
		if(login == null || login.isBlank())
			
		{
			
			throw new IllegalArgumentException("Login is empty");
			
		}
		
		return login;
	}
	
	public void setLogin(String login)
	
	{
		
         if(login == null || login.isBlank())
			
		{
			
			throw new IllegalArgumentException("Login cannot be empty");
			
		}
         this.login = login;
		
	}
	
	public void setPassword(String password)
	
	{
	   //надо реализовать хэширование
	}
	
	public LocalDate getLastActivity()
	
	{
		return lastActivity; 
	}
	
	public void setLastActivity(LocalDate lastActivity)
	
	{
		if(lastActivity == null)
			
		{
			
			throw new IllegalArgumentException("Last activity cannot be null");
			
		}
		
		this.lastActivity = lastActivity;
	}
	
	
}
