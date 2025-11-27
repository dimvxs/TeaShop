package com.goldenleaf.shop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.Collections;
import java.util.List;

/**
 * Data Transfer Object representing a customer's shopping cart.
 * <p>
 * Used in:
 * <ul>
 *   <li>GET /api/cart — returning current cart state</li>
 *   <li>Mini-cart widget in header</li>
 *   <li>Checkout page — final review before order</li>
 *   <li>Service layer → controller communication</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Important:</strong>
 * <ul>
 *   <li>{@code totalPrice} is calculated server-side — never trust client input!</li>
 *   <li>{@code customerId} is derived from authentication context — not accepted from client</li>
 *   <li>List of items is returned as unmodifiable to prevent external modification</li>
 * </ul>
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
public class ShoppingCartDTO {

    @Positive(message = "Cart ID must be positive")
    private Long id;

    @Positive(message = "Customer ID must be positive")
    private Long customerId;

    @NotNull(message = "Cart items cannot be null")
    @Size(max = 100, message = "Maximum 100 items allowed in cart")
    private List<@Valid ShoppingItemDTO> items = Collections.emptyList();

    @PositiveOrZero(message = "Total price cannot be negative")
    @DecimalMax(value = "999999.99", message = "Total price exceeds limit")
    private double totalPrice;

    /** Required for JSON deserialization */
    public ShoppingCartDTO() {}

    public ShoppingCartDTO(Long id, Long customerId, List<ShoppingItemDTO> items, double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.items = items != null ? List.copyOf(items) : Collections.emptyList();
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns an unmodifiable view of cart items.
     * Ensures external code cannot modify the list after DTO creation.
     */
    public List<ShoppingItemDTO> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<ShoppingItemDTO> items) {
        this.items = items != null ? List.copyOf(items) : Collections.emptyList();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Total price is calculated on the server.
     * This setter should only be used internally or in mapping.
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", itemCount=" + items.size() +
                ", totalPrice=" + String.format("%.2f", totalPrice) +
                '}';
    }
}