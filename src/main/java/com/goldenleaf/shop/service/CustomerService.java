package com.goldenleaf.shop.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.goldenleaf.shop.dto.CustomerDTO;
import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.exception.IncorrectEmailException;
import com.goldenleaf.shop.exception.IncorrectMobileException;
import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

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
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new {@code CustomerService} with the provided repository.
     *
     * @param repo the repository used to perform operations on customers
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see CustomerRepository
     */
    public CustomerService(CustomerRepository repo, PasswordEncoder passwordEncoder) {
        if (repo == null) {
            throw new IllegalArgumentException("CustomerRepository cannot be null");
        }
        this.customerRepository = repo;
        
        if(passwordEncoder == null) {
        				throw new IllegalArgumentException("PasswordEncoder cannot be null");
        				
        }
        this.passwordEncoder = passwordEncoder;
    }
    
    

    
    public Customer get(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
	}
    
    
    
    public Customer create(CustomerDTO dto) throws IncorrectMobileException, IncorrectEmailException {
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password required");
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        Customer customer = new Customer(
            dto.getLogin(),
            hashedPassword,
            dto.getMobile(),
            dto.getEmail()
        );
        return customerRepository.save(customer);
    }

    
    public Customer update(Long id, CustomerDTO dto) throws IncorrectMobileException, IncorrectEmailException, EmptyLoginException {
    			Customer existingCustomer = get(id);
    			
    			if(existingCustomer == null) {
    				
					throw new RuntimeException("Customer not found with id: " + id);
					
				}
    			
    			else
    			{
    				existingCustomer.setName(dto.getName());
    				existingCustomer.setEmail(dto.getEmail());
    				existingCustomer.setLogin(dto.getLogin());
    				existingCustomer.setPassword(passwordEncoder.encode(dto.getPassword()));
    			}
    			return customerRepository.save(existingCustomer);
    }
    
    public Customer login(String login, String rawPassword) {
        Customer customer = customerRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, customer.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }
        

        return customer;
    }
    

    public Customer changePasswordAndReturn(Customer customer, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, customer.getPasswordHash())) {
            throw new IllegalArgumentException("Неверный старый пароль");
        }

        customer.setPassword(passwordEncoder.encode(newPassword));
        return customerRepository.save(customer);
    }



    
    
    public void deleteById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
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
