package com.goldenleaf.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.ShoppingCart;
import com.goldenleaf.shop.repository.ShoppingCartRepository;

/**
 * Service class for managing {@link ShoppingCart} entities.
 * <p>
 * Provides business logic for retrieving, adding, updating, and deleting shopping carts.
 * Supports operations by {@link Customer} and cart ID.
 * Acts as an intermediary between controllers and the {@link ShoppingCartRepository}.
 * </p>
 *
 * @see ShoppingCart
 * @see ShoppingCartRepository
 * @see Customer
 */
@Service
public class ShoppingCartService {

    /**
     * Repository used for performing CRUD operations on {@link ShoppingCart} entities.
     */
    private final ShoppingCartRepository shoppingCartRepository;

    /**
     * Constructs a new {@code ShoppingCartService} with the provided repository.
     *
     * @param repo the repository used to perform operations on shopping carts
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see ShoppingCartRepository
     */
    public ShoppingCartService(ShoppingCartRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("ShoppingCartRepository cannot be null");
        }
        this.shoppingCartRepository = repo;
    }

    /**
     * Retrieves all shopping carts from the database.
     *
     * @return a {@link List} of all {@link ShoppingCart} entities
     *
     * @see ShoppingCartRepository#findAll()
     */
    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    /**
     * Retrieves a {@link ShoppingCart} by its unique ID.
     *
     * @param id the ID of the shopping cart
     * @return the {@link ShoppingCart} with the specified ID
     * @throws RuntimeException if no shopping cart with the given ID exists
     *
     * @see ShoppingCartRepository#findById(Object)
     */
    public ShoppingCart getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found with id: " + id));
    }

    /**
     * Retrieves a {@link ShoppingCart} for a specific {@link Customer}.
     *
     * @param customer the customer whose shopping cart is retrieved
     * @return the {@link ShoppingCart} for the given customer
     * @throws RuntimeException if no shopping cart exists for the given customer
     *
     * @see ShoppingCartRepository#findByCustomer(Customer)
     */
    public ShoppingCart getShoppingCartByCustomer(Customer customer) {
        return shoppingCartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found by customer: " + customer));
    }

    /**
     * Adds a new {@link ShoppingCart} to the database.
     *
     * @param cart the shopping cart to add
     *
     * @see ShoppingCartRepository#save(Object)
     */
    public ShoppingCart addShoppingCart(ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    /**
     * Removes an existing {@link ShoppingCart} from the database.
     *
     * <p>If the shopping cart exists (by ID), it will be deleted; otherwise, nothing happens.</p>
     *
     * @param cart the shopping cart to remove
     *
     * @see ShoppingCartRepository#delete(Object)
     * @see ShoppingCartRepository#existsById(Object)
     */
    public void removeShoppingCart(ShoppingCart cart) {
        if (cart != null && shoppingCartRepository.existsById(cart.getId())) {
            shoppingCartRepository.delete(cart);
        }
    }

    /**
     * Removes a {@link ShoppingCart} by its ID.
     *
     * @param id the ID of the shopping cart to delete
     *
     * @see ShoppingCartRepository#deleteById(Object)
     */
    public void removeShoppingCartById(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    /**
     * Removes a {@link ShoppingCart} associated with a specific {@link Customer}.
     *
     * @param customer the customer whose shopping cart should be deleted
     * @throws EmptyLoginException if no shopping cart exists for the given customer
     *
     * @see ShoppingCartRepository#findByCustomer(Customer)
     * @see ShoppingCartRepository#delete(Object)
     */
    public void removeShoppingCartByCustomer(Customer customer) throws EmptyLoginException {
        Optional<ShoppingCart> cart = shoppingCartRepository.findByCustomer(customer);
        if (cart.isPresent()) {
            shoppingCartRepository.delete(cart.get());
        } else {
            throw new EmptyLoginException("Customer not found with login: " + customer.getLogin());
        }
    }

    /**
     * Updates an existing {@link ShoppingCart}.
     *
     * <p>The shopping cart must have a valid ID that exists in the database. Otherwise,
     * a {@link RuntimeException} is thrown.</p>
     *
     * @param cart the shopping cart to update
     * @throws RuntimeException if the shopping cart does not exist or ID is null
     *
     * @see ShoppingCartRepository#save(Object)
     * @see ShoppingCartRepository#existsById(Object)
     */
    public ShoppingCart editShoppingCart(ShoppingCart cart) {
        if (cart.getId() == null || !shoppingCartRepository.existsById(cart.getId())) {
            throw new RuntimeException("Shopping cart not found");
        }
       return shoppingCartRepository.save(cart);
    }
}
