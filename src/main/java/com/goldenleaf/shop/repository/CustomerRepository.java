package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.model.ShoppingCart;
import com.goldenleaf.shop.model.User;

/**
 * Spring Data JPA repository for {@link Customer} entities.
 * <p>
 * Provides standard CRUD operations inherited from {@link JpaRepository}
 * and additional query methods specific to customer management.
 * </p>
 * <p>
 * The {@code Customer} entity extends {@link User} and represents registered shoppers
 * in the system. Mobile phone number is used as a secondary unique identifier
 * (alongside login/email) for fast lookup and account recovery.
 * </p>
 * <p>
 * This repository is automatically implemented by Spring at runtime — no manual implementation required.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see Customer
 * @see JpaRepository
 * @since 1.0
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds a customer by their mobile phone number.
     * <p>
     * Mobile number is often used as an alternative identifier for:
     * <ul>
     *   <li>Login via phone (SMS OTP)</li>
     *   <li>Account recovery / password reset</li>
     *   <li>Duplicate check during registration</li>
     *   <li>Admin/support lookup by phone</li>
     * </ul>
     * </p>
     * <p>
     * Assumes mobile numbers are stored in normalized format (e.g., digits only or with country code).
     * </p>
     *
     * @param mobile the customer's mobile phone number (not null, not blank)
     * @return an {@link Optional} containing the matching {@link Customer} if found,
     *         or {@link Optional#empty()} if no customer has this mobile number
     */
    Optional<Customer> findByMobile(String mobile);
    

    /**
     * Deletes a customer by their mobile phone number.
     * <p>
     * Convenience method that removes a customer without loading the entity first.
     * Useful in scenarios such as:
     * <ul>
     *   <li>Account deletion request by phone identifier</li>
     *   <li>GDPR / right-to-be-forgotten compliance cleanup</li>
     *   <li>Admin tools performing bulk deletion by mobile</li>
     * </ul>
     * </p>
     * <p>
     * <strong>Warning:</strong> This is a destructive operation. Use with extreme caution
     * and ensure proper authorization, logging, and soft-delete consideration.
     * </p>
     *
     * @param mobile the mobile number of the customer to delete
     */
    void deleteByMobile(String mobile);
    /**
	 * Finds a customer by their unique login.
	 * <p>
	 * Login is the primary identifier used during authentication.
	 * </p>
	 *
	 * @param login the unique login/username
	 * @return an {@link Optional} containing the {@link Customer} if found,
	 *         or {@link Optional#empty()} if no customer has this login
	 */
    Optional<Customer> findByLogin(String login);



    /**
     * Optional: case-insensitive email lookup (commonly needed).
     */
    // Optional<Customer> findByEmailIgnoreCase(String email);

    /**
     * Optional: existence check by mobile (more efficient than find + isPresent).
     */
    // boolean existsByMobile(String mobile);
}