package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to set or update a customer's
 * mobile phone number with an invalid, malformed, or otherwise unacceptable value.
 * <p>
 * The mobile phone number in {@link com.goldenleaf.shop.model.Customer} must meet
 * the application's validation requirements:
 * <ul>
 *   <li>Must not be {@code null}, empty, or consist only of whitespace</li>
 *   <li>Must contain a minimum number of digits (typically 9–15 depending on country)</li>
 *   <li>May be required to follow international format (e.g., starting with '+')</li>
 *   <li>Often validated using length, allowed characters, and basic pattern checks</li>
 * </ul>
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>User enters too few digits, includes letters, or uses incorrect formatting during registration</li>
 *   <li>Calling {@link com.goldenleaf.shop.model.Customer#setMobile(String)} 
 *       with an invalid value (e.g., "123", "+abc123", "   ")</li>
 *   <li>Admin tool or data import process attempting to assign a malformed phone number</li>
 *   <li>Validation failure before persisting or updating customer contact information</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, requiring explicit handling or declaration.
 * It represents an expected and recoverable validation error during user input
 * — very common in registration, profile update, and checkout flows.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Customer
 * @see com.goldenleaf.shop.model.Customer#setMobile(String)
 * @see com.goldenleaf.shop.model.Customer#getMobile()
 * @since 1.0
 */
public class IncorrectMobileException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new incorrect-mobile exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Invalid mobile phone number format")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public IncorrectMobileException(String message) {
        super(message);
    }

    /**
     * Constructs a new incorrect-mobile exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping lower-level validation failures (e.g., regex mismatch,
     * third-party phone validation library error, or constraint violation).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public IncorrectMobileException(String message, Throwable cause) {
        super(message, cause);
    }
}