package com.goldenleaf.shop.model;

import com.goldenleaf.shop.exception.EmptyProductException;
import com.goldenleaf.shop.exception.IncorrectQuantityException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class ShoppingItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	private int quantity;
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	private ShoppingCart cart;
	
	
	public ShoppingItem() {}
	
	public ShoppingItem(Product product, int quantity) throws EmptyProductException, IncorrectQuantityException
	{
		if(product == null)
		{
			throw new EmptyProductException("Product cannot be null");
		}
	
		if(quantity < 1)
		{
			throw new IncorrectQuantityException("Quanity cannot be less than 1");
				
		}
			
		this.product = product;
		this.quantity = quantity;
		
		}
	
    public Long getId() {
        return id;
    }
    
	public Product getProduct() {
	    return product;
	}

	public int getQuantity() {
	    return quantity;
	}
	
	public void setProduct(Product product) throws EmptyProductException {
	    if(product == null) {
	        throw new EmptyProductException("Product cannot be null");
	    }
	    this.product = product;
	}
	

	public void setQuantity(int quantity) throws IncorrectQuantityException {
	    if(quantity < 1) {
	        throw new IncorrectQuantityException("Quantity cannot be less than 1");
	    }
	    this.quantity = quantity;
	}
	
	public ShoppingCart getCart() {
	    return cart;
	}


	public void setCart(ShoppingCart cart) {
	    this.cart = cart;
	}
	
}
