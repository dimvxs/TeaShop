package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to create or update a credit card
 * without providing a valid card number.
 * <p>
 * The card number is a mandatory, non-blank field for the {@link com.goldenleaf.shop.model.CreditCard}
 * entity. This exception enforces data integrity and prevents incomplete or invalid
 * payment method records from being persisted.
 * </p>
 * <p>
 * Common situations that trigger this exception:
 * <ul>
 *   <li>Passing {@code null}, empty string, or whitespace-only value to 
 *       {@link com.goldenleaf.shop.model.CreditCard#setCardNumber(String)}</li>
 *   <li>User submitting a payment method form with missing card number</li>
 *   <li>Validation failure during checkout or payment method registration</li>
 *   <li>Importing or migrating payment data with incomplete records</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, requiring explicit handling or declaration.
 * It represents a recoverable business/validation error rather than a programming defect.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.CreditCard
 * @see com.goldenleaf.shop.model.CreditCard#setCardNumber(String)
 * @see com.goldenleaf.shop.model.CreditCard#getCardNumber()
 * @since 1.0
 */
public class EmptyCardNumberException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message explaining the validation failure
     *                (e.g., "Credit card number cannot be null or empty")
     *                The message is saved for later retrieval by {@link #getMessage()}
     */
    public EmptyCardNumberException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * Useful when this exception wraps a lower-level technical exception
     * (e.g., from a payment gateway, validation framework, or constraint violation).
     * </p>
     *
     * @param message the detail message
     * @param cause   the original cause of the error
     * @since 1.2
     */
    public EmptyCardNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}