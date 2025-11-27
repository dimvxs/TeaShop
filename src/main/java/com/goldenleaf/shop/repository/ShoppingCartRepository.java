package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.ShoppingCart;

/**
 * Spring Data JPA repository for {@link ShoppingCart} entities.
 * <p>
 * Provides standard CRUD operations inherited from {@link JpaRepository}
 * and specialized query methods for shopping cart management.
 * </p>
 * <p>
 * The shopping cart has a strict one-to-one relationship with {@link Customer} relationship.
 * Each customer has exactly one active cart at any time. This repository enables
 * fast retrieval and cleanup of carts by customer association.
 * </p>
 * <p>
 * Key use cases:
 * <ul>
 *   <li>Restoring cart contents after user login</li>
 *   <li>Displaying cart summary in header/mini-cart</li>
 *   <li>Checkout process – retrieving cart for order creation</li>
 *   <li>Scheduled cleanup of abandoned carts</li>
 *   <li>GDPR/account deletion – removing associated cart</li>
 * </ul>
 * </p>
 * <p>
 * This repository is automatically implemented by Spring at runtime —
 * no manual implementation required.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see ShoppingCart
 * @see Customer
 * @see JpaRepository
 * @since 1.0
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    /**
     * Finds the active shopping cart for a given customer.
     * <p>
     * Since the cart is uniquely tied to the customer (one-to-one),
     * this method returns at most one result.
     * </p>
     * <p>
     * Used in virtually every authenticated user request to load or update the cart.
     * </p>
     *
     * @param customer the customer whose cart to retrieve (must not be null)
     * @return an {@link Optional} containing the customer's {@link ShoppingCart} if exists,
     *         or {@link Optional#empty()} if the customer has no cart (should not happen in normal flow)
     */
    Optional<ShoppingCart> findByCustomer(Customer customer);

    /**
     * Deletes the shopping cart associated with a specific customer.
     * <p>
     * Used during:
     * <ul>
     *   <li>Account deletion (GDPR compliance)</li>
     *   <li>Manual cart reset (e.g., "Clear cart" button)</li>
     *   <li>Admin-initiated cleanup</li>
     * </ul>
     * </p>
     * <p>
     * <strong>Important:</strong> This method also cascades deletion of all {@link CartItem}s
     * if properly configured in the entity (orphanRemoval = true).
     * </p>
     *
     * @param customer the customer whose cart should be deleted
     */
    void deleteByCustomer(Customer customer);

    /* Recommended additional useful methods (uncomment when needed): */
    /*
    // Check if customer already has a cart (more efficient than find + isPresent)
    boolean existsByCustomer(Customer customer);

    // Find abandoned carts (no activity for X days) – for cleanup/email campaigns
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.lastModified < :cutoffDate")
    List<ShoppingCart> findAbandonedCarts(@Param("cutoffDate") LocalDateTime cutoffDate);

    // Find carts with total value > X (for marketing analytics)
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.totalPrice > :minValue")
    List<ShoppingCart> findHighValueCarts(@Param("minValue") BigDecimal minValue);
    */
}