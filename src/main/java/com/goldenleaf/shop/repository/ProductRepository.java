package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Product;

/**
 * Spring Data JPA repository for {@link Product} entities.
 * <p>
 * Provides full CRUD functionality inherited from {@link JpaRepository}
 * and custom query methods optimized for product catalog management and search.
 * </p>
 * <p>
 * Products are the core sellable items in the shop. Fast lookup by name and brand
 * is essential for:
 * <ul>
 *   <li>Admin panel operations</li>
 *   <li>Duplicate prevention during product creation</li>
 *   <li>Search functionality and filtering</li>
 *   <li>Import/export and data synchronization processes</li>
 * </ul>
 * </p>
 * <p>
 * This repository is automatically implemented by Spring at runtime — 
 * no manual implementation required.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see Product
 * @see JpaRepository
 * @since 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds a product by its exact name (case-sensitive).
     * <p>
     * Product names are typically unique within the system (or within a brand),
     * making this method ideal for exact-match lookups.
     * </p>
     * <p>
     * Used in:
     * <ul>
     *   <li>Product creation validation (prevent duplicates)</li>
     *   <li>Admin edit forms (load by name)</li>
     *   <li>URL slugs / SEO-friendly URLs</li>
     *   <li>Integration with external systems</li>
     * </ul>
     * </p>
     *
     * @param name the exact product name to search for
     * @return an {@link Optional} containing the matching {@link Product} if found,
     *         or {@link Optional#empty()} if no product has this name
     */
    Optional<Product> findByName(String name);

    /**
     * Finds a product by its brand name.
     * <p>
     * Useful when displaying all products from a specific manufacturer
     * or during brand-specific filtering in the catalog.
     * </p>
     * <p>
     * Note: Multiple products can share the same brand.
     * </p>
     *
     * @param brand the brand name to search for (exact match)
     * @return an {@link Optional} containing the first matching {@link Product},
     *         or {@link Optional#empty()} if none found.
     *         For multiple results, consider using {@code List<Product> findAllByBrand(String brand)}
     */
    Optional<Product> findByBrand(String brand);

    /**
     * Deletes a product by its name.
     * <p>
     * Convenience method that removes a product without loading it first.
     * Particularly useful in:
     * <ul>
     *   <li>Batch cleanup scripts</li>
     *   <li>Admin tools performing deletion by known name</li>
     *   <li>Data migration or deduplication processes</li>
     * </ul>
     * </p>
     * <p>
     * <strong>Warning:</strong> This is a destructive operation.
     * Consider soft-delete patterns or cascading effects (reviews, order history)
     * before using in production.
     * </p>
     *
     * @param name the name of the product to delete
     */
    void deleteByName(String name);

    /**
     * Recommended additional methods (uncomment when needed):
     */
    /*
    // Case-insensitive name search
    Optional<Product> findByNameIgnoreCase(String name);

    // Find all products by brand (more common than single result)
    List<Product> findAllByBrand(String brand);

    // Search by partial name (LIKE query)
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // Check existence without loading entity
    boolean existsByName(String name);
    */
}