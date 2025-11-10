package com.goldenleaf.shop.dto;

import java.util.List;
import java.util.Set;

public class ProductDTO {

    private Long id;
    private String name;
    private String brand;
    private double price;

    private List<String> imageUrls;

    private Set<Long> categoryIds;

    private List<Long> reviewIds;

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String brand, double price,
                      List<String> imageUrls, Set<Long> categoryIds, List<Long> reviewIds) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageUrls = imageUrls;
        this.categoryIds = categoryIds;
        this.reviewIds = reviewIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Set<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<Long> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<Long> reviewIds) {
        this.reviewIds = reviewIds;
    }
}
