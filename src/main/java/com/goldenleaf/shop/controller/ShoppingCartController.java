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

import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.model.ShoppingCart;
import com.goldenleaf.shop.service.ShoppingCartService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/shoppingcarts")
@RequiredArgsConstructor
public class ShoppingCartController {
	
	private final ShoppingCartService shoppingCartService;
	
  	public ShoppingCartController(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

  	
	@GetMapping("/{id}")
	public ResponseEntity<ShoppingCart> get(@PathVariable Long id) {
	
		return ResponseEntity.ok(shoppingCartService.getShoppingCartById(id));
	}
	

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ShoppingCart> create(@RequestBody ShoppingCart cart) {
		return ResponseEntity.ok(shoppingCartService.addShoppingCart(cart));
	}
	

	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
			public ResponseEntity<ShoppingCart> update(@RequestBody ShoppingCart cart) throws EmptyNameException {
				return ResponseEntity.ok(shoppingCartService.editShoppingCart(cart));
			}
	
	

	   @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
		   shoppingCartService.removeShoppingCartById(id);
	        return ResponseEntity.noContent().build();
	       
	   }
}
