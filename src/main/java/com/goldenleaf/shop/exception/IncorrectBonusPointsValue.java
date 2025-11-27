package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to set or add an invalid number
 * of bonus points for a {@link com.goldenleaf.shop.model.Customer}.
 * <p>
 * According to the current business rules, bonus points must always be
 * <strong>non-negative</strong> (≥ 0). Negative values are not allowed because they
 * would represent debt or an illogical state in the loyalty program.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>Creating a {@link com.goldenleaf.shop.model.Customer} with negative initial bonus points</li>
 *   <li>Calling {@link com.goldenleaf.shop.model.Customer#setBonusPoints(int)} 
 *       or {@link com.goldenleaf.shop.model.Customer#addBonusPoints(int)} with a negative value</li>
 *   <li>Administrative tools or data imports mistakenly assigning negative points</li>
 *   <li>Attempting to deduct more points than the customer currently has
 *       (should be handled separately, but may also result in this exception)</li>
 * </ul>
 * </p>
 * <p>
 * This is a <strong>checked exception</strong>, requiring the caller to explicitly handle
 * the invalid value. It represents a recoverable business-rule violation rather than
 * a programming error.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Customer
 * @see com.goldenleaf.shop.model.Customer#setBonusPoints(int)
 * @see com.goldenleaf.shop.model.Customer#addBonusPoints(int)
 * @since 1.0
 */
public class IncorrectBonusPointsValue extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new incorrect-bonus-points exception with the specified detail message.
     *
     * @param message the detail message (e.g. "Bonus points cannot be negative")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public IncorrectBonusPointsValue(String message) {
        super(message);
    }

    /**
     * Constructs a new incorrect-bonus-points exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping a lower-level exception (e.g., arithmetic error,
     * validation framework issue, or data parsing problem).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public IncorrectBonusPointsValue(String message, Throwable cause) {
        super(message, cause);
    }
}