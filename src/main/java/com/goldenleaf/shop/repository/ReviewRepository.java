package com.goldenleaf.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Review;
import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.Product;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	
List<Review> findByCustomer(Customer customer);
List<Review> findByCustomerLogin(String login);
List<Review> findByProduct(Product product);
List<Review> findByProductName(String name);
void deleteByProduct(Product product);
void deleteByProductName(String name);

}
