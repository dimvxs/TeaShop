package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateCartItemRequest {

    @NotNull(message = "Item ID не может быть null")
    @Positive(message = "Item ID должен быть положительным")
    private Long itemId;

    @Positive(message = "Количество должно быть положительным числом")
    private int quantity;

    // Конструктор по умолчанию
    public UpdateCartItemRequest() {}

    // Конструктор с параметрами
    public UpdateCartItemRequest(Long itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    // Геттеры и сеттеры
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "UpdateCartItemRequest{" +
                "itemId=" + itemId +
                ", quantity=" + quantity +
                '}';
    }
}
