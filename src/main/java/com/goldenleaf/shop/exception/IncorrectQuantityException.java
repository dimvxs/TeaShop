package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to set or update a quantity
 * (e.g., product stock, items in cart, order line quantity) with an invalid or
 * unacceptable value.
 * <p>
 * According to the business rules of the shop, quantity values must always satisfy:
 * <ul>
 *   <li>Be greater than or equal to zero (negative quantities are not allowed)</li>
 *   <li>Not exceed available stock when adding to cart or creating orders</li>
 *   <li>Be a positive integer when placing an order (usually ≥ 1)</li>
 *   <li>Never be {@code null} in persisted entities</li>
 * </ul>
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>User tries to add a negative or zero number of items to the shopping cart</li>
 *   <li>Attempting to order more items than currently available in stock</li>
 *   <li>Admin or warehouse tool mistakenly setting negative stock levels</li>
 *   <li>Data import or integration process submitting invalid quantity values</li>
 *   <li>Cart manipulation or checkout validation detecting inconsistent quantities</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to handle invalid
 * quantity situations explicitly. It represents a recoverable business-rule violation
 * that frequently occurs during cart operations, order placement, and inventory management.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.CartItem
 * @see com.goldenleaf.shop.model.Product#setStock(int)
 * @see com.goldenleaf.shop.model.OrderLine#setQuantity(int)
 * @since 1.0
 */
public class IncorrectQuantityException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new incorrect-quantity exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Quantity cannot be negative or zero")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public IncorrectQuantityException(String message) {
        super(message);
    }

    /**
     * Constructs a new incorrect-quantity exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping lower-level errors (e.g., arithmetic overflow,
     * stock-check failure, validation framework issue, or constraint violation).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public IncorrectQuantityException(String message, Throwable cause) {
        super(message, cause);
    }
}