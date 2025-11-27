package com.goldenleaf.shop.service;

import java.util.List;

import com.goldenleaf.shop.model.Review;
import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.repository.ReviewRepository;

/**
 * Service class for managing {@link Review} entities.
 * <p>
 * Provides business logic for retrieving, adding, updating, and deleting reviews.
 * Supports filtering reviews by {@link Product}, {@link Customer}, and customer login.
 * Acts as an intermediary between controllers and the {@link ReviewRepository}.
 * </p>
 *
 * @see Review
 * @see ReviewRepository
 * @see Product
 * @see Customer
 */
public class ReviewService {

    /**
     * Repository used for performing CRUD operations on {@link Review} entities.
     */
    private final ReviewRepository reviewRepository;

    /**
     * Constructs a new {@code ReviewService} with the provided repository.
     *
     * @param repo the repository used to perform operations on reviews
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see ReviewRepository
     */
    public ReviewService(ReviewRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("ReviewRepository cannot be null");
        }
        this.reviewRepository = repo;
    }

    /**
     * Retrieves all reviews from the database.
     *
     * @return a {@link List} of all {@link Review} entities
     *
     * @see ReviewRepository#findAll()
     */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /**
     * Retrieves all reviews associated with a specific product.
     *
     * @param product the {@link Product} for which reviews are retrieved
     * @return a {@link List} of reviews for the specified product
     *
     * @see ReviewRepository#findByProduct(Product)
     */
    public List<Review> getAllReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    /**
     * Retrieves all reviews made by a specific customer.
     *
     * @param customer the {@link Customer} whose reviews are retrieved
     * @return a {@link List} of reviews by the specified customer
     *
     * @see ReviewRepository#findByCustomer(Customer)
     */
    public List<Review> getAllReviewsOfCustomer(Customer customer) {
        return reviewRepository.findByCustomer(customer);
    }

    /**
     * Retrieves all reviews made by a customer identified by their login.
     *
     * @param login the login of the customer
     * @return a {@link List} of reviews by the customer with the given login
     *
     * @see ReviewRepository#findByCustomerLogin(String)
     */
    public List<Review> getAllReviewsOfCustomerLogin(String login) {
        return reviewRepository.findByCustomerLogin(login);
    }

    /**
     * Retrieves a {@link Review} by its unique ID.
     *
     * @param id the ID of the review
     * @return the {@link Review} with the specified ID
     * @throws RuntimeException if no review with the given ID exists
     *
     * @see ReviewRepository#findById(Object)
     */
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
    }

    /**
     * Adds a new {@link Review} to the database.
     *
     * @param review the review to add
     *
     * @see ReviewRepository#save(Object)
     */
    public void addReview(Review review) {
        reviewRepository.save(review);
    }

    /**
     * Removes an existing {@link Review} from the database.
     *
     * <p>If the review exists (by ID), it will be deleted; otherwise, nothing happens.</p>
     *
     * @param review the review to remove
     *
     * @see ReviewRepository#delete(Object)
     * @see ReviewRepository#existsById(Object)
     */
    public void removeReview(Review review) {
        if (review != null && reviewRepository.existsById(review.getId())) {
            reviewRepository.delete(review);
        }
    }

    /**
     * Removes a {@link Review} by its ID.
     *
     * @param id the ID of the review to delete
     *
     * @see ReviewRepository#deleteById(Object)
     */
    public void removeReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    /**
     * Updates an existing {@link Review}.
     *
     * <p>The review must have a valid ID that exists in the database. Otherwise,
     * a {@link RuntimeException} is thrown.</p>
     *
     * @param review the review to update
     * @throws RuntimeException if the review does not exist or ID is null
     *
     * @see ReviewRepository#save(Object)
     * @see ReviewRepository#existsById(Object)
     */
    public void editReview(Review review) {
        if (review.getId() == null || !reviewRepository.existsById(review.getId())) {
            throw new RuntimeException("Review not found");
        }
        reviewRepository.save(review);
    }

    /**
     * Retrieves reviews for a specific customer.
     *
     * @param customer the {@link Customer} whose reviews are retrieved
     * @return a {@link List} of reviews
     *
     * @see ReviewRepository#findByCustomer(Customer)
     */
    public List<Review> getReviewsByCustomer(Customer customer) {
        return reviewRepository.findByCustomer(customer);
    }

    /**
     * Retrieves reviews for a customer identified by login.
     *
     * @param login the login of the customer
     * @return a {@link List} of reviews
     *
     * @see ReviewRepository#findByCustomerLogin(String)
     */
    public List<Review> getReviewsByCustomerLogin(String login) {
        return reviewRepository.findByCustomerLogin(login);
    }

    /**
     * Retrieves reviews for a specific product.
     *
     * @param product the {@link Product} whose reviews are retrieved
     * @return a {@link List} of reviews
     *
     * @see ReviewRepository#findByProduct(Product)
     */
    public List<Review> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    /**
     * Retrieves reviews for a product by its name.
     *
     * @param name the name of the product
     * @return a {@link List} of reviews
     *
     * @see ReviewRepository#findByProductName(String)
     */
    public List<Review> getReviewsByProductName(String name) {
        return reviewRepository.findByProductName(name);
    }

    /**
     * Removes all reviews associated with a specific product.
     *
     * @param product the {@link Product} whose reviews should be deleted
     *
     * @see ReviewRepository#deleteByProduct(Product)
     */
    public void removebyProduct(Product product) {
        reviewRepository.deleteByProduct(product);
    }

    /**
     * Removes all reviews associated with a product identified by name.
     *
     * @param name the name of the product whose reviews should be deleted
     *
     * @see ReviewRepository#deleteByProductName(String)
     */
    public void removebyProductName(String name) {
        reviewRepository.deleteByProductName(name);
    }
}
