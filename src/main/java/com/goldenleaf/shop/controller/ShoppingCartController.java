package com.goldenleaf.shop.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.goldenleaf.shop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

    @GetMapping
    public ResponseEntity<ShoppingCartDTO> getCurrentUserCart() throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {
        ShoppingCartDTO cart = shoppingCartService.getCurrentUserCartDTO();
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartDTO> get(@PathVariable("id") Long id) throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {
        ShoppingCartDTO cart = shoppingCartService.getShoppingCartByIdDTO(id);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update-item")
    public ResponseEntity<ShoppingCartDTO> updateCartItem(@RequestBody UpdateCartItemRequest request) 
            throws IncorrectQuantityException, EmptyProductException, IncorrectPriceException {
        ShoppingCartDTO cart = shoppingCartService.updateCartItem(request.getItemId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove-item/{itemId}")
    public ResponseEntity<ShoppingCartDTO> removeItem(@PathVariable("itemId") Long itemId)
            throws EmptyProductException, IncorrectQuantityException, IncorrectPriceException {
        ShoppingCartDTO cartDTO = shoppingCartService.removeItemDTO(itemId);
        return ResponseEntity.ok(cartDTO);
    }


    @PostMapping("/add")
    public ResponseEntity<ShoppingCartDTO> addToCart(@RequestBody AddToCartRequest request) 
            throws EmptyProductException, IncorrectQuantityException {
        ShoppingCartDTO cart = shoppingCartService.addProductToCart(request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }
  

}
