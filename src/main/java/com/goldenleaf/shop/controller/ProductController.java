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
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.service.ProductService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> get(@PathVariable Long id) {
	
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	

	
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> create(@RequestBody Product product) {
		return ResponseEntity.ok(productService.addProduct(product));
	}
	
	


	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
			public ResponseEntity<Product> update(@RequestBody Product product) throws EmptyNameException {
				return ResponseEntity.ok(productService.editProduct(product));
			}
	
	

	   @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        productService.removeProductById(id);
	        return ResponseEntity.noContent().build();
	   }
	   

}
