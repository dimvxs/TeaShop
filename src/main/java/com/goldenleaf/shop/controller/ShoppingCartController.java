package com.goldenleaf.shop.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goldenleaf.shop.dto.AddToCartRequest;
import com.goldenleaf.shop.dto.ShoppingCartDTO;
import com.goldenleaf.shop.dto.UpdateCartItemRequest;
import com.goldenleaf.shop.exception.EmptyProductException;
import com.goldenleaf.shop.exception.IncorrectPriceException;
import com.goldenleaf.shop.exception.IncorrectQuantityException;
import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.service.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    public record UpdateItemDTO(Long itemId, int quantity) {}
    
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

    @GetMapping
    public ResponseEntity<ShoppingCartDTO> getCurrentCart() {
        try {
            ShoppingCartDTO cartDTO = shoppingCartService.getCurrentUserCartDTOSafe();
            return ResponseEntity.ok(cartDTO);
        } catch (Exception e) {
            e.printStackTrace(); // на всякий случай, чтобы видеть ошибку
            return ResponseEntity.status(500).body(new ShoppingCartDTO());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartDTO> get(@PathVariable("id") Long id) 
            throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {
        return ResponseEntity.ok(shoppingCartService.getShoppingCartByIdDTO(id));
    }

//    @PutMapping("/update-item")
//    public ResponseEntity<ShoppingCartDTO> updateCartItem(@RequestBody UpdateCartItemRequest request) 
//            throws IncorrectQuantityException, EmptyProductException, IncorrectPriceException {
//        return ResponseEntity.ok(shoppingCartService.updateCartItem(request.getItemId(), request.getQuantity()));
//    }

    


    @PutMapping("/update-item")
    public ShoppingCartDTO updateCartItem(@RequestBody UpdateItemDTO dto) throws IncorrectQuantityException, EmptyProductException, IncorrectPriceException {
        Long itemId = dto.itemId(); // вместо dto.id
        return shoppingCartService.updateCartItem(itemId, dto.quantity());
    }

    
    @DeleteMapping("/remove-item/{itemId}")
    public ResponseEntity<ShoppingCartDTO> removeItem(@PathVariable("itemId") Long itemId) 
            throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {
        return ResponseEntity.ok(shoppingCartService.removeItemDTO(itemId));
    }


    @PostMapping("/add")
    public ResponseEntity<ShoppingCartDTO> addToCart(@RequestBody AddToCartRequest request)
            throws EmptyProductException, IncorrectQuantityException {
        System.out.println("POST /api/cart/add вызван с данными: " + request);
        ShoppingCartDTO result = shoppingCartService.addProductToCart(request.getProductId(), request.getQuantity());
        System.out.println("Результат добавления: " + result);
        return ResponseEntity.ok(result);
    }

}
