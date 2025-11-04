package com.goldenleaf.shop.service;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.repository.CustomerRepository;

@Service
public class CustomerService {
private final CustomerRepository customerRepository;

public CustomerService(CustomerRepository repo)
{
	this.customerRepository = repo;
}

public Customer getCustomerByMobile(String mobile)
{
	return customerRepository.findByMobile(mobile)
			.orElseThrow(() -> new RuntimeException("Customer not found with mobile: " + mobile));
}

public void removeByMobile(String mobile)
{
	customerRepository.findByMobile(mobile)
     .ifPresentOrElse(
    		 customerRepository::delete,
         () -> { throw new RuntimeException("Customer not found with mobile: " + mobile); }
     );
}

}
