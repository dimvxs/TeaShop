package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an operation requires a valid, non-null payment method
 * (typically a {@link com.goldenleaf.shop.model.CreditCard} or other payment entity),
 * but {@code null} is supplied or the payment reference is missing.
 * <p>
 * Payment information is mandatory for critical operations such as order checkout,
 * payment processing, or associating a saved card with a customer. Allowing {@code null}
 * payments would result in incomplete orders, failed transactions, or orphaned data.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>Attempting to place an order without selecting a payment method</li>
 *   <li>Calling constructors or service methods that expect a payment object with {@code null}</li>
 *   <li>Creating a {@link com.goldenleaf.shop.model.Customer} with a {@code null} payments list
 *       when the business rules forbid it</li>
 *   <li>Checkout flow where the selected {@link com.goldenleaf.shop.model.CreditCard} 
 *       could not be loaded (e.g., removed concurrently)</li>
 *   <li>Batch processing, admin tools, or data migration trying to process transactions
 *       without an associated payment method</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to explicitly handle
 * the missing payment case. It represents a recoverable validation/state error
 * that commonly occurs during checkout and order finalization.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.CreditCard
 * @see com.goldenleaf.shop.model.Customer
 * @see com.goldenleaf.shop.model.Order
 * @since 1.0
 */
public class NullPaymentException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new null-payment exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Payment method cannot be null")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public NullPaymentException(String message) {
        super(message);
    }

    /**
     * Constructs a new null-payment exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping lower-level errors (e.g., entity not found,
     * lazy-initialization exception, payment gateway failure, etc.).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public NullPaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}