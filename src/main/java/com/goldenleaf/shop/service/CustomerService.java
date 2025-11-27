package com.goldenleaf.shop.service;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.repository.CustomerRepository;

/**
 * Service class for managing {@link Customer} entities.
 * <p>
 * Provides business logic for retrieving and deleting customers by their mobile number.
 * Acts as an intermediary between controllers and the {@link CustomerRepository}.
 * </p>
 *
 * @see Customer
 * @see CustomerRepository
 */
@Service
public class CustomerService {

    /**
     * Repository used for performing CRUD operations on {@link Customer} entities.
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructs a new {@code CustomerService} with the provided repository.
     *
     * @param repo the repository used to perform operations on customers
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see CustomerRepository
     */
    public CustomerService(CustomerRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("CustomerRepository cannot be null");
        }
        this.customerRepository = repo;
    }

    /**
     * Retrieves a {@link Customer} by their mobile number.
     *
     * @param mobile the mobile number of the customer
     * @return the {@link Customer} with the specified mobile number
     * @throws RuntimeException if no customer with the given mobile number exists
     *
     * @see CustomerRepository#findByMobile(String)
     */
    public Customer getCustomerByMobile(String mobile) {
        return customerRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Customer not found with mobile: " + mobile));
    }

    /**
     * Removes a {@link Customer} identified by their mobile number.
     *
     * @param mobile the mobile number of the customer to delete
     * @throws RuntimeException if no customer with the given mobile number exists
     *
     * @see CustomerRepository#findByMobile(String)
     * @see CustomerRepository#delete(Object)
     */
    public void removeByMobile(String mobile) {
        customerRepository.findByMobile(mobile)
                .ifPresentOrElse(
                        customerRepository::delete,
                        () -> { throw new RuntimeException("Customer not found with mobile: " + mobile); }
                );
    }
}
