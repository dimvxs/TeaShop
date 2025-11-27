package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object representing a single item in the shopping cart.
 * <p>
 * Used exclusively in {@link ShoppingCartDTO} responses.
 * Contains read-only data about the product and its current state in the cart.
 * </p>
 * <p>
 * <strong>Important security rules:</strong>
 * <ul>
 *   <li>All price-related fields are calculated server-side — never trust client input</li>
 *   <li>Quantity is validated against stock during add/update operations</li>
 *   <li>This DTO is output-only — never used for input from client</li>
 * </ul>
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
public class ShoppingItemDTO {

    @Positive(message = "Item ID must be positive")
    private Long id;

    @Positive(message = "Product ID must be positive")
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Product name is required")
    @Size(max = 200, message = "Product name too long")
    private String productName;

    @Positive(message = "Quantity must be at least 1")
    @Max(value = 999, message = "Maximum 999 units per item allowed")
    private int quantity;

    @PositiveOrZero(message = "Price per unit cannot be negative")
    @DecimalMax(value = "999999.99", message = "Price per unit too high")
    @Digits(integer = 6, fraction = 2, message = "Price must have max 2 decimal places")
    private double pricePerUnit;

    /** Required for JSON deserialization */
    public ShoppingItemDTO() {}

    public ShoppingItemDTO(Long id, Long productId, String productName,
                           int quantity, double pricePerUnit) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    /**
     * Calculates subtotal for this item.
     * Convenience method for frontend or logging.
     *
     * @return quantity × pricePerUnit, rounded to 2 decimal places
     */
    public double getSubtotal() {
        return Math.round(quantity * pricePerUnit * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "ShoppingItemDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", pricePerUnit=" + String.format("%.2f", pricePerUnit) +
                ", subtotal=" + String.format("%.2f", getSubtotal()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingItemDTO that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}