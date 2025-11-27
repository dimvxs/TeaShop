package com.goldenleaf.shop.model;

import com.goldenleaf.shop.exception.EmptyProductException;
import com.goldenleaf.shop.exception.IncorrectQuantityException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * <p>
 * Represents an item inside a shopping cart. Each {@code ShoppingItem} binds a
 * specific {@link Product} with a {@link ShoppingCart} and stores the quantity
 * of that product selected by the user.
 * </p>
 *
 * <p>
 * The class ensures that the assigned product is never {@code null} and that
 * the quantity is always at least 1.
 * </p>
 *
 * @see Product
 * @see ShoppingCart
 */
@Entity
public class ShoppingItem {

    /**
     * <p>
     * Unique identifier of the shopping item. Automatically generated.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * <p>
     * The product assigned to this shopping item.
     * Cannot be {@code null}.
     * </p>
     *
     * @see Product
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * <p>
     * Quantity of the selected product.
     * Must be 1 or greater.
     * </p>
     */
    private int quantity;

    /**
     * <p>
     * The shopping cart that contains this item.
     * </p>
     *
     * @see ShoppingCart
     */
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart cart;

    /**
     * <p>
     * Default constructor required by JPA.
     * </p>
     */
    public ShoppingItem() {}

    /**
     * <p>
     * Constructs a new {@code ShoppingItem} with the given product and quantity.
     * Validates both parameters before assignment.
     * </p>
     *
     * @param product the product to assign; must not be {@code null}
     * @param quantity the quantity of the product; must be at least 1
     *
     * @throws EmptyProductException if {@code product} is {@code null}
     * @throws IncorrectQuantityException if {@code quantity} is less than 1
     *
     * @see Product
     */
    public ShoppingItem(Product product, int quantity)
            throws EmptyProductException, IncorrectQuantityException {

        if (product == null) {
            throw new EmptyProductException("Product cannot be null");
        }

        if (quantity < 1) {
            throw new IncorrectQuantityException("Quantity cannot be less than 1");
        }

        this.product = product;
        this.quantity = quantity;
    }

    /**
     * @return the unique ID of this shopping item
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the product assigned to this item
     *
     * @see Product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * <p>
     * Sets a new product for this shopping item.
     * </p>
     *
     * @param product the product to assign; must not be {@code null}
     *
     * @throws EmptyProductException if the provided product is {@code null}
     *
     * @see Product
     */
    public void setProduct(Product product) throws EmptyProductException {
        if (product == null) {
            throw new EmptyProductException("Product cannot be null");
        }
        this.product = product;
    }

    /**
     * <p>
     * Updates the quantity of this item.
     * </p>
     *
     * @param quantity must be at least 1
     *
     * @throws IncorrectQuantityException if {@code quantity} is less than 1
     */
    public void setQuantity(int quantity) throws IncorrectQuantityException {
        if (quantity < 1) {
            throw new IncorrectQuantityException("Quantity cannot be less than 1");
        }
        this.quantity = quantity;
    }

    /**
     * @return the shopping cart containing this item
     *
     * @see ShoppingCart
     */
    public ShoppingCart getCart() {
        return cart;
    }

    /**
     * <p>
     * Sets the cart that contains this shopping item.
     * </p>
     *
     * @param cart the shopping cart to associate with this item
     *
     * @see ShoppingCart
     */
    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
