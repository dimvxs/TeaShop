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
import com.goldenleaf.shop.repository.CustomerRepository;
import com.goldenleaf.shop.repository.ProductRepository;
import com.goldenleaf.shop.repository.ShoppingCartRepository;
import com.goldenleaf.shop.repository.ShoppingItemRepository;
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
    private final CustomerRepository customerRepository;
    private final AppMapper appMapper;


    /**
     * Constructs a new {@code ShoppingCartService} with the provided repository.
     *
     * @param repo the repository used to perform operations on shopping carts
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see ShoppingCartRepository
     */
    public ShoppingCartService(ShoppingCartRepository repo, ShoppingItemRepository itemRepository, ProductRepository productRepository, AppMapper appMapper, CustomerRepository customerRepository) {
        if (repo == null) {
            throw new IllegalArgumentException("ShoppingCartRepository cannot be null");
        }
        this.shoppingCartRepository = repo;
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
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
    





    public ShoppingCartDTO getShoppingCartByIdDTO(Long id) 
            throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {
        ShoppingCart cart = shoppingCartRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        return appMapper.toDTO(cart);
    }

    
    public ShoppingCartDTO getCartByCustomerIdDTO(Long customerId) 
            throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {

        if (customerId == null) {
            // Если нет пользователя — пустая корзина
            return new ShoppingCartDTO();
        }

        // Предполагаем, что у тебя есть CustomerRepository
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        ShoppingCart cart = customer.getShoppingCart();
        if (cart == null) {
            // Если корзины нет — возвращаем пустую
            return new ShoppingCartDTO();
        }

        // Используем MapStruct или ручное преобразование в DTO
        return appMapper.toDTO(cart);
    }

    

    public ShoppingCartDTO updateCartItem(Long itemId, int quantity) 
            throws IncorrectQuantityException, EmptyProductException, IncorrectPriceException {
        ShoppingCart cart = updateItemQuantity(itemId, quantity); 
        return appMapper.toDTO(cart); 
    }
    

    public ShoppingCart updateItemQuantity(Long itemId, int quantity) throws IncorrectQuantityException {
        if (itemId == null) {
            throw new IllegalArgumentException("Item id must not be null");
        }

        ShoppingItem item = itemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Item not found"));

        ShoppingCart cart = item.getCart();
        Customer currentCustomer = getCurrentCustomer();

        // Если корзина ещё не привязана к пользователю — привязываем её
        if (cart.getCustomer() == null) {
            cart.setCustomer(currentCustomer);
            shoppingCartRepository.save(cart); // сохраняем связь
            System.out.println("Cart was unassigned. Assigned to current customer: " + currentCustomer);
        }

        // Проверка прав: если корзина принадлежит другому пользователю
        if (!cart.getCustomer().equals(currentCustomer)) {
            throw new RuntimeException("Access denied");
        }
        System.out.println("currentCustomer = " + currentCustomer);
        System.out.println("cartCustomer = " + cart.getCustomer());


        // Обновление количества
        if (quantity == 0) {
            itemRepository.delete(item);
        } else {
            // увеличиваем текущее количество на переданное
        	item.setQuantity(quantity);
            itemRepository.save(item);
        }


        return cart;
    }



    
    
    
    @Transactional
    public ShoppingCartDTO addProductToCart(Long productId, int quantity)
            throws EmptyProductException, IncorrectQuantityException {

        Customer customer = getCurrentCustomer();
        if (customer == null) {
            throw new RuntimeException("Требуется авторизация для добавления товара в корзину");
        }

        // Получаем корзину пользователя или создаем новую
        ShoppingCart cart = customer.getShoppingCart();
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setCustomer(customer);
            customer.setShoppingCart(cart);
            shoppingCartRepository.save(cart); // сохраняем, чтобы появился cart_id
        }

        // Находим продукт
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EmptyProductException("Продукт не найден"));

        // Ищем существующий товар в корзине
        ShoppingItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Если есть, увеличиваем количество
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Если нет, создаем новый
            ShoppingItem newItem = new ShoppingItem(product, quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        // Пересчитываем totalPrice безопасно
        try {
            cart.setTotalPrice(cart.calculateTotalPrice());
        } catch (IncorrectPriceException e) {
            throw new RuntimeException("Ошибка при подсчете общей цены корзины", e);
        }

        // Сохраняем корзину
        shoppingCartRepository.save(cart);

        
        List<ShoppingItemDTO> itemsDTO = cart.getItems().stream()
        	    .map(item -> {
        	        Long pid = item.getProduct() != null ? item.getProduct().getId() : null;
        	        String pname = item.getProduct() != null ? item.getProduct().getName() : "Ошибка загрузки товара";
        	        double price = item.getProduct() != null ? item.getProduct().getPrice() : 0;
        	        String img = item.getProduct() != null && !item.getProduct().getImageUrls().isEmpty()
        	                     ? item.getProduct().getImageUrls().get(0)
        	                     : "/images/default-product.jpg";
System.out.println("Image URL for product ID  and name" + pid + ": " + img + "nama" + pname); 
        	        return new ShoppingItemDTO(
        	                item.getId(),
        	                pid,
        	                pname,
        	                item.getQuantity(),
        	                price,
        	                img   
        	        );
        	    })
        	    .toList();


        // Считаем totalPrice для DTO
        double totalPrice = itemsDTO.stream()
                .mapToDouble(i -> i.getQuantity() * i.getPricePerUnit())
                .sum();

        return new ShoppingCartDTO(cart.getId(), customer.getId(), itemsDTO, totalPrice);
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
            return null; 
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof Customer customer) {
            return customer;
        }
        
        return null;
    }
    
    
    
    
    @Transactional
    public ShoppingCartDTO getCurrentUserCartDTOSafe() 
            throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {

        Customer customer = getCurrentCustomer();
        if (customer == null) {
            return new ShoppingCartDTO();
        }

        ShoppingCart cart = customer.getShoppingCart();
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setCustomer(customer);
            cart = shoppingCartRepository.save(cart);
        }

        // Принудительно инициализируем список товаров
        cart.getItems().size();

        return appMapper.toDTO(cart);
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
