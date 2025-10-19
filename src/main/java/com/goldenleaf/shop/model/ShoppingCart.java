package com.goldenleaf.shop.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ShoppingItem> items = new ArrayList<>();
	
	
	
    public int getId() {
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
    
    public double getTotalPrice()
    {
    	return  items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

    	
    
    }
    

  
}
