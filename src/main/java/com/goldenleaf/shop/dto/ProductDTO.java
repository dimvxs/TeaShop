package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Data Transfer Object for creating, updating or returning product information.
 * <p>
 * Used in:
 * <ul>
 *   <li>Product catalog API (list, details)</li>
 *   <li>Admin panel – product management</li>
 *   <li>Search and filtering operations</li>
 *   <li>Cart and order processing</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Note:</strong> For performance and security, consider using separate DTOs:
 * <ul>
 *   <li>{@code ProductListDTO} – minimal data for catalog listings</li>
 *   <li>{@code ProductDetailsDTO} – full data including reviews and images</li>
 * </ul>
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String name;

    @NotBlank(message = "Brand is required")
    @Size(max = 100, message = "Brand name too long")
    private String brand;

    @PositiveOrZero(message = "Price cannot be negative")
    @DecimalMax(value = "999999.99", message = "Price too high")
    @Digits(integer = 6, fraction = 2, message = "Price must have max 2 decimal places")
    private double price;

    @Size(max = 10, message = "Maximum 10 images allowed")
    private List<@Pattern(regexp = "^(https?://).+", message = "Image URL must be valid") String> imageUrls;

    @NotEmpty(message = "Product must belong to at least one category")
    @Size(max = 5, message = "Maximum 5 categories allowed")
    private Set<@Positive Long> categoryIds;

    // Review IDs — only for response, never accepted on input
    private List<Long> reviewIds = Collections.emptyList();

    /** Required for JSON deserialization */
    public ProductDTO() {}

    public ProductDTO(Long id, String name, String brand, double price,
                      List<String> imageUrls, Set<Long> categoryIds, List<Long> reviewIds) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageUrls = imageUrls != null ? List.copyOf(imageUrls) : Collections.emptyList();
        this.categoryIds = categoryIds != null ? Set.copyOf(categoryIds) : Collections.emptySet();
        this.reviewIds = reviewIds != null ? List.copyOf(reviewIds) : Collections.emptyList();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public List<String> getImageUrls() {
        return imageUrls != null ? Collections.unmodifiableList(imageUrls) : Collections.emptyList();
    }
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls != null ? List.copyOf(imageUrls) : Collections.emptyList();
    }

    public Set<Long> getCategoryIds() {
        return categoryIds != null ? Collections.unmodifiableSet(categoryIds) : Collections.emptySet();
    }
    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds != null ? Set.copyOf(categoryIds) : Collections.emptySet();
    }

    public List<Long> getReviewIds() {
        return Collections.unmodifiableList(reviewIds);
    }
    public void setReviewIds(List<Long> reviewIds) {
        this.reviewIds = reviewIds != null ? List.copyOf(reviewIds) : Collections.emptyList();
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", imageCount=" + (imageUrls != null ? imageUrls.size() : 0) +
                ", categoryCount=" + (categoryIds != null ? categoryIds.size() : 0) +
                ", reviewCount=" + reviewIds.size() +
                '}';
    }
}