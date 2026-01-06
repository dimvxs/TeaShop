package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Set;

public class ProductCreateDTO {

    @NotBlank(message = "Название обязательно")
    private String name;

    @NotBlank(message = "Бренд обязателен")
    private String brand;

    @PositiveOrZero(message = "Цена не может быть отрицательной")
    private double price;
    
    private String description;

    @NotEmpty(message = "Укажите хотя бы одну категорию")
    private Set<Long> categoryIds;

    // === Геттеры (ОБЯЗАТЕЛЬНО!) ===
    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }
    
    public String getDescription() {
		return description;
	}

    // === Сеттеры (если нужно для десериализации из JSON) ===
    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public void setDescription(String description) {
    			this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

	
}