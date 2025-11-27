package com.goldenleaf.shop.model;

import java.util.ArrayList;
import java.util.List;

import com.goldenleaf.shop.exception.EmptyProductException;
import com.goldenleaf.shop.exception.IncorrectPriceException;
import com.goldenleaf.shop.exception.IncorrectQuantityException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * <p>
 * Represents a shopping cart belonging to a {@link Customer}. The cart contains
 * multiple {@link ShoppingItem} objects, each storing a product and its quantity.
 * </p>
 *
 * <p>
 * The cart is responsible for maintaining item relationships, calculating the
 * total price, and handling item additions or removals.
 * </p>
 *
 * @see ShoppingItem
 * @see Customer
 * @see Product
 */
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    /**
     * <p>
     * Unique identifier of the shopping cart. Automatically generated.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    /**
     * <p>
     * List of items contained in this cart. Mapped by the {@code cart} field
     * inside {@link ShoppingItem}.
     * </p>
     * <p>
     * Cascade operations ensure that when the cart is persisted or deleted,
     * items follow these operations as well. Orphan removal removes items when
     * they are detached from the cart.
     * </p>
     *
     * @see ShoppingItem
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingItem> items = new ArrayList<>();

    /**
     * <p>
     * Customer that owns this shopping cart.
     * </p>
     *
     * @see Customer
     */
    @OneToOne(mappedBy = "shoppingCart")
    private Customer customer;

    /**
     * <p>
     * Total price of all items inside the cart. Always >= 0.
     * </p>
     */
    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    /**
     * <p>
     * Default constructor required by JPA.
     * </p>
     */
    public ShoppingCart() {}

    /**
     * <p>
     * Constructs a new {@code ShoppingCart} with predefined items, customer and total price.
     * </p>
     *
     * @param items list of shopping items
     * @param customer owner of the cart
     * @param totalPrice pre-calculated total price
     */
    public ShoppingCart(List<ShoppingItem> items, Customer customer, double totalPrice) {
        this.items = items;
        this.customer = customer;
        this.totalPrice = totalPrice;
    }

    /**
     * @return the unique identifier of this cart
     */
    public Long getId() {
        return id;
    }

    /**
     * @return a list of items inside the cart
     *
     * @see ShoppingItem
     */
    public List<ShoppingItem> getItems() {
        return items;
    }

    /**
     * <p>
     * Replaces the list of items with a new one and updates cart references.
     * </p>
     *
     * @param items new list of items
     *
     * @throws EmptyProductException if any item's product is null
     *
     * @see ShoppingItem
     */
    public void setItems(List<ShoppingItem> items) throws EmptyProductException {
        this.items = items;

        for (ShoppingItem item : items) {
            item.setCart(this);
        }
    }

    /**
     * <p>
     * Adds a new item to the cart based on a product and quantity.
     * </p>
     *
     * @param product the product to add
     * @param quantity number of units
     *
     * @throws EmptyProductException if the product is null
     * @throws IncorrectQuantityException if quantity < 1
     *
     * @see Product
     * @see ShoppingItem
     */
    public void addItem(Product product, int quantity)
            throws EmptyProductException, IncorrectQuantityException {

        ShoppingItem item = new ShoppingItem(product, quantity);
        item.setCart(this);
        items.add(item);
    } 

    /**
     * <p>
     * Removes the specified item from the cart and clears its cart reference.
     * </p>
     *
     * @param item the item to remove
     */
    public void removeItem(ShoppingItem item) {
        items.remove(item);
        item.setCart(null);
    }

    /**
     * <p>
     * Removes all items from the cart.
     * </p>
     */
    public void clear() {
        items.clear();
    }

    /**
     * <p>
     * Removes an item from the cart by its product.
     * </p>
     *
     * @param product the product whose item should be removed
     */
    public void removeItem(Product product) {
        if (product == null) return;

        ShoppingItem toRemove = null;

        for (ShoppingItem item : items) {
            if (item.getProduct().equals(product)) {
                toRemove = item;
                break;
            }
        }

        if (toRemove != null) {
            items.remove(toRemove);
            toRemove.setCart(null);
        }
    }

    /**
     * <p>
     * Assigns a customer to the cart.
     * </p>
     *
     * @param customer the customer to set
     *
     * @see Customer
     */
    public void setCustomer(Customer customer) {
        if (customer != null) {
            this.customer = customer;
        }
    }

    /**
     * <p>
     * Calculates and returns the total price of all items in the cart.
     * </p>
     *
     * @return sum of (product price × quantity) for every item
     *
     * @see ShoppingItem
     * @see Product
     */
    public double calculateTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * @return the stored total price field
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * <p>
     * Sets a new total price for the cart.
     * </p>
     *
     * @param totalPrice must be >= 0
     *
     * @throws IncorrectPriceException if price is negative
     */
    public void setTotalPrice(double totalPrice) throws IncorrectPriceException {
        if (totalPrice < 0) {
            throw new IncorrectPriceException("Total price should be greater than or equal to 0");
        }
        this.totalPrice = totalPrice;
    }
}
