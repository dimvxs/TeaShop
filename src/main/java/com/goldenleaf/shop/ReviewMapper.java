package com.goldenleaf.shop;

import com.goldenleaf.shop.dto.ReviewDTO;
import com.goldenleaf.shop.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "product.id", target = "productId")
    ReviewDTO toDto(Review review);
}
