package com.goldenleaf.shop.model;

import java.time.YearMonth;

import com.goldenleaf.shop.exception.EmptyCardNumberException;
import com.goldenleaf.shop.exception.EmptyCvvException;
import com.goldenleaf.shop.exception.EmptyExpiryException;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.exception.NullCustomerException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "credit_card")
public class CreditCard {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(nullable = false, length = 100)
private String holderName;
@Column(nullable = false, length = 16)
private String cardNumber;
@Column(nullable = false)
private YearMonth expiry;
private transient String cvv;
@ManyToOne
@JoinColumn(name = "customer_id", nullable = false)
private Customer customer;



public Long getId() {
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


public void setCustomer(Customer customer) throws NullCustomerException{
 if (customer == null) {
     throw new NullCustomerException("Customer cannot be null");
 }
 this.customer = customer;
}

public void setHolderName(String holderName) throws EmptyNameException {
 if (holderName == null || holderName.isBlank()) {
     throw new EmptyNameException("Holder name cannot be empty");
 }
 this.holderName = holderName;
}

public void setCardNumber(String cardNumber) throws EmptyCardNumberException{
 if (cardNumber == null || cardNumber.isBlank()) {
     throw new EmptyCardNumberException("Card number cannot be empty");
 }
 this.cardNumber = cardNumber;
}

public void setExpiry(YearMonth expiry) throws EmptyExpiryException{
 if (expiry == null) {
     throw new EmptyExpiryException("Expiry cannot be null");
 }
 this.expiry = expiry;
}

public void setCvv(String cvv) throws EmptyCvvException{
 if (cvv == null || cvv.isBlank()) {
     throw new EmptyCvvException("CVV cannot be empty");
 }
 this.cvv = cvv;
}

public void clearCvv()
{
	this.cvv = null;
}

}
