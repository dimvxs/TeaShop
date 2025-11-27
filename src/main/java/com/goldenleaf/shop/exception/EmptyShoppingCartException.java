package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to perform an operation that requires
 * a valid, non-null {@link com.goldenleaf.shop.model.ShoppingCart} instance,
 * but the provided shopping cart is {@code null}.
 * <p>
 * The shopping cart is a mandatory one-to-one association for every
 * {@link com.goldenleaf.shop.model.Customer}. Certain business operations
 * (e.g. checkout, order creation, applying bonus points, calculating totals)
 * cannot proceed without an active cart.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>Creating a {@link com.goldenleaf.shop.model.Customer} with {@code null} shopping cart
 *       when the application policy requires an initialized cart</li>
 *   <li>Calling service methods (checkout, apply discount, etc.) on a customer
 *       whose cart has not been created or was accidentally set to {@code null}</li>
 *   <li>Administrative tools or data-migration processes trying to process
 *       a customer without an associated cart</li>
 *   <li>Attempting to add items to or retrieve items from a non-existent cart</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to handle the missing cart
 * explicitly. It represents an expected, recoverable validation/state error
 * rather than a programming bug.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Customer
 * @see com.goldenleaf.shop.model.Customer#setShoppingCart(com.goldenleaf.shop.model.ShoppingCart)
 * @see com.goldenleaf.shop.model.ShoppingCart
 * @since 1.0
 */
public class EmptyShoppingCartException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty-shopping-cart exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Shopping cart cannot be null")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public EmptyShoppingCartException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty-shopping-cart exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping a lower-level exception (e.g., lazy-initialization failure,
     * entity-not-found error, or persistence issue).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public EmptyShoppingCartException(String message, Throwable cause) {
        super(message, cause);
    }
}