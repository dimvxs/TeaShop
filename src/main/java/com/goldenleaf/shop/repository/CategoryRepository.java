package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Category;

/**
 * Spring Data JPA repository for {@link Category} entities.
 * <p>
 * Provides standard CRUD operations inherited from {@link JpaRepository}
 * and custom query methods for category-specific lookups.
 * </p>
 * <p>
 * Categories are used to organize products into logical groups
 * (e.g., "Electronics", "Clothing", "Books"). The {@code name} field is unique
 * across the system, making it a natural key for fast lookups.
 * </p>
 * <p>
 * This repository is automatically implemented by Spring at runtime
 * using proxy-based mechanism — no manual implementation required.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see Category
 * @see JpaRepository
 * @since 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Finds a category by its exact name (case-sensitive).
     * <p>
     * Since category names are unique in the system, this method returns
     * at most one result wrapped in an {@link Optional}.
     * </p>
     * <p>
     * Typical use cases:
     * <ul>
     *   <li>Checking if a category already exists before creating a new one</li>
     *   <li>Retrieving a category for product assignment during creation/update</li>
     *   <li>Admin panel operations (edit, delete, view products in category)</li>
     *   <li>URL-friendly slugs or navigation menu generation</li>
     * </ul>
     * </p>
     *
     * @param name the exact name of the category to search for (not null, not blank)
     * @return an {@link Optional} containing the matching {@link Category} if found,
     *         or {@link Optional#empty()} if no category with this name exists
     */
    Optional<Category> findByName(String name);

    /**
     * Optional: case-insensitive search variant (uncomment if needed).
     * <p>
     * Useful when user input may vary in case (e.g., "electronics" vs "Electronics").
     * </p>
     */
    /*
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Category> findByNameIgnoreCase(@Param("name") String name);
    */

    /**
     * Optional: checks existence without loading the full entity.
     * <p>
     * More efficient when only presence check is needed.
     * </p>
     */
    // boolean existsByName(String name);
}