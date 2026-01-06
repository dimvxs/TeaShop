package com.goldenleaf.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goldenleaf.shop.exception.EmptyAuthorException;
import com.goldenleaf.shop.exception.EmptyContentException;
import com.goldenleaf.shop.exception.EmptyProductException;
import com.goldenleaf.shop.exception.IncorrectRatingException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

/**
 * <p>
 * Represents a review left by a {@link Customer} for a {@link Product}.
 * Each review contains an author, text content, a rating from 1 to 5,
 * and the product it refers to.
 * </p>
 *
 * <p>
 * The class validates all fields both in the constructor and setters to ensure
 * that no review can be created with invalid or missing data.
 * </p>
 *
 * @see Customer
 * @see Product
 */
@Entity
public class Review {

    /**
     * <p>
     * Unique identifier of the review. Automatically generated.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * <p>
     * The customer who authored the review.
     * Cannot be {@code null}.
     * </p>
     *
     * @see Customer
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer author;

    /**
     * <p>
     * The textual content of the review.
     * Limited to 1000 characters.
     * Cannot be null or blank.
     * </p>
     */
    @Column(length = 1000)
    private String content;

    /**
     * <p>
     * Rating given by the customer, from 1 to 5.
     * The rating is validated using both field-level annotations and logic in
     * the constructor and setter.
     * </p>
     */
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private int rating;

    /**
     * <p>
     * The product that the review refers to.
     * Cannot be {@code null}.
     * </p>
     *
     * @see Product
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore  // ← ЭТО КЛЮЧЕВОЕ!
    private Product product;

    /**
     * <p>
     * Default constructor required by JPA.
     * </p>
     */
    public Review() {}

    /**
     * <p>
     * Constructs a new review with the given parameters.
     * Validates all fields to ensure correctness.
     * </p>
     *
     * @param author the customer who wrote the review; must not be null
     * @param content the text content of the review; must not be null or blank
     * @param rating the rating from 1 to 5
     * @param product the product being reviewed; must not be null
     *
     * @throws EmptyAuthorException if {@code author} is null
     * @throws EmptyContentException if {@code content} is null or blank
     * @throws IncorrectRatingException if {@code rating} is outside the range 1–5
     * @throws EmptyProductException if {@code product} is null
     *
     * @see Customer
     * @see Product
     */
    public Review(Customer author, String content, int rating, Product product)
            throws IncorrectRatingException, EmptyAuthorException, EmptyContentException, EmptyProductException {

        if (author == null) {
            throw new EmptyAuthorException("Author should be specified");
        }

        if (content == null || content.isBlank()) {
            throw new EmptyContentException("Content must be set");
        }

        if (rating < 1 || rating > 5) {
            throw new IncorrectRatingException("Rating should be between 1 and 5");
        }

        if (product == null) {
            throw new EmptyProductException("Product must be set");
        }

        this.author = author;
        this.content = content;
        this.rating = rating;
        this.product = product;
    }

    /**
     * @return the unique identifier of the review
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the customer who authored the review
     *
     * @see Customer
     */
    public Customer getAuthor() {
        return author;
    }

    /**
     * @return the review content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the numeric rating from 1 to 5
     */
    public int getRating() {
        return rating;
    }

    /**
     * <p>
     * Sets a new author for the review.
     * </p>
     *
     * @param author must not be null
     *
     * @throws EmptyAuthorException if {@code author} is null
     *
     * @see Customer
     */
    public void setAuthor(Customer author) throws EmptyAuthorException {
        if (author == null) {
            throw new EmptyAuthorException("Author should be specified");
        }
        this.author = author;
    }

    /**
     * <p>
     * Sets the content of the review.
     * </p>
     *
     * @param content must not be null or blank
     *
     * @throws EmptyContentException if {@code content} is null or blank
     */
    public void setContent(String content) throws EmptyContentException {
        if (content == null || content.isBlank()) {
            throw new EmptyContentException("Content must be set");
        }
        this.content = content;
    }

    /**
     * <p>
     * Sets the numeric rating for the review.
     * </p>
     *
     * @param rating must be between 1 and 5
     *
     * @throws IncorrectRatingException if {@code rating} is outside valid range
     */
    public void setRating(int rating) throws IncorrectRatingException {
        if (rating < 1 || rating > 5) {
            throw new IncorrectRatingException("Rating should be between 1 and 5");
        }
        this.rating = rating;
    }

	public Product getProduct() {
		return product;
	}
}
