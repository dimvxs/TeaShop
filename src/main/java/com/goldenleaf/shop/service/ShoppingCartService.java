package com.goldenleaf.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.goldenleaf.shop.AppMapper;
import com.goldenleaf.shop.dto.ShoppingCartDTO;
import com.goldenleaf.shop.dto.ShoppingItemDTO;
import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.exception.EmptyProductException;
import com.goldenleaf.shop.exception.IncorrectPriceException;
import com.goldenleaf.shop.exception.IncorrectQuantityException;
import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.model.ShoppingCart;
import com.goldenleaf.shop.model.ShoppingItem;
import com.goldenleaf.shop.model.User;
import com.goldenleaf.shop.repository.CustomerRepository;
import com.goldenleaf.shop.repository.ProductRepository;
import com.goldenleaf.shop.repository.ShoppingCartRepository;
import com.goldenleaf.shop.repository.ShoppingItemRepository;
import com.goldenleaf.shop.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Service class for managing {@link ShoppingCart} entities.
 * <p>
 * Provides business logic for retrieving, adding, updating, and deleting shopping carts.
 * Supports operations by {@link Customer} and cart ID.
 * Acts as an intermediary between controllers and the {@link ShoppingCartRepository}.
 * </p>
 *
 * @see ShoppingCart
 * @see ShoppingCartRepository
 * @see Customer
 */
@Service
public class ShoppingCartService {

    /**
     * Repository used for performing CRUD operations on {@link ShoppingCart} entities.
     */
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final AppMapper appMapper;


    /**
     * Constructs a new {@code ShoppingCartService} with the provided repository.
     *
     * @param repo the repository used to perform operations on shopping carts
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see ShoppingCartRepository
     */
    public ShoppingCartService(ShoppingCartRepository repo, ShoppingItemRepository itemRepository, ProductRepository productRepository, AppMapper appMapper) {
        if (repo == null) {
            throw new IllegalArgumentException("ShoppingCartRepository cannot be null");
        }
        this.shoppingCartRepository = repo;
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
        this.appMapper = appMapper;
  
    }

    /**
     * Retrieves all shopping carts from the database.
     *
     * @return a {@link List} of all {@link ShoppingCart} entities
     *
     * @see ShoppingCartRepository#findAll()
     */
    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    /**
     * Retrieves a {@link ShoppingCart} by its unique ID.
     *
     * @param id the ID of the shopping cart
     * @return the {@link ShoppingCart} with the specified ID
     * @throws RuntimeException if no shopping cart with the given ID exists
     *
     * @see ShoppingCartRepository#findById(Object)
     */
    public ShoppingCart getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found with id: " + id));
    }

    /**
     * Retrieves a {@link ShoppingCart} for a specific {@link Customer}.
     *
     * @param customer the customer whose shopping cart is retrieved
     * @return the {@link ShoppingCart} for the given customer
     * @throws RuntimeException if no shopping cart exists for the given customer
     *
     * @see ShoppingCartRepository#findByCustomer(Customer)
     */
    public ShoppingCart getShoppingCartByCustomer(Customer customer) {
        return shoppingCartRepository.findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found by customer: " + customer));
    }
    
 // Получить корзину текущего пользователя
    public ShoppingCart getCurrentUserCart() {
        Customer customer = getCurrentCustomer();
        return customer.getShoppingCart();
    }
    
    public ShoppingCartDTO getCurrentUserCartDTO() 
            throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {
        ShoppingCart cart = getCurrentUserCart(); 
        return appMapper.toDTO(cart); // используем MapStruct
    }

    public ShoppingCartDTO getShoppingCartByIdDTO(Long id) 
            throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {
        ShoppingCart cart = shoppingCartRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        return appMapper.toDTO(cart);
    }

    
//    @Transactional
//    public ShoppingCart updateCartItem(Long itemId, int quantity) throws IncorrectQuantityException {
//        ShoppingItem item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("Элемент корзины не найден"));
//
//        if (quantity <= 0) {
//            itemRepository.delete(item);
//        } else {
//            item.setQuantity(quantity);
//            itemRepository.save(item);
//        }
//
//        // Возвращаем актуальное состояние корзины пользователя
//        return shoppingCartRepository.findById(item.getCart().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Корзина не найдена"));
//    }
//    
    public ShoppingCartDTO updateCartItem(Long itemId, int quantity) 
            throws IncorrectQuantityException, EmptyProductException, IncorrectPriceException {
        ShoppingCart cart = updateItemQuantity(itemId, quantity); // <--- вызываем правильный метод
        return appMapper.toDTO(cart); 
    }

    @Transactional
    public ShoppingCartDTO addProductToCart(Long productId, int quantity) throws EmptyProductException, IncorrectQuantityException {
        ShoppingCart cart = getCurrentUserCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ищем, есть ли уже этот товар в корзине
        ShoppingItem existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            ShoppingItem newItem = new ShoppingItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        shoppingCartRepository.save(cart);

        // Создаём DTO вручную
        List<ShoppingItemDTO> itemsDTO = cart.getItems().stream()
                .map(i -> new ShoppingItemDTO(
                        i.getId(),
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getProduct().getPrice()
                ))
                .toList();

        double totalPrice = itemsDTO.stream()
                .mapToDouble(i -> i.getQuantity() * i.getPricePerUnit())
                .sum();

        return new ShoppingCartDTO(cart.getId(), cart.getCustomer().getId(), itemsDTO, totalPrice);
    }


    public ShoppingCart updateItemQuantity(Long itemId, int quantity) throws IncorrectQuantityException {
        ShoppingItem item = itemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item not found"));

        // Проверка, что товар принадлежит текущему пользователю
        if (!item.getCart().getCustomer().equals(getCurrentCustomer())) {
            throw new RuntimeException("Access denied");
        }

        if (quantity <= 0) {
            itemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            itemRepository.save(item);
        }

        return item.getCart();
    }

    public ShoppingCart removeItemFromCurrentUserCart(Long itemId) {
        ShoppingItem item = itemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getCart().getCustomer().equals(getCurrentCustomer())) {
            throw new RuntimeException("Access denied");
        }

        ShoppingCart cart = item.getCart();
        cart.getItems().remove(item);
        itemRepository.delete(item);

        return shoppingCartRepository.save(cart);
    }

    public void clearCurrentUserCart() {
        Customer customer = getCurrentCustomer();
        ShoppingCart cart = customer.getShoppingCart();
        if (cart != null) {
            cart.getItems().clear();
            shoppingCartRepository.save(cart);
        }
    }

    private Customer getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Customer customer) {
            return customer;
        }

        throw new RuntimeException("Authenticated user is not a Customer");
    }
    /**
     * Adds a new {@link ShoppingCart} to the database.
     *
     * @param cart the shopping cart to add
     *
     * @see ShoppingCartRepository#save(Object)
     */
    public ShoppingCart addShoppingCart(ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    /**
     * Removes an existing {@link ShoppingCart} from the database.
     *
     * <p>If the shopping cart exists (by ID), it will be deleted; otherwise, nothing happens.</p>
     *
     * @param cart the shopping cart to remove
     *
     * @see ShoppingCartRepository#delete(Object)
     * @see ShoppingCartRepository#existsById(Object)
     */
    public void removeShoppingCart(ShoppingCart cart) {
        if (cart != null && shoppingCartRepository.existsById(cart.getId())) {
            shoppingCartRepository.delete(cart);
        }
    }

    /**
     * Removes a {@link ShoppingCart} by its ID.
     *
     * @param id the ID of the shopping cart to delete
     *
     * @see ShoppingCartRepository#deleteById(Object)
     */
    public void removeShoppingCartById(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    /**
     * Removes a {@link ShoppingCart} associated with a specific {@link Customer}.
     *
     * @param customer the customer whose shopping cart should be deleted
     * @throws EmptyLoginException if no shopping cart exists for the given customer
     *
     * @see ShoppingCartRepository#findByCustomer(Customer)
     * @see ShoppingCartRepository#delete(Object)
     */
    public void removeShoppingCartByCustomer(Customer customer) throws EmptyLoginException {
        Optional<ShoppingCart> cart = shoppingCartRepository.findByCustomer(customer);
        if (cart.isPresent()) {
            shoppingCartRepository.delete(cart.get());
        } else {
            throw new EmptyLoginException("Customer not found with login: " + customer.getLogin());
        }
    }
    
    
    public ShoppingCartDTO removeItemDTO(Long itemId) 
            throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {
        ShoppingCart cart = removeItemFromCurrentUserCart(itemId);
        return appMapper.toDTO(cart); 
    }

    


    /**
     * Updates an existing {@link ShoppingCart}.
     *
     * <p>The shopping cart must have a valid ID that exists in the database. Otherwise,
     * a {@link RuntimeException} is thrown.</p>
     *
     * @param cart the shopping cart to update
     * @throws RuntimeException if the shopping cart does not exist or ID is null
     *
     * @see ShoppingCartRepository#save(Object)
     * @see ShoppingCartRepository#existsById(Object)
     */
    public ShoppingCart editShoppingCart(ShoppingCart cart) {
        if (cart.getId() == null || !shoppingCartRepository.existsById(cart.getId())) {
            throw new RuntimeException("Shopping cart not found");
        }
       return shoppingCartRepository.save(cart);
    }
}
