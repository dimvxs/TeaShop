package com.goldenleaf.shop;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.goldenleaf.shop.dto.*;
import com.goldenleaf.shop.exception.*;
import com.goldenleaf.shop.model.*;

@Component
@Primary
public class AppMapperImpl implements AppMapper {

    // ------------------- Product -------------------
    @Override
    public ProductDTO toDTO(Product product) {
        if (product == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        return dto;
    }

    @Override
    public Product toEntity(ProductDTO dto) throws EmptyNameException {
        if (dto == null) return null;
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        return product;
    }

    // ------------------- Admin -------------------
    @Override
    public AdminDTO toDTO(Admin admin) throws EmptyLoginException {
        if (admin == null) return null;
        AdminDTO dto = new AdminDTO();
        dto.setId(admin.getId());
        dto.setLogin(admin.getLogin());
        dto.setLastActivity(admin.getLastActivity());
        return dto;
    }

    @Override
    public Admin toEntity(AdminDTO dto) throws EmptyLoginException, EmptyLastActivityException {
        if (dto == null) return null;
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setLogin(dto.getLogin());
        admin.setLastActivity(dto.getLastActivity());
        return admin;
    }

    // ------------------- Category -------------------
    @Override
    public CategoryDTO toDTO(Category category) {
        if (category == null) return null;
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    @Override
    public Category toEntity(CategoryDTO dto) throws EmptyNameException {
        if (dto == null) return null;
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }

    // ------------------- CreditCard -------------------
    @Override
    public CreditCardDTO toDTO(CreditCard creditCard) {
        if (creditCard == null) return null;
        CreditCardDTO dto = new CreditCardDTO();
        dto.setId(creditCard.getId());
        dto.setCardNumber(creditCard.getCardNumber());
        dto.setExpiry(creditCard.getExpiry());
        return dto;
    }

    @Override
    public CreditCard toEntity(CreditCardDTO dto) throws EmptyCardNumberException, EmptyExpiryException {
        if (dto == null) return null;
        CreditCard card = new CreditCard();
        card.setId(dto.getId());
        card.setCardNumber(dto.getCardNumber());
        card.setExpiry(dto.getExpiry());
        return card;
    }

    // ------------------- Customer -------------------
    @Override
    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        return dto;
    }

    @Override
    public Customer toEntity(CustomerDTO dto) throws IncorrectEmailException {
        if (dto == null) return null;
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        return customer;
    }

    // ------------------- Review -------------------
    @Override
    public ReviewDTO toDTO(Review review) {
        if (review == null) return null;
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setRating(review.getRating());
        return dto;
    }

    @Override
    public Review toEntity(ReviewDTO dto) throws IncorrectRatingException, EmptyContentException {
        if (dto == null) return null;
        Review review = new Review();
        review.setId(dto.getId());
        review.setContent(dto.getContent());
        review.setRating(dto.getRating());
        return review;
    }

    // ------------------- ShoppingCart -------------------
    @Override
    public ShoppingCartDTO toDTO(ShoppingCart cart) {
        if (cart == null) return null;
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setId(cart.getId());
        dto.setCustomerId(cart.getCustomer() != null ? cart.getCustomer().getId() : null);
        dto.setItems(cart.getItems().stream()
                .map(this::toDTO)
                .toList());
        return dto;
    }

    @Override
    public ShoppingCart toEntity(ShoppingCartDTO dto) {
        if (dto == null) return null;
        ShoppingCart cart = new ShoppingCart();
        cart.setId(dto.getId());
        // customer и items нужно будет заполнить через сервис
        return cart;
    }

    // ------------------- ShoppingItem -------------------
    @Override
    public ShoppingItemDTO toDTO(ShoppingItem item) {
        if (item == null) return null;
        ShoppingItemDTO dto = new ShoppingItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct() != null ? item.getProduct().getId() : null);
        dto.setProductName(item.getProduct() != null ? item.getProduct().getName() : null);
        dto.setQuantity(item.getQuantity());
        dto.setPricePerUnit(item.getProduct() != null ? item.getProduct().getPrice() : 0);
        return dto;
    }

    @Override
    public ShoppingItem toEntity(ShoppingItemDTO dto) throws IncorrectQuantityException {
        if (dto == null) return null;
        ShoppingItem item = new ShoppingItem();
        item.setId(dto.getId());
        item.setQuantity(dto.getQuantity());
        // product нужно будет подставить через сервис
        return item;
    }
}
