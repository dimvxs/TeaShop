package com.goldenleaf.shop;
import com.goldenleaf.shop.model.*;
import com.goldenleaf.shop.dto.*;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppMapper {

    // User
    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);

    // Product
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO dto);
    
    
    AdminDTO toDTO(Admin admin);
    Admin toEntity(AdminDTO dto);
    
    
    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO dto);
    
    
    CreditCardDTO toDTO(CreditCard creditCard);
    CreditCard toEntity(CreditCardDTO dto);
    
    
    CustomerDTO toDTO(Customer customer);
    Customer toEntity(CustomerDTO dto);
    
    
    
    ReviewDTO toDTO(Review review);
    Review toEntity(ReviewDTO dto);
    
    
    
    ShoppingCartDTO toDTO(ShoppingCart shoppingCart);
    ShoppingCart toEntity(ShoppingCartDTO dto);
    
    
    
    ShoppingItemDTO toDTO(ShoppingItem shoppingItem);
    ShoppingItem toEntity(ShoppingItemDTO dto);
    
    
    
    
    
    

    
    
}
