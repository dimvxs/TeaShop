package com.goldenleaf.shop.model;

import java.time.YearMonth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CreditCard {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String holderName;
private String cardNumber;
private YearMonth expiry;
private transient String cvv;
@ManyToOne
@JoinColumn(name = "customer_id", nullable = false)
private Customer customer;



public int getId() {
 return id;
}

public Customer getCustomer() {
 return customer;
}

public String getHolderName() {
 return holderName;
}

public String getCardNumber() {
 return cardNumber;
}

public YearMonth getExpiry() {
 return expiry;
}


public void setCustomer(Customer customer) {
 if (customer == null) {
     throw new IllegalArgumentException("Customer cannot be null");
 }
 this.customer = customer;
}

public void setHolderName(String holderName) {
 if (holderName == null || holderName.isBlank()) {
     throw new IllegalArgumentException("Holder name cannot be empty");
 }
 this.holderName = holderName;
}

public void setCardNumber(String cardNumber) {
 if (cardNumber == null || cardNumber.isBlank()) {
     throw new IllegalArgumentException("Card number cannot be empty");
 }
 this.cardNumber = cardNumber;
}

public void setExpiry(YearMonth expiry) {
 if (expiry == null) {
     throw new IllegalArgumentException("Expiry cannot be null");
 }
 this.expiry = expiry;
}

public void setCvv(String cvv) {
 if (cvv == null || cvv.isBlank()) {
     throw new IllegalArgumentException("CVV cannot be empty");
 }
 this.cvv = cvv;
}

public void clearCvv()
{
	this.cvv = null;
}

}
