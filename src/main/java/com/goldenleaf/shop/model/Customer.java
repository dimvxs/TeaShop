package com.goldenleaf.shop.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Customer extends User{
	
	@Column(nullable = false)
	private String mobile;
	@Column(nullable = false, unique = true)
	private String email;
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CreditCard> payments = new ArrayList<>();
	private int bonusPoints;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id", referencedColumnName = "id")
	private ShoppingCart shoppingCart;
	
	
	public Customer() {bonusPoints = 0;}
	
	public Customer(String login, String passwordHash, String name, LocalDate lastActivity, String mobile, String email, int bonusPoints, ShoppingCart shoppingCart, List<CreditCard> payments)
	{
		super(login, passwordHash, name, lastActivity);
		
		if(!checkField(mobile))
		{
			throw new IllegalArgumentException("Inccorect mobile");
		}
		
		if(!checkField(email))
		{
			throw new IllegalArgumentException("Inccorect email");
		}
		
		if(bonusPoints < 0)
		{
			throw new IllegalArgumentException("Bonus points cannot be less than 0");
		}
		
		if(shoppingCart == null)
		{
			throw new IllegalArgumentException("Shopping cart cannot be null");
		}
		if(payments == null)
		{
			throw new IllegalArgumentException("Payment cannot be null");
		}
		
		this.mobile = mobile;
		this.email = email;
		this.bonusPoints = bonusPoints;
		this.shoppingCart = shoppingCart;
		this.payments = payments;
	}
	

	private boolean checkField(String value)
	{
		
		 return value != null && !value.isBlank() && value.trim().length() >= 3;
        
	}
	
	
	public String getMobile() 
	
	{
		
		return mobile;
	}
	
	
	
    public void setMobile(String mobile)
    
    {
    	
    	if(!checkField(mobile))
    		
    	{
    		
    		throw new IllegalArgumentException("Inccorect mobile");
    		
    	}
    	
    	this.mobile = mobile;
    	
    }
    
    
    
    public String getEmail()
    
    {
    	
    	return email;
    	
    }
    
    
    public void setEmail(String email)
    {
    	if(!checkField(email))
    		
    	{
    		
    		throw new IllegalArgumentException("Inccorect email");
    		
    	}
    	
    	this.email = email;
    }
    
    
    public int getBonusPoints()
    {
    	return bonusPoints;
    }
    
    
    public void setBonusPoints(int bonuses)
    {
    	if(bonuses > 0) {
    	  	this.bonusPoints = bonuses;
    	}
  
    }
    
    public void addBonusPoints(int bonuses)
    {
     if(bonuses > 0)
    	{
    		this.bonusPoints += bonuses;
    	}
    }
    
    public void makeReview(Product product, String content, int rating)
    {
    	Review review = new Review(this, content, rating, product);
    	product.addReview(review);
    }
    
    
    public void addPayment(CreditCard card) {
        card.setCustomer(this);
        payments.add(card);
    }

    public void removePayment(CreditCard card) {
        payments.remove(card);
        card.setCustomer(null);
    }


}
