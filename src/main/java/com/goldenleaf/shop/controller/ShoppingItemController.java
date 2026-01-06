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
import com.goldenleaf.shop.model.ShoppingItem;
import com.goldenleaf.shop.service.ShoppingItemService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/shoppingitems")
@RequiredArgsConstructor
public class ShoppingItemController {

	private final ShoppingItemService shoppingItemService;
	
	public ShoppingItemController(ShoppingItemService shoppingItemService) {
		this.shoppingItemService = shoppingItemService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ShoppingItem> get(@PathVariable Long id) {
	
		return ResponseEntity.ok(shoppingItemService.getShoppingItemById(id));
	}
	

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ShoppingItem> create(@RequestBody ShoppingItem item) {
		return ResponseEntity.ok(shoppingItemService.addShoppingItem(item));
	}
	

	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
			public ResponseEntity<ShoppingItem> update(@RequestBody ShoppingItem item) throws EmptyNameException {
				return ResponseEntity.ok(shoppingItemService.editShoppingItem(item));
			}
	
	

	   @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
		   shoppingItemService.removeShoppingItemById(id);
	        return ResponseEntity.noContent().build();
	       
	   }
}
