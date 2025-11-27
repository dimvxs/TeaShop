package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to create, update, or authenticate
 * a user with a {@code null}, empty, or whitespace-only login.
 * <p>
 * The {@code login} field in the base {@link com.goldenleaf.shop.model.User} entity
 * (and all its subclasses — {@code Customer}, {@code Admin}, etc.) is a mandatory,
 * unique identifier used for authentication. Allowing empty logins would break
 * security, uniqueness constraints, and the entire authentication system.
 * </p>
 * <p>
 * Common scenarios that trigger this exception:
 * <ul>
 *   <li>User registration form submitted without entering a login/username</li>
 *   <li>Calling the {@link com.goldenleaf.shop.model.User} constructor or 
 *       {@code setLogin()} with {@code null}, empty string, or only whitespace</li>
 *   <li>Admin manually creating a user account with missing login</li>
 *   <li>Data import/migration process containing records with blank login values</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, requiring explicit handling or declaration.
 * It represents an expected, recoverable validation error — not a programming bug.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.User
 * @see com.goldenleaf.shop.model.User#setLogin(String)
 * @see com.goldenleaf.shop.model.User#getLogin()
 * @since 1.0
 */
public class EmptyLoginException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty-login exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Login cannot be null or empty")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public EmptyLoginException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty-login exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping lower-level errors (e.g., database unique constraint violation
     * during login assignment, validation framework errors, etc.).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public EmptyLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}