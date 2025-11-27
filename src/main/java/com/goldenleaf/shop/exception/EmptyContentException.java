package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to create or save a review
 * (or any other user-generated textual content) with an empty or missing text body.
 * <p>
 * The content of a {@link com.goldenleaf.shop.model.Review} is a mandatory field.
 * This exception guarantees that only meaningful reviews are persisted,
 * preventing spam, accidental submissions, or incomplete data.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>User submits a review form without typing any text</li>
 *   <li>Calling {@link com.goldenleaf.shop.model.Customer#makeReview} with {@code null},
 *       empty string or whitespace-only content</li>
 *   <li>Validation failure during batch import of reviews/comments</li>
 *   <li>Business rule enforcement – empty reviews are not allowed</li>
 * </ul>
 * </p>
 * <p>
 * Being a <strong>checked exception</strong>, the caller must explicitly handle it
 * or declare it in the {@code throws} clause. This is appropriate for expected,
 * recoverable validation errors in normal application flow.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Review
 * @see com.goldenleaf.shop.model.Customer#makeReview
 * @see com.goldenleaf.shop.model.Review#setContent(String)
 * @since 1.0
 */
public class EmptyContentException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty-content exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Review content cannot be null or empty")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public EmptyContentException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty-content exception with the specified detail message and cause.
     * <p>
     * Useful when this validation exception wraps a lower-level technical exception
     * (e.g., constraint violation, parsing error, etc.).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public EmptyContentException(String message, Throwable cause) {
        super(message, cause);
    }
}