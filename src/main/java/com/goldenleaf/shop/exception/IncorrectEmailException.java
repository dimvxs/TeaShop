package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to set or update a customer's
 * email address with an invalid, malformed, or otherwise unacceptable value.
 * <p>
 * The email address in {@link com.goldenleaf.shop.model.Customer} must satisfy
 * strict validation rules:
 * <ul>
 *   <li>Must not be {@code null}, empty, or whitespace-only</li>
 *   <li>Must contain at least one {@code '@'} symbol and a domain part</li>
 *   <li>Must conform to common email format standards (e.g., local-part@domain.tld)</li>
 *   <li>Must be unique across all customers (enforced at DB level and in business logic)</li>
 * </ul>
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>User enters an incorrectly formatted email during registration or profile update</li>
 *   <li>Calling {@link com.goldenleaf.shop.model.Customer#setEmail(String)} 
 *       with an invalid value (e.g., "user@", "user.domain", "user@.com")</li>
 *   <li>Admin or data import tool attempting to assign a malformed email</li>
 *   <li>Validation failure before persisting a customer entity</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to handle the invalid email
 * explicitly. It represents a recoverable business/validation error that commonly occurs
 * during user input — not a programming defect.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Customer
 * @see com.goldenleaf.shop.model.Customer#setEmail(String)
 * @see com.goldenleaf.shop.model.Customer#getEmail()
 * @since 1.0
 */
public class IncorrectEmailException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new incorrect-email exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Invalid or malformed email address")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public IncorrectEmailException(String message) {
        super(message);
    }

    /**
     * Constructs a new incorrect-email exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping lower-level validation errors (e.g., regex failure,
     * constraint violation, or third-party validation library exception).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public IncorrectEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}