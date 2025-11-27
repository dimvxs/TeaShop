package com.goldenleaf.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.model.ShoppingCart;
import com.goldenleaf.shop.model.ShoppingItem;

/**
 * Spring Data JPA repository for {@link ShoppingItem} entities (also known as CartItem).
 * <p>
 * Provides standard CRUD operations inherited from {@link JpaRepository}
 * and specialized query methods for managing items within a customer's shopping cart.
 * </p>
 * <p>
 * A {@link ShoppingItem} represents a single product entry in a {@link ShoppingCart}
 * with a specific quantity. The combination of {@code cart} and {@code product}
 * must be unique — a customer cannot add the same product twice.
 * </p>
 * <p>
 * This repository is essential for:
 * <ul>
 *   <li>Loading cart contents on every authenticated request</li>
 *   <li>Adding, updating, or removing items from the cart</li>
 *   <li>Validating stock availability during checkout</li>
 *   <li>Calculating cart totals and applying promotions</li>
 * </ul>
 * </p>
 * <p>
 * This repository is automatically implemented by Spring at runtime —
 * no manual implementation required.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see ShoppingItem
 * @see ShoppingCart
 * @see Product
 * @see JpaRepository
 * @since 1.0
 */
@Repository
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {

    /**
     * Finds a shopping cart item by the associated product.
     * <p>
     * Used to check if a product is already in the cart before adding it again.
     * In a properly designed system, only one {@link ShoppingItem} per product
     * should exist in a given cart.
     * </p>
     * <p>
     * Typical use case: "Add to Cart" button — increase quantity if exists,
     * create new item if not.
     * </p>
     *
     * @param product the product to search for in any cart
     * @return an {@link Optional} containing the matching {@link ShoppingItem} if found,
     *         or {@link Optional#empty()} if the product is not in any cart
     */
    Optional<ShoppingItem> findByProduct(Product product);

    /**
     * Finds all shopping items belonging to a specific shopping cart.
     * <p>
     * This is the primary method for loading the full contents of a customer's cart.
     * Usually ordered by creation date or product name in the service layer.
     * </p>
     *
     * @param cart the shopping cart to retrieve items from (must not be null)
     * @return a list of all {@link ShoppingItem}s in the cart (may be empty)
     */
    List<ShoppingItem> findByCart(ShoppingCart cart);

    /* Highly recommended additional methods (uncomment when needed): */

    /**
     * Finds a specific item in a specific cart by product — the most common operation.
     * Prevents duplicate entries and enables quantity updates.
     */
    Optional<ShoppingItem> findByCartAndProduct(ShoppingCart cart, Product product);

    /**
     * Deletes all items from a cart — used on "Clear Cart" or account deletion.
     */
    void deleteByCart(ShoppingCart cart);

    /**
     * Checks if a product is already in the customer's cart.
     */
    boolean existsByCartAndProduct(ShoppingCart cart, Product product);

    /**
     * Finds items with quantity greater than available stock — for out-of-stock warnings.
     */
    /*
    @Query("SELECT si FROM ShoppingItem si WHERE si.product.stock < si.quantity")
    List<ShoppingItem> findItemsWithInsufficientStock();
    */
}