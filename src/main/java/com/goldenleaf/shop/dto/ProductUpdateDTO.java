package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

public class ProductUpdateDTO {

    @NotBlank(message = "Название обязательно")
    private String name;

    private String brand;

    private Double price;
    
    private String description; 

    private Set<Long> categoryIds;

    private List<String> existingImageUrls;

    // === Геттеры (ОБЯЗАТЕЛЬНО!) ===
    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public Double getPrice() {
        return price;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    public List<String> getExistingImageUrls() {
        return existingImageUrls;
    }
    
    public String getDescription() {
		return description;
	}

    // === Сеттеры (нужны для Jackson при десериализации multipart) ===
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
    			this.description = description;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public void setExistingImageUrls(List<String> existingImageUrls) {
        this.existingImageUrls = existingImageUrls;
    }

	
}