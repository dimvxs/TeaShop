package com.goldenleaf.shop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.goldenleaf.shop.exception.EmptyBrandException;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.exception.IncorrectPriceException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

/**
 * <p>
 * Represents a product available for purchase in the system.
 * A product contains core details such as name, brand, price, categories,
 * associated image URLs, and user reviews.
 * </p>
 *
 * <p>
 * The class ensures validation of required fields such as product name,
 * brand, and price, preventing creation of invalid product objects.
 * </p>
 *
 * @see Category
 * @see Review
 */
@Entity
public class Product {

    /**
     * <p>
     * Unique identifier of the product, generated automatically.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * <p>
     * Name of the product. Cannot be null or blank.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Brand of the product. Cannot be null or blank.
     * </p>
     */
    private String brand;

    /**
     * <p>
     * Price of the product. Must be non-negative.
     * </p>
     */
    private double price;

    /**
     * <p>
     * List of image URLs associated with this product.
     * Stored as a simple element collection of strings.
     * </p>
     */
    @ElementCollection
    private List<String> imageUrls = new ArrayList<>();

    /**
     * <p>
     * Categories to which this product belongs.
     * Stored through a many-to-many relationship.
     * </p>
     *
     * @see Category
     */
    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    /**
     * <p>
     * Customer reviews associated with this product.
     * This is a one-to-many relationship mapped by the product field in Review.
     * </p>
     *
     * @see Review
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    /**
     * <p>
     * Default constructor required by JPA.
     * </p>
     */
    public Product() {}

    /**
     * <p>
     * Constructs a new product with the given parameters.
     * Validates name, brand, and price before initialization.
     * </p>
     *
     * @param name the name of the product; must not be null or blank
     * @param brand the brand of the product; must not be null or blank
     * @param price the price of the product; must not be negative
     * @param categories categories assigned to this product
     * @param imageUrls associated image URLs
     * @param reviews initial list of reviews
     *
     * @throws EmptyNameException if {@code name} is null or blank
     * @throws EmptyBrandException if {@code brand} is null or blank
     * @throws IncorrectPriceException if {@code price} is negative
     *
     * @see Category
     * @see Review
     */
    public Product(
            String name,
            String brand,
            double price,
            Set<Category> categories,
            List<String> imageUrls,
            List<Review> reviews
    ) throws EmptyBrandException, EmptyNameException, IncorrectPriceException {

        if (name == null || name.isBlank()) {
            throw new EmptyNameException("Name cannot be null or empty");
        }

        if (brand == null || brand.isBlank()) {
            throw new EmptyBrandException("Brand cannot be null or empty");
        }

        if (price < 0) {
            throw new IncorrectPriceException("Price cannot be negative");
        }

        this.name = name;
        this.brand = brand;
        this.price = price;
        this.categories = categories;
        this.imageUrls = imageUrls;
        this.reviews = reviews;
    }

    /**
     * @return the unique product identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets a new name for the product.
     * </p>
     *
     * @param name must not be null or blank
     *
     * @throws EmptyNameException if {@code name} is null or blank
     */
    public void setName(String name) throws EmptyNameException {
        if (name == null || name.isBlank()) {
            throw new EmptyNameException("Name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * @return the brand name
     */
    public String getBrand() {
        return brand;
    }

    /**
     * <p>
     * Sets a new product brand.
     * </p>
     *
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * <p>
     * Sets the price of the product.
     * </p>
     *
     * @param price must not be negative
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return categories assigned to this product
     *
     * @see Category
     */
    public Set<Category> getCategories() {
        return categories;
    }

    /**
     * <p>
     * Sets the categories associated with this product.
     * </p>
     *
     * @param categories the categories to assign
     */
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    /**
     * @return list of image URLs
     */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    /**
     * <p>
     * Sets the image URLs associated with this product.
     * </p>
     *
     * @param imageUrls a list of image URL strings
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    /**
     * @return list of reviews for this product
     *
     * @see Review
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * <p>
     * Sets the list of reviews for this product.
     * </p>
     *
     * @param reviews the new list of reviews
     *
     * @see Review
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * <p>
     * Adds a new review for this product.
     * </p>
     *
     * @param review the review to add
     *
     * @see Review
     */
    public void addReview(Review review) {
        reviews.add(review);
    }
}
