package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.User;

/**
 * Spring Data JPA repository for the {@link User} entity hierarchy.
 * <p>
 * Serves as a common access point for both {@link com.goldenleaf.shop.model.Customer}
 * and {@link com.goldenleaf.shop.model.Admin} entities, since they both extend {@link User}.
 * </p>
 * <p>
 * Provides generic CRUD operations and utility methods that work across all user types.
 * Specific queries (e.g., by mobile, secret word, etc.) are defined in dedicated repositories
 * ({@code CustomerRepository}, {@code AdminRepository}).
 * </p>
 * <p>
 * This repository is particularly useful for:
 * <ul>
 *   <li>Authentication & authorization (finding user by login)</li>
 *   <li>Global search by name across all users</li>
 *   <li>Admin tools managing any type of user account</li>
 *   <li>Account deletion by login identifier</li>
 * </ul>
 * </p>
 * <p>
 * Automatically implemented by Spring at runtime — no implementation class needed.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see User
 * @see com.goldenleaf.shop.model.Customer
 * @see com.goldenleaf.shop.model.Admin
 * @see JpaRepository
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user (Customer or Admin) by their full name.
     * <p>
     * Useful for global search functionality in admin panels or support tools.
     * Note: Names are not guaranteed to be unique, so at most one result is returned
     * (first match by ID order).
     * </p>
     *
     * @param name the full name to search for (case-sensitive)
     * @return an {@link Optional} containing the user if found
     */
    Optional<User> findByName(String name);

    /**
     * Finds a user (Customer or Admin) by their unique login.
     * <p>
     * This is the primary method used during authentication.
     * Login is unique across the entire system.
     * </p>
     *
     * @param login the unique login/username
     * @return an {@link Optional} containing the {@link User} if found,
     *         or {@link Optional#empty()} if no user has this login
     */
    Optional<User> findByLogin(String login);

    /**
     * Deletes a user by their login.
     * <p>
     * Convenience method for account removal without loading the full entity first.
     * Used in:
     * <ul>
     *   <li>Admin-initiated user deletion</li>
     *   <li>GDPR "right to be forgotten" requests</li>
     *   <li>Automated cleanup scripts</li>
     * </ul>
     * </p>
     * <p>
     * <strong>Warning:</strong> This is a destructive operation.
     * Ensure proper authorization, auditing, and cascading/soft-delete logic.
     * </p>
     *
     * @param login the login of the user to delete
     */
    void deleteByLogin(String login);

    /* Recommended additional methods (uncomment when needed): */

    /*
    // Case-insensitive login search (common in login forms)
    Optional<User> findByLoginIgnoreCase(String login);

    // Check if login already exists (more efficient than find + isPresent)
    boolean existsByLogin(String login);

    // Find all users with a specific role (if using @DiscriminatorColumn)
    List<User> findByType(String discriminatorValue);
    */
}