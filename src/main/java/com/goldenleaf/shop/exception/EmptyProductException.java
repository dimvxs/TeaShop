package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to perform an operation that requires
 * a valid {@link com.goldenleaf.shop.model.Product} instance, but the provided product
 * is {@code null}.
 * <p>
 * This exception is typically used in review-related logic where a review must be
 * unambiguously linked to an existing, persisted product. Allowing {@code null} products
 * would break referential integrity and lead to orphaned or meaningless reviews.
 * </p>
 * <p>
 * Common scenarios that trigger this exception:
 * <ul>
 *   <li>Calling {@link com.goldenleaf.shop.model.Customer#makeReview} with {@code null} product</li>
 *   <li>Creating a {@link com.goldenleaf.shop.model.Review} entity with a {@code null} product reference</li>
 *   <li>Adding a review via service/admin tools without selecting a product</li>
 *   <li>Validation failure during batch import of reviews where the product is missing</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to explicitly handle
 * the missing product case. It represents an expected, recoverable validation error
 * rather than a programming defect.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Review
 * @see com.goldenleaf.shop.model.Customer#makeReview
 * @see com.goldenleaf.shop.model.Product
 * @since 1.0
 */
public class EmptyProductException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty-product exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Product cannot be null when creating a review")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public EmptyProductException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty-product exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping a lower-level exception (e.g., entity not found,
     * persistence error, or validation framework issue).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public EmptyProductException(String message, Throwable cause) {
        super(message, cause);
    }
}