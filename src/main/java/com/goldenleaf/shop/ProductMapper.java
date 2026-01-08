package com.goldenleaf.shop;

import java.awt.Image;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.goldenleaf.shop.dto.ProductDTO;
import com.goldenleaf.shop.model.Category;
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.model.Review;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product product) {
        if (product == null) return null;

        return new ProductDTO(
        	     product.getId(),
                 product.getName(),
                 product.getBrand(),
                 product.getPrice(),
                 product.getImageUrls(),
                 product.getCategories()
                 .stream()
                 .map(Category::getId)
                 .collect(Collectors.toSet()),
          product.getReviews()
                 .stream()
                 .map(Review::getId)
                 .toList()
        
        );
    }

    public List<ProductDTO> toDtoList(List<Product> products) {
        return products.stream()
                       .map(this::toDto)
                       .toList();
    }
}
