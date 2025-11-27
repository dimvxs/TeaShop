package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to create or update an entity
 * that requires a non-empty name (full name, category name, product name, etc.)
 * but the provided name is {@code null}, empty, or consists only of whitespace.
 * <p>
 * The {@code name} field is mandatory in many domain entities:
 * <ul>
 *   <li>{@link com.goldenleaf.shop.model.User} and its subclasses (Customer, Admin) – full name</li>
 *   <li>{@link com.goldenleaf.shop.model.Category} – category title</li>
 *   <li>{@link com.goldenleaf.shop.model.Product} – product title</li>
 *   <li>{@link com.goldenleaf.shop.model.CreditCard} – cardholder name</li>
 * </ul>
 * Empty names would violate data integrity, search functionality, and user experience.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>User registration or profile update with blank full name</li>
 *   <li>Creating a category/product without entering a name</li>
 *   <li>Adding a credit card with missing cardholder name</li>
 *   <li>Admin tools or data import processes submitting records with empty names</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to handle the missing name
 * explicitly. It represents an expected, recoverable validation error in normal application flow.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.User
 * @see com.goldenleaf.shop.model.Category
 * @see com.goldenleaf.shop.model.Product
 * @see com.goldenleaf.shop.model.CreditCard#setHolderName(String)
 * @since 1.0
 */
public class EmptyNameException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty-name exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Name cannot be null or empty")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public EmptyNameException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty-name exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping a lower-level technical exception
     * (e.g., constraint violation, parsing error, validation framework issue).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public EmptyNameException(String message, Throwable cause) {
        super(message, cause);
    }
}