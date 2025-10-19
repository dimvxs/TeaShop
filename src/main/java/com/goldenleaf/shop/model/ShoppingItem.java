package com.goldenleaf.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ShoppingItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	private int quantity;
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private ShoppingCart cart;
	
	
	public ShoppingItem() {}
	
	public ShoppingItem(Product product, int quantity)
	{
		if(product == null)
		{
			throw new IllegalArgumentException("Product cannot be null");
		}
	
		if(quantity < 1)
		{
			throw new IllegalArgumentException("Quanity cannot be less than 1");
				
		}
			
		this.product = product;
		this.quantity = quantity;
		
		}
	
    public int getId() {
        return id;
    }
    
	public Product getProduct() {
	    return product;
	}

	public int getQuantity() {
	    return quantity;
	}
	
	public void setProduct(Product product) {
	    if(product == null) {
	        throw new IllegalArgumentException("Product cannot be null");
	    }
	    this.product = product;
	}
	

	public void setQuantity(int quantity) {
	    if(quantity < 1) {
	        throw new IllegalArgumentException("Quantity cannot be less than 1");
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
