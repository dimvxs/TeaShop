//package com.goldenleaf.shop;
//
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//
//import com.goldenleaf.shop.dto.*;
//import com.goldenleaf.shop.exception.*;
//import com.goldenleaf.shop.model.*;
//
//@Component
//@Primary
//public class AppMapperImpl implements AppMapper {
//
//    // ------------------- Product -------------------
//    @Override
//    public ProductDTO toDTO(Product product) {
//        if (product == null) return null;
//        ProductDTO dto = new ProductDTO();
//        dto.setId(product.getId());
//        dto.setName(product.getName());
//        dto.setBrand(product.getBrand());
//        dto.setPrice(product.getPrice());
//        return dto;
//    }
//
//    @Override
//    public Product toEntity(ProductDTO dto) throws EmptyNameException {
//        if (dto == null) return null;
//        Product product = new Product();
//        product.setId(dto.getId());
//        product.setName(dto.getName());
//        product.setBrand(dto.getBrand());
//        product.setPrice(dto.getPrice());
//        return product;
//    }
//
//    // ------------------- Admin -------------------
//    @Override
//    public AdminDTO toDTO(Admin admin) throws EmptyLoginException {
//        if (admin == null) return null;
//        AdminDTO dto = new AdminDTO();
//        dto.setId(admin.getId());
//        dto.setLogin(admin.getLogin());
//        dto.setLastActivity(admin.getLastActivity());
//        return dto;
//    }
//
//    @Override
//    public Admin toEntity(AdminDTO dto) throws EmptyLoginException, EmptyLastActivityException {
//        if (dto == null) return null;
//        Admin admin = new Admin();
//        admin.setId(dto.getId());
//        admin.setLogin(dto.getLogin());
//        admin.setLastActivity(dto.getLastActivity());
//        return admin;
//    }
//
//    // ------------------- Category -------------------
//    @Override
//    public CategoryDTO toDTO(Category category) {
//        if (category == null) return null;
//        CategoryDTO dto = new CategoryDTO();
//        dto.setId(category.getId());
//        dto.setName(category.getName());
//        return dto;
//    }
//
//    @Override
//    public Category toEntity(CategoryDTO dto) throws EmptyNameException {
//        if (dto == null) return null;
//        Category category = new Category();
//        category.setId(dto.getId());
//        category.setName(dto.getName());
//        return category;
//    }
//
//    // ------------------- CreditCard -------------------
//    @Override
//    public CreditCardDTO toDTO(CreditCard creditCard) {
//        if (creditCard == null) return null;
//        CreditCardDTO dto = new CreditCardDTO();
//        dto.setId(creditCard.getId());
//        dto.setCardNumber(creditCard.getCardNumber());
//        dto.setExpiry(creditCard.getExpiry());
//        return dto;
//    }
//
//    @Override
//    public CreditCard toEntity(CreditCardDTO dto) throws EmptyCardNumberException, EmptyExpiryException {
//        if (dto == null) return null;
//        CreditCard card = new CreditCard();
//        card.setId(dto.getId());
//        card.setCardNumber(dto.getCardNumber());
//        card.setExpiry(dto.getExpiry());
//        return card;
//    }
//
//    // ------------------- Customer -------------------
//    @Override
//    public CustomerDTO toDTO(Customer customer) {
//        if (customer == null) return null;
//        CustomerDTO dto = new CustomerDTO();
//        dto.setId(customer.getId());
//        dto.setName(customer.getName());
//        dto.setEmail(customer.getEmail());
//        return dto;
//    }
//
//    @Override
//    public Customer toEntity(CustomerDTO dto) throws IncorrectEmailException {
//        if (dto == null) return null;
//        Customer customer = new Customer();
//        customer.setId(dto.getId());
//        customer.setName(dto.getName());
//        customer.setEmail(dto.getEmail());
//        return customer;
//    }
//
//    // ------------------- Review -------------------
//    @Override
//    public ReviewDTO toDTO(Review review) {
//        if (review == null) return null;
//        ReviewDTO dto = new ReviewDTO();
//        dto.setId(review.getId());
//        dto.setContent(review.getContent());
//        dto.setRating(review.getRating());
//        return dto;
//    }
//
//    @Override
//    public Review toEntity(ReviewDTO dto) throws IncorrectRatingException, EmptyContentException {
//        if (dto == null) return null;
//        Review review = new Review();
//        review.setId(dto.getId());
//        review.setContent(dto.getContent());
//        review.setRating(dto.getRating());
//        return review;
//    }
//
//    // ------------------- ShoppingCart -------------------
////    @Override
////    public ShoppingCartDTO toDTO(ShoppingCart cart) {
////        if (cart == null) return null;
////        ShoppingCartDTO dto = new ShoppingCartDTO();
////        dto.setId(cart.getId());
////        dto.setCustomerId(cart.getCustomer() != null ? cart.getCustomer().getId() : null);
////        dto.setItems(cart.getItems().stream()
////                .map(this::toDTO)
////                .toList());
////        return dto;
////    }
//    
//    @Override
//    public ShoppingCartDTO toDTO(ShoppingCart cart) {
//        if (cart == null) return null;
//        // Принудительно загружаем items, чтобы Hibernate их подгрузил
//        cart.getItems().size();
//
//        ShoppingCartDTO dto = new ShoppingCartDTO();
//        dto.setId(cart.getId());
//        dto.setCustomerId(cart.getCustomer() != null ? cart.getCustomer().getId() : null);
//        dto.setItems(cart.getItems().stream()
//                .map(this::toDTO)
//                .toList());
//
//        // Можно посчитать totalPrice вручную, если в сущности оно не хранится
//        double totalPrice = cart.getItems().stream()
//                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
//                .sum();
//        dto.setTotalPrice(totalPrice);
//
//        return dto;
//    }
//
//
//
//    @Override
//    public ShoppingCart toEntity(ShoppingCartDTO dto) {
//        if (dto == null) return null;
//        ShoppingCart cart = new ShoppingCart();
//        cart.setId(dto.getId());
//        // customer и items нужно будет заполнить через сервис
//        return cart;
//    }
//
//    // ------------------- ShoppingItem -------------------
////    @Override
////    public ShoppingItemDTO toDTO(ShoppingItem item) {
////        if (item == null) return null;
////
////        ShoppingItemDTO dto = new ShoppingItemDTO();
////        dto.setId(item.getId());
////        dto.setProductId(item.getProduct().getId());
////        dto.setProductName(item.getProduct().getName());
////        dto.setQuantity(item.getQuantity());
////        dto.setPricePerUnit(item.getProduct().getPrice());
////
////        // картинки
////        List<String> images = item.getProduct().getImageUrls();
////        if (images != null && !images.isEmpty()) {
////            dto.setImageUrls(images);
////            dto.setImageUrl(images.get(0)); // главная картинка
////        } else {
////            dto.setImageUrls(List.of("/uploads/default-product.jpg"));
////            dto.setImageUrl("/uploads/default-product.jpg");
////        }
////
////        System.out.println("Images for product " + item.getProduct().getName() + ": " + images);
////
////        return dto;
////    }
//    
////    @Override
////    public ShoppingItemDTO toDTO(ShoppingItem item) throws EmptyProductException, IncorrectQuantityException {
////        if (item == null) return null;
////
////        Product p = item.getProduct();
////        ProductDTO productDTO = new ProductDTO(
////            p.getId(),
////            p.getName(),
////            p.getBrand(),
////            p.getPrice(),
////            p.getImageUrls(), // здесь ВСЕ картинки из БД
////            Set.of(), // категории, если есть
////            List.of() // reviewIds
////        );
////
////        return new ShoppingItemDTO(
////            item.getId(),
////            item.getQuantity(),
////            productDTO
////        );
////        
////    }
////
////
////
//    
//    @Override
//    public ShoppingItemDTO toDTO(ShoppingItem item) throws EmptyProductException, IncorrectQuantityException {
//        if (item == null) return null;
//
//        Product p = item.getProduct();
//
//        if (p == null) {
//            ProductDTO defaultProduct = new ProductDTO(
//                0L,
//                "Продукт не найден",
//                "",
//                0.0,
//                List.of("/uploads/default-product.jpg"),
//                Set.of(),
//                List.of()
//            );
//            return new ShoppingItemDTO(
//                item.getId() != null ? item.getId() : 0L,
//                item.getQuantity(),
//                defaultProduct
//            );
//        }
//
//        List<String> images = p.getImageUrls() != null && !p.getImageUrls().isEmpty()
//                ? p.getImageUrls()
//                : List.of("/uploads/default-product.jpg");
//
//        ProductDTO productDTO = new ProductDTO(
//            p.getId(),
//            p.getName(),
//            p.getBrand(),
//            p.getPrice(),
//            images,
//            Set.of(),
//            List.of()
//        );
//
//        return new ShoppingItemDTO(
//            item.getId() != null ? item.getId() : 0L,
//            item.getQuantity(),
//            productDTO
//        );
//    }
//
//    @Override
//    public ShoppingItem toEntity(ShoppingItemDTO dto) throws IncorrectQuantityException {
//        if (dto == null) return null;
//        ShoppingItem item = new ShoppingItem();
//        item.setId(dto.getId());
//        item.setQuantity(dto.getQuantity());
//        // product нужно будет подставить через сервис
//        return item;
//    }
//}


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

        // Без ссылки на ProductDTO, берем только id, имя и цену через item.getProduct()
        Long productId = item.getProduct() != null ? item.getProduct().getId() : null;
        String productName = item.getProduct() != null ? item.getProduct().getName() : null;
        double pricePerUnit = item.getProduct() != null ? item.getProduct().getPrice() : 0;
        System.out.println("CartItem mapping debug:");
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Price per unit: " + pricePerUnit);

        return new ShoppingItemDTO(
            item.getId(),
            productId,
            productName,
            item.getQuantity(),
            pricePerUnit,
            item.getProduct() != null && !item.getProduct().getImageUrls().isEmpty() 
            ? item.getProduct().getImageUrls().get(0) 
            : "/images/default-product.jpg"
            
        );
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
