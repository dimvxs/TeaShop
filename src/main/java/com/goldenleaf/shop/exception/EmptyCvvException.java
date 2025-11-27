package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to process or temporarily store
 * a credit-card CVV (Card Verification Value) that is {@code null}, empty, or consists only of whitespace.
 * <p>
 * The CVV is a mandatory security code required for most card-not-present transactions.
 * This exception enforces that a CVV is supplied during payment processing
 * (even though it is never persisted to the database).
 * </p>
 * <p>
 * Common situations that trigger this exception:
 * <ul>
 *   <li>User submits a payment form without entering the CVV</li>
 *   <li>Calling {@link com.goldenleaf.shop.model.CreditCard#setCvv(String)} with {@code null},
 *       empty string, or only whitespace</li>
 *   <li>Validation step before sending data to a payment gateway</li>
 *   <li>Programmatic payment processing with incomplete card data</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to handle the missing CVV
 * explicitly — appropriate for expected, recoverable validation errors during checkout
 * or payment-method registration.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.CreditCard
 * @see com.goldenleaf.shop.model.CreditCard#setCvv(String)
 * @see com.goldenleaf.shop.model.CreditCard#clearCvv()
 * @since 1.0
 */
public class EmptyCvvException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty-CVV exception with the specified detail message.
     *
     * @param message the detail message (e.g. "CVV code cannot be null or empty")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public EmptyCvvException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty-CVV exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping a lower-level exception (e.g., validation framework error,
     * constraint violation, or payment-gateway response parsing issue).
     * </p>
     *
     * @param message the detail message
     * @param cause   the original cause of the error
     * @since 1.2
     */
    public EmptyCvvException(String message, Throwable cause) {
        super(message, cause);
    }
}