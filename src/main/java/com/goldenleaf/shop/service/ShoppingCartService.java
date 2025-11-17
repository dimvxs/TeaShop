package com.goldenleaf.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.ShoppingCart;
import com.goldenleaf.shop.model.User;
import com.goldenleaf.shop.repository.ShoppingCartRepository;
import com.goldenleaf.shop.repository.UserRepository;

@Service
public class ShoppingCartService {
private final ShoppingCartRepository shoppingCartRepository;

public ShoppingCartService(ShoppingCartRepository repo)
{
	this.shoppingCartRepository = repo;
}



public List<ShoppingCart> getAllShoppingCarts()
{
	return shoppingCartRepository.findAll();
}

public ShoppingCart getShoppingCartById(Long id)
{
	 return shoppingCartRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Shopping cart not found with id: " + id));
}


public ShoppingCart getShoppingCartByCustomer(Customer customer)
{
	return shoppingCartRepository.findByCustomer(customer)
			.orElseThrow(() -> new RuntimeException("Shopping cart not found by customer: " + customer));
}



public void addShoppingCart(ShoppingCart cart)
{
	shoppingCartRepository.save(cart);
}


public void removeShoppingCart(ShoppingCart cart)
{
    if (cart != null && shoppingCartRepository.existsById(cart.getId())) {
    	shoppingCartRepository.delete(cart);
    }
}

public void removeShoppingCartById(Long id)
{

	shoppingCartRepository.deleteById(id);
}

public void removeShoppingCartByCustomer(Customer customer) throws EmptyLoginException
{


	    Optional<ShoppingCart> cart = shoppingCartRepository.findByCustomer(customer);

	    if (cart.isPresent()) {
	        shoppingCartRepository.delete(cart.get());
	    } else {
	        throw new EmptyLoginException("Customer not found with login: " + customer.getLogin());
	    }
	

}


public void editShoppingCart(ShoppingCart cart)
{
   if (cart.getId() == null || !shoppingCartRepository.existsById(cart.getId())) {
        throw new RuntimeException("Shopping cart not found");
    }
   shoppingCartRepository.save(cart); 
}


}
