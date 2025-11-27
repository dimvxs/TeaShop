package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to create or persist a user
 * (customer, admin, etc.) without specifying the date of the last activity.
 * <p>
 * The {@code lastActivity} field in the base {@link com.goldenleaf.shop.model.User}
 * entity represents the date/time of the most recent user interaction
 * (login, order placement, profile update, etc.). 
 * Although it can be automatically set to the current date on first login,
 * certain business rules or data-import scenarios require an explicit value.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>Creating a {@link com.goldenleaf.shop.model.User} (or subclass) with {@code null} lastActivity
 *       when the application policy forbids it</li>
 *   <li>Importing/migrating historical user data where the last activity date is mandatory</li>
 *   <li>Validation in administrative tools that enforce complete audit information</li>
 *   <li>Custom user creation workflows that require an explicit initial activity date</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to handle the missing
 * last-activity date explicitly. It is intended for expected, recoverable validation
 * situations rather than programming errors.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.User
 * @see com.goldenleaf.shop.model.User#setLastActivity(java.time.LocalDate)
 * @since 1.0
 */
public class EmptyLastActivityException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty-last-activity exception with the specified detail message.
     *
     * @param message the detail message (e.g. "User last activity date cannot be null")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public EmptyLastActivityException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty-last-activity exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping a lower-level exception (e.g., constraint violation,
     * data parsing error, or migration tool failure).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public EmptyLastActivityException(String message, Throwable cause) {
        super(message, cause);
    }
}