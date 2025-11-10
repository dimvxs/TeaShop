package com.goldenleaf.shop.dto;

import java.util.List;

public class ShoppingCartDTO {

    private Long id;
    private Long customerId;
    private List<ShoppingItemDTO> items;
    private double totalPrice;

    public ShoppingCartDTO() {}

    public ShoppingCartDTO(Long id, Long customerId, List<ShoppingItemDTO> items, double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
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

    public List<ShoppingItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ShoppingItemDTO> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
