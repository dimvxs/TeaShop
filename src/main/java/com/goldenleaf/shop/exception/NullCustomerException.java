package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an operation requires a valid, non-null
 * {@link com.goldenleaf.shop.model.Customer} instance, but {@code null} is provided
 * or the customer reference is missing.
 * <p>
 * The {@code Customer} is a central entity in the shop domain. Many business operations
 * (adding items to cart, creating reviews, managing payment methods, placing orders,
 * applying bonus points, etc.) are performed in the context of an authenticated customer.
 * Passing {@code null} instead of a proper customer would break referentialIntegrity
 * and lead to inconsistent or orphaned data.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>Calling {@link com.goldenleaf.shop.model.CreditCard#setCustomer(Customer)} with {@code null}</li>
 *   <li>Attempting to add a payment method, review, or order item without an authenticated customer</li>
 *   <li>Service-layer methods receiving a {@code null} customer from a controller or repository</li>
 *   <li>Batch processing or data migration trying to associate entities with a non-existent customer</li>
 *   <li>Security context issues where the current user could not be resolved</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to explicitly handle
 * the missing customer case. It represents a recoverable validation/state error
 * that commonly occurs during user-bound operations.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Customer
 * @see com.goldenleaf.shop.model.CreditCard#setCustomer(Customer)
 * @see com.goldenleaf.shop.model.Review
 * @see com.goldenleaf.shop.model.ShoppingCart
 * @since 1.0
 */
public class NullCustomerException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new null-customer exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Customer cannot be null")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public NullCustomerException(String message) {
        super(message);
    }

    /**
     * Constructs a new null-customer exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping lower-level errors (e.g., {@link org.springframework.security.core.Authentication}
     * being null, entity-not-found issues, lazy-initialization failures, etc.).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public NullCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}