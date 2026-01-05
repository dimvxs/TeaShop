package com.goldenleaf.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.model.ShoppingCart;
import com.goldenleaf.shop.model.ShoppingItem;
import com.goldenleaf.shop.repository.ShoppingItemRepository;

/**
 * Service class for managing {@link ShoppingItem} entities.
 * <p>
 * Provides business logic for retrieving, adding, updating, and deleting shopping items.
 * Supports operations by {@link ShoppingCart} and {@link Product}.
 * Acts as an intermediary between controllers and the {@link ShoppingItemRepository}.
 * </p>
 *
 * @see ShoppingItem
 * @see ShoppingItemRepository
 * @see ShoppingCart
 * @see Product
 */
@Service
public class ShoppingItemService {

    /**
     * Repository used for performing CRUD operations on {@link ShoppingItem} entities.
     */
    private final ShoppingItemRepository shoppingItemRepository;

    /**
     * Constructs a new {@code ShoppingItemService} with the provided repository.
     *
     * @param repo the repository used to perform operations on shopping items
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see ShoppingItemRepository
     */
    public ShoppingItemService(ShoppingItemRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("ShoppingItemRepository cannot be null");
        }
        this.shoppingItemRepository = repo;
    }

    /**
     * Retrieves all shopping items from the database.
     *
     * @return a {@link List} of all {@link ShoppingItem} entities
     *
     * @see ShoppingItemRepository#findAll()
     */
    public List<ShoppingItem> getAllShoppingItems() {
        return shoppingItemRepository.findAll();
    }

    /**
     * Retrieves all shopping items in a specific shopping cart.
     *
     * @param cart the {@link ShoppingCart} whose items are retrieved
     * @return a {@link List} of {@link ShoppingItem} in the cart
     *
     * @see ShoppingItemRepository#findByCart(ShoppingCart)
     */
    public List<ShoppingItem> getShoppingItemsByCart(ShoppingCart cart) {
        return shoppingItemRepository.findByCart(cart);
    }

    /**
     * Retrieves a {@link ShoppingItem} by its unique ID.
     *
     * @param id the ID of the shopping item
     * @return the {@link ShoppingItem} with the specified ID
     * @throws RuntimeException if no shopping item with the given ID exists
     *
     * @see ShoppingItemRepository#findById(Object)
     */
    public ShoppingItem getShoppingItemById(Long id) {
        return shoppingItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping item not found with id: " + id));
    }

    /**
     * Adds a new {@link ShoppingItem} to the database.
     *
     * @param item the shopping item to add
     *
     * @see ShoppingItemRepository#save(Object)
     */
    public ShoppingItem addShoppingItem(ShoppingItem item) {
        return shoppingItemRepository.save(item);
    }

    /**
     * Removes an existing {@link ShoppingItem} from the database.
     *
     * <p>If the shopping item exists (by ID), it will be deleted; otherwise, nothing happens.</p>
     *
     * @param item the shopping item to remove
     *
     * @see ShoppingItemRepository#delete(Object)
     * @see ShoppingItemRepository#existsById(Object)
     */
    public void removeShoppingItem(ShoppingItem item) {
        if (item != null && shoppingItemRepository.existsById(item.getId())) {
            shoppingItemRepository.delete(item);
        }
    }

    /**
     * Removes a {@link ShoppingItem} by its ID.
     *
     * @param id the ID of the shopping item to delete
     *
     * @see ShoppingItemRepository#deleteById(Object)
     */
    public void removeShoppingItemById(Long id) {
        shoppingItemRepository.deleteById(id);
    }

    /**
     * Removes a {@link ShoppingItem} associated with a specific {@link Product}.
     *
     * @param product the product whose shopping item should be deleted
     * @throws RuntimeException if no shopping item exists for the given product
     *
     * @see ShoppingItemRepository#findByProduct(Product)
     * @see ShoppingItemRepository#delete(Object)
     */
    public void removeShoppingItemByProduct(Product product) {
        shoppingItemRepository.findByProduct(product)
                .ifPresentOrElse(
                        shoppingItemRepository::delete,
                        () -> { throw new RuntimeException("Shopping item not found by product: " + product.getName()); }
                );
    }

    /**
     * Updates an existing {@link ShoppingItem}.
     *
     * <p>The shopping item must have a valid ID that exists in the database. Otherwise,
     * a {@link RuntimeException} is thrown.</p>
     *
     * @param item the shopping item to update
     * @throws RuntimeException if the shopping item does not exist or ID is null
     *
     * @see ShoppingItemRepository#save(Object)
     * @see ShoppingItemRepository#existsById(Object)
     */
    public ShoppingItem editShoppingItem(ShoppingItem item) {
        if (item.getId() == null || !shoppingItemRepository.existsById(item.getId())) {
            throw new RuntimeException("Shopping item not found");
        }
       return shoppingItemRepository.save(item);
    }
}
