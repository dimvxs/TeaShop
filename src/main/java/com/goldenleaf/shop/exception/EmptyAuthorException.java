package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to create or process a review
 * without a valid author (customer).
 * <p>
 * This typically occurs in the {@link com.goldenleaf.shop.model.Review} entity
 * or in methods such as {@link com.goldenleaf.shop.model.Customer#makeReview}.
 * The author field must reference a persisted {@link com.goldenleaf.shop.model.Customer}
 * and must not be {@code null}.
 * </p>
 * <p>
 * Example scenarios that trigger this exception:
 * <ul>
 *   <li>Passing {@code null} as the customer when creating a review</li>
 *   <li>Trying to save a review with an unassigned or detached author</li>
 *   <li>Business logic violation where anonymous reviews are not allowed</li>
 * </ul>
 * </p>
 *
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to handle
 * or declare it — appropriate for validation errors that are recoverable
 * and expected in normal application flow.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Review
 * @see com.goldenleaf.shop.model.Customer#makeReview
 * @since 1.0
 */
public class EmptyAuthorException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty author exception with the specified detail message.
     *
     * @param message the detail message explaining why the author is invalid
     *                (e.g., "Review author (customer) cannot be null")
     *                The message is saved for later retrieval by {@link #getMessage()}.
     */
    public EmptyAuthorException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty author exception with the specified detail message
     * and cause.
     * <p>
     * Useful when this exception is a wrapper around a lower-level exception
     * (e.g., persistence or validation failure).
     * </p>
     *
     * @param message the detail message
     * @param cause   the cause of this exception (can be retrieved later by {@link #getCause()})
     * @since 1.2
     */
    public EmptyAuthorException(String message, Throwable cause) {
        super(message, cause);
    }
}