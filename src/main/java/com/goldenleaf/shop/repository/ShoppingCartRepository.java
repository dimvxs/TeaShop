package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

	
	Optional<ShoppingCart> findByCustomer(Customer customer);
	void deleteByCustomer(Customer customer);
}
