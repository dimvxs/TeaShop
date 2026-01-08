package com.goldenleaf.shop;

import com.goldenleaf.shop.model.*;
import com.goldenleaf.shop.dto.*;
import com.goldenleaf.shop.exception.EmptyAuthorException;
import com.goldenleaf.shop.exception.EmptyBrandException;
import com.goldenleaf.shop.exception.EmptyCardNumberException;
import com.goldenleaf.shop.exception.EmptyContentException;
import com.goldenleaf.shop.exception.EmptyCvvException;
import com.goldenleaf.shop.exception.EmptyExpiryException;
import com.goldenleaf.shop.exception.EmptyLastActivityException;
import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.exception.EmptyProductException;
import com.goldenleaf.shop.exception.EmptyShoppingCartException;
import com.goldenleaf.shop.exception.IncorrectBonusPointsValue;
import com.goldenleaf.shop.exception.IncorrectEmailException;
import com.goldenleaf.shop.exception.IncorrectMobileException;
import com.goldenleaf.shop.exception.IncorrectPriceException;
import com.goldenleaf.shop.exception.IncorrectQuantityException;
import com.goldenleaf.shop.exception.IncorrectRatingException;
import com.goldenleaf.shop.exception.NullCustomerException;
import com.goldenleaf.shop.exception.NullPaymentException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

/**
 * Mapper interface for converting between entity classes and their corresponding DTOs.
 * <p>
 * Uses <a href="https://mapstruct.org/">MapStruct</a> for automatic mapping.
 * Each entity has a pair of methods: one to convert to a DTO and one to convert back to an entity.
 * This interface is configured as a Spring component with {@code componentModel = "spring"}.
 * </p>
 *
 * @see User
 * @see UserDTO
 * @see Product
 * @see ProductDTO
 * @see Admin
 * @see AdminDTO
 * @see Category
 * @see CategoryDTO
 * @see CreditCard
 * @see CreditCardDTO
 * @see Customer
 * @see CustomerDTO
 * @see Review
 * @see ReviewDTO
 * @see ShoppingCart
 * @see ShoppingCartDTO
 * @see ShoppingItem
 * @see ShoppingItemDTO
 */
@Mapper(componentModel = "spring")
public interface AppMapper {

    // ------------------- User -------------------
//    UserDTO toDTO(User user);
//    User toEntity(UserDTO dto);

    // ------------------- Product -------------------
    @Mapping(target = "categoryIds", ignore = true)
    @Mapping(target = "reviewIds", ignore = true)
    ProductDTO toDTO(Product product) throws EmptyNameException, EmptyBrandException, IncorrectPriceException;

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Product toEntity(ProductDTO dto) throws EmptyNameException, EmptyBrandException, IncorrectPriceException;

    // ------------------- Admin -------------------


    AdminDTO toDTO(Admin admin) throws EmptyLastActivityException, EmptyLoginException;
    Admin toEntity(AdminDTO dto)throws EmptyLastActivityException, EmptyLoginException;

    // ------------------- Category -------------------
    CategoryDTO toDTO(Category category) throws EmptyNameException;
    Category toEntity(CategoryDTO dto) throws EmptyNameException;

    // ------------------- CreditCard -------------------
    @Mapping(target = "customerId", ignore = true)
    CreditCardDTO toDTO(CreditCard creditCard) throws EmptyNameException, NullCustomerException, EmptyCardNumberException, EmptyExpiryException, EmptyCvvException;

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "cvv", ignore = true)
    CreditCard toEntity(CreditCardDTO dto) throws EmptyNameException, NullCustomerException, EmptyCardNumberException, EmptyExpiryException, EmptyCvvException;

    // ------------------- Customer -------------------
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "shoppingCartId", ignore = true)
    @Mapping(target = "paymentIds", ignore = true)
    CustomerDTO toDTO(Customer customer) throws IncorrectMobileException, IncorrectEmailException, IncorrectBonusPointsValue, EmptyShoppingCartException, NullPaymentException, EmptyLastActivityException, EmptyLoginException;

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    @Mapping(target = "payments", ignore = true)
    Customer toEntity(CustomerDTO dto) throws IncorrectMobileException, IncorrectEmailException, IncorrectBonusPointsValue, EmptyShoppingCartException, NullPaymentException, EmptyLastActivityException, EmptyLoginException;

    // ------------------- Review -------------------
    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "authorName", ignore = true)
    @Mapping(target = "productId", ignore = true)
    ReviewDTO toDTO(Review review)   throws IncorrectRatingException, EmptyAuthorException, EmptyContentException, EmptyProductException;

    @Mapping(target = "author", ignore = true)
    Review toEntity(ReviewDTO dto)   throws IncorrectRatingException, EmptyAuthorException, EmptyContentException, EmptyProductException;

    // ------------------- ShoppingCart -------------------
    @Mapping(target = "customerId", ignore = true)
    ShoppingCartDTO toDTO(ShoppingCart shoppingCart) 
        throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException;


    @Mapping(target = "customer", ignore = true)
    ShoppingCart toEntity(ShoppingCartDTO dto) throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException;;

    // ------------------- ShoppingItem -------------------
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "pricePerUnit", ignore = true)
    ShoppingItemDTO toDTO(ShoppingItem shoppingItem) throws EmptyProductException, IncorrectQuantityException;

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cart", ignore = true)
    ShoppingItem toEntity(ShoppingItemDTO dto) throws EmptyProductException, IncorrectQuantityException;

}
