package com.goldenleaf.shop.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private Long id;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ShoppingItem> items = new ArrayList<>();
	

	@OneToOne(mappedBy = "shoppingCart")
	private Customer customer;
	
	@Column(name = "total_price", nullable = false)
	private double totalPrice;
	
	public ShoppingCart() {}
	
	public ShoppingCart( List<ShoppingItem> items, Customer customer, double totalPrice)
	{
		this.items = items;
		this.customer = customer;
		this.totalPrice = totalPrice;
	}
	
    public Long getId() {
        return id;
    }
    
    
	
    public List<ShoppingItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingItem> items) {
        this.items = items;
        
        for (ShoppingItem item : items) {
            item.setCart(this);
        }
    }
    
	public void addItem(Product product, int quantity) {
	    ShoppingItem item = new ShoppingItem(product, quantity);
	    item.setCart(this);        // связываем с этой корзиной
	    items.add(item);
	}

	public void removeItem(ShoppingItem item) {
	    items.remove(item);
	    item.setCart(null);        // разрываем связь
	}
	


    public void clear()
    {
    	items.clear();
    }
    
    public void removeItem(Product product)
    {
    	if(product == null) return;
    	
    	ShoppingItem toRemove = null;
    	
    	for(ShoppingItem item: items)
    	{
    		if(item.getProduct().equals(product))
    		{
    			toRemove = item;
    			break;
    		}
    	}
    	
    	if(toRemove != null)
    	{
    		items.remove(toRemove);
    		toRemove.setCart(null);
    	}
    	
    }
    
    public void setCustomer(Customer customer)
    {
    	if(customer != null)
    	{
    		this.customer = customer;
    	}
    }
    
    public double calculateTotalPrice()
    {
    	return  items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();	
    
    }
    
    public double getTotalPrice() {
    	return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice)
    {
    	if(totalPrice < 0)
    	{
    		throw new IllegalArgumentException("total price should be more than 0");
    		
    	}
    	
    	this.totalPrice = totalPrice;
    }
    

  
}
