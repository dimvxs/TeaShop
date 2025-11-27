package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to set or update a product price
 * (or any monetary amount) with an invalid or unacceptable value.
 * <p>
 * According to the current business rules, a price must satisfy the following constraints:
 * <ul>
 *   <li>Must be greater than or equal to zero (negative prices are not allowed)</li>
 *   <li>Cannot be {@code null} when persisting a {@link com.goldenleaf.shop.model.Product}</li>
 *   <li>Typically represented as {@link java.math.BigDecimal} to avoid floating-point precision issues</li>
 *   <li>May have additional restrictions (e.g., maximum value, specific scale/precision)</li>
 * </ul>
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>Admin or seller enters a negative price while creating/updating a product</li>
 *   <li>Calling {@code Product#setPrice(BigDecimal)} with {@code null} or a negative value</li>
 *   <li>Data import or integration process submitting invalid pricing information</li>
 *   <li>Dynamic pricing engine generating an out-of-range or malformed price</li>
 *   <li>Discount/promotion logic accidentally resulting in a negative final price</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, forcing the caller to explicitly handle
 * invalid pricing situations. It represents a recoverable business-rule violation
 * that commonly occurs during product management and order processing.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Product
 * @see com.goldenleaf.shop.model.Product#setPrice(java.math.BigDecimal)
 * @since 1.0
 */
public class IncorrectPriceException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new incorrect-price exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Price cannot be negative or null")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public IncorrectPriceException(String message) {
        super(message);
    }

    /**
     * Constructs a new incorrect-price exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping lower-level errors (e.g., arithmetic exceptions,
     * validation framework failures, or constraint violations).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public IncorrectPriceException(String message, Throwable cause) {
        super(message, cause);
    }
}