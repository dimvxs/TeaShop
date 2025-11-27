package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to create or save a product review
 * with a rating that falls outside the allowed range (typically 1–5 stars).
 * <p>
 * The rating in {@link com.goldenleaf.shop.model.Review} is a mandatory integer value
 * representing customer satisfaction. Valid ratings are strictly defined as:
 * <ul>
 *   <li>Minimum: 1 (lowest satisfaction)</li>
 *   <li>Maximum: 5 (highest satisfaction)</li>
 * </ul>
 * Values outside this range (0, negative numbers, 6+, fractional values, or {@code null})
 * are considered invalid and violate the business rules of the review system.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>User selects an invalid number of stars in the review form (e.g., due to UI bug or manipulation)</li>
 *   <li>Calling {@link com.goldenleaf.shop.model.Customer#makeReview} or 
 *       {@link com.goldenleaf.shop.model.Review#setRating(int)} with an out-of-range value</li>
 *   <li>Admin tool or API endpoint receiving malformed rating data</li>
 *   <li>Data import/migration process containing incorrect rating values</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to explicitly handle
 * invalid ratings. It represents a recoverable validation error commonly occurring
 * during user-generated content submission — not a programming defect.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Review
 * @see com.goldenleaf.shop.model.Review#setRating(int)
 * @see com.goldenleaf.shop.model.Customer#makeReview
 * @since 1.0
 */
public class IncorrectRatingException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new incorrect-rating exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Rating must be between 1 and 5")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public IncorrectRatingException(String message) {
        super(message);
    }

    /**
     * Constructs a new incorrect-rating exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping lower-level validation errors (e.g., parsing failure,
     * constraint violation, or third-party form validation issue).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public IncorrectRatingException(String message, Throwable cause) {
        super(message, cause);
    }
}