package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to create or update a credit card
 * without providing a valid expiry date (month/year).
 * <p>
 * The expiry date is a mandatory, non-null field for the {@link com.goldenleaf.shop.model.CreditCard}
 * entity. This exception ensures that only cards with a properly defined expiration
 * are accepted, preventing incomplete or invalid payment methods from being stored.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>User submits a payment method form without selecting or entering an expiry date</li>
 *   <li>Calling {@link com.goldenleaf.shop.model.CreditCard#setExpiry(java.time.YearMonth)}
 *       with {@code null}</li>
 *   <li>Validation failure during checkout, registration of a new card, or data import</li>
 *   <li>Business rule enforcement – cards must have a defined expiration date</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, requiring the caller to handle the situation
 * explicitly. It represents an expected, recoverable validation error rather than
 * a programming bug.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.CreditCard
 * @see com.goldenleaf.shop.model.CreditCard#setExpiry(java.time.YearMonth)
 * @see com.goldenleaf.shop.model.CreditCard#getExpiry()
 * @since 1.0
 */
public class EmptyExpiryException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty-expiry exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Credit card expiry date cannot be null")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public EmptyExpiryException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty-expiry exception with the specified detail message and cause.
     * <p>
     * Useful when this validation exception wraps a lower-level technical exception
     * (e.g., parsing error, constraint violation, or payment gateway issue).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public EmptyExpiryException(String message, Throwable cause) {
        super(message, cause);
    }
}