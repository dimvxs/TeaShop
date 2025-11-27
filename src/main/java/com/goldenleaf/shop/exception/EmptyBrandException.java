package com.goldenleaf.shop.exception;

/**
 * Checked exception thrown when an attempt is made to create or update a product
 * without specifying a valid brand.
 * <p>
 * The brand is a mandatory attribute for {@link com.goldenleaf.shop.model.Product}
 * entities in the system. This exception ensures data integrity by preventing
 * products from being persisted with {@code null} or blank brand values.
 * </p>
 * <p>
 * Typical scenarios that trigger this exception:
 * <ul>
 *   <li>Calling {@code new Product(...)} or a setter with {@code null} or empty brand</li>
 *   <li>Submitting a product creation form with missing brand information</li>
 *   <li>Validation failure during import or batch processing of products</li>
 * </ul>
 * </p>
 * <p>
 * Being a <strong>checked exception</strong>, it forces the caller to explicitly handle
 * the situation — appropriate for business rule violations that are expected
 * and recoverable in normal application flow.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see com.goldenleaf.shop.model.Product
 * @see com.goldenleaf.shop.model.Product#setBrand(String)
 * @since 1.0
 */
public class EmptyBrandException extends Exception {

    /** Serial version UID for serialization compatibility. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new empty brand exception with the specified detail message.
     *
     * @param message the detail message (e.g., "Product brand cannot be null or empty")
     *                Saved for later retrieval by {@link #getMessage()}
     */
    public EmptyBrandException(String message) {
        super(message);
    }

    /**
     * Constructs a new empty brand exception with the specified detail message and cause.
     * <p>
     * Useful when wrapping a lower-level exception (e.g., from validation frameworks
     * or database constraints).
     * </p>
     *
     * @param message the detail message
     * @param cause   the root cause of this exception
     * @since 1.2
     */
    public EmptyBrandException(String message, Throwable cause) {
        super(message, cause);
    }
}