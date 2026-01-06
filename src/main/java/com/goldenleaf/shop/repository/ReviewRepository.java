package com.goldenleaf.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.model.Review;

/**
 * Spring Data JPA repository for {@link Review} entities.
 * <p>
 * Provides full CRUD operations inherited from {@link JpaRepository}
 * and rich query methods optimized for review management, moderation,
 * and display in the product catalog and customer profiles.
 * </p>
 * <p>
 * Reviews are user-generated content linking a {@link Customer} to a {@link Product}
 * with a rating (1–5) and optional text. Efficient access by customer, product,
 * or login is essential for:
 * <ul>
 *   <li>Displaying reviews on product pages</li>
 *   <li>Showing "My Reviews" section in customer profile</li>
 *   <li>Admin moderation tools</li>
 *   <li>Calculating average product ratings</li>
 * </ul>
 * </p>
 * <p>
 * This repository is automatically implemented by Spring at runtime —
 * no manual implementation required.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see Review
 * @see Customer
 * @see Product
 * @see JpaRepository
 * @since 1.0
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Finds all reviews written by a specific customer.
     * <p>
     * Used in the customer profile ("My Reviews") and for reputation/engagement analytics.
     * </p>
     *
     * @param customer the customer who wrote the reviews (must not be null)
     * @return a list of reviews by this customer (may be empty)
     */
    List<Review> findByAuthor(Customer customer);

    /**
     * Finds all reviews written by a customer identified by their login.
     * <p>
     * Useful when only the login is known (e.g., from authentication context)
     * and full {@link Customer} entity is not loaded.
     * </p>
     *
     * @param login the customer's login/username
     * @return a list of reviews written by the customer with this login
     */
    List<Review> findByAuthorLogin(String login);

    /**
     * Finds all reviews for a specific product.
     * <p>
     * Primary method for displaying reviews on product detail pages
     * and calculating average rating and review count.
     * </p>
     *
     * @param product the product being reviewed (must not be null)
     * @return a list of all reviews for this product (ordered by date descending by default)
     */
    List<Review> findByProduct(Product product);

    /**
     * Finds all reviews for a product by its name.
     * <p>
     * Convenient when the {@link Product} entity is not loaded,
     * but the name is known (e.g., from URL slug or search).
     * </p>
     *
     * @param name the exact product name
     * @return a list of reviews for the product with this name
     */
    List<Review> findByProductName(String name);

    /**
     * Deletes all reviews associated with a given product.
     * <p>
     * Used when a product is permanently removed from the catalog.
     * Typically called within a service that handles cascading deletion.
     * </p>
     * <p>
     * <strong>Warning:</strong> This is a destructive operation affecting user-generated content.
     * Consider soft-delete, archiving, or anonymization instead.
     * </p>
     *
     * @param product the product whose reviews should be deleted
     */
    void deleteByProduct(Product product);

    /**
     * Deletes all reviews for a product identified by its name.
     * <p>
     * Convenience method for batch operations when only the product name is available.
     * </p>
     *
     * @param name the name of the product whose reviews should be deleted
     */
    void deleteByProductName(String name);

	List<Review> findByProductId(Long productId);

    /* Recommended additional methods (uncomment when needed): */
    /*
    // Average rating for a product
    @Query("SELECT COALESCE(AVG(r.rating), 0.0) FROM Review r WHERE r.product = :product")
    Double getAverageRatingByProduct(@Param("product") Product product);

    // Count reviews for a product
    Long countByProduct(Product product);

    // Find reviews with rating ≥ 4 (for "Top Reviews" section)
    List<Review> findByProductAndRatingGreaterThanEqual(Product product, int minRating);

    // Case-insensitive product name search
    List<Review> findByProductNameContainingIgnoreCase(String name);
    */
}