package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.User;
@Repository
public interface CustomerRepository extends JpaRepository<User, Long> {
 
	Optional<Customer> findByMobile(String mobile);
	void deleteByMobile(String mobile);
}
