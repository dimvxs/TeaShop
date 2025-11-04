package com.goldenleaf.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.model.ShoppingCart;
import com.goldenleaf.shop.model.ShoppingItem;
import com.goldenleaf.shop.model.User;
import com.goldenleaf.shop.repository.ShoppingCartRepository;
import com.goldenleaf.shop.repository.ShoppingItemRepository;

@Service
public class ShoppingItemService {
	
	private final ShoppingItemRepository shoppingItemRepository;
	
	public ShoppingItemService(ShoppingItemRepository repo)
	{
		this.shoppingItemRepository = repo;
	}
	

public List<ShoppingItem> getAllShoppingItems()
{
	return shoppingItemRepository.findAll();
}

public List<ShoppingItem> getShoppingItemsByCart(ShoppingCart cart)
{
	return shoppingItemRepository.findByCart(cart);
}

public ShoppingItem getShoppingItemById(Long id)
{
	 return shoppingItemRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Shopping item not found with id: " + id));
}




public void addShoppingItem(ShoppingItem item)
{
	shoppingItemRepository.save(item);
}


public void removeShoppingItem(ShoppingItem item)
{
    if (item != null && shoppingItemRepository.existsById(item.getId())) {
    	shoppingItemRepository.delete(item);
    }
}

public void removeShoppingItemById(Long id)
{

	shoppingItemRepository.deleteById(id);
}

public void removeShoppingItemByProduct(Product product)
{

	shoppingItemRepository.findByProduct(product)
     .ifPresentOrElse(
    		 shoppingItemRepository::delete,
         () -> { throw new RuntimeException("Shopping item not found by product" + product.getName()); }
     );
}


public void editShoppingItem(ShoppingItem item)
{
   if (item.getId() == null || !shoppingItemRepository.existsById(item.getId())) {
        throw new RuntimeException("Shopping item not found");
    }
   shoppingItemRepository.save(item); 
}
	
}
