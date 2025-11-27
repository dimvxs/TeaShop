package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for creating or displaying product reviews.
 * <p>
 * Used in:
 * <ul>
 *   <li>Customer submitting a new review</li>
 *   <li>Displaying reviews on product detail page</li>
 *   <li>Admin moderation panel</li>
 *   <li>API responses (GET /products/{id}/reviews)</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Security & UX note:</strong>
 * <ul>
 *   <li>{@code authorId} and {@code productId} are required on input</li>
 *   <li>{@code authorName} is usually filled from JWT/auth context — not trusted from client</li>
 *   <li>Rating must be strictly between 1 and 5 (inclusive</li>
 * </ul>
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
public class ReviewDTO {

    private Long id;

    @NotNull(message = "Author ID is required")
    @Positive(message = "Author ID must be positive")
    private Long authorId;

    // Usually filled server-side from authenticated user — do not trust client input!
    @Size(max = 100, message = "Author name too long")
    private String authorName;

    @NotBlank(message = "Review content cannot be empty")
    @Size(min = 10, max = 2000, message = "Review must be between 10 and 2000 characters")
    private String content;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private int rating;

    @NotNull(message = "Product ID is required")
    @Positive(message = "Product ID must be positive")
    private Long productId;

    /** Required for JSON deserialization (Jackson/Gson) */
    public ReviewDTO() {}

    public ReviewDTO(Long id, Long authorId, String authorName,
                     String content, int rating, Long productId) {
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.rating = rating;
        this.productId = productId;
    }

    // === Геттеры и сеттеры ===

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content != null ? content.trim() : null; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", rating=" + rating + "/5" +
                ", productId=" + productId +
                ", contentLength=" + (content != null ? content.length() : 0) +
                '}';
    }
}