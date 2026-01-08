package com.goldenleaf.shop.controller;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goldenleaf.shop.ProductMapper;
import com.goldenleaf.shop.dto.ProductCreateDTO;
import com.goldenleaf.shop.dto.ProductDTO;
import com.goldenleaf.shop.dto.ProductUpdateDTO;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.service.ProductService;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	private final ProductMapper productMapper;
	
	public ProductController(ProductService productService, ProductMapper productMapper) {
		this.productService = productService;
		this.productMapper = productMapper;
	}
	
	
//	@GetMapping("/{id}")
//	public ResponseEntity<Product> get(@PathVariable("id") Long id){
//	
//		return ResponseEntity.ok(productService.getProductById(id));
//	}
//	
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> get(@PathVariable("id") Long id)
	{
		return ResponseEntity.ok(
			    productMapper.toDto(productService.getProductById(id))
			);

	}

	
//	@GetMapping
//	public ResponseEntity<List<Product>> getAll() {
//	
//		return ResponseEntity.ok(productService.getAllProducts());
//	}
	
	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAll() {
	    return ResponseEntity.ok(
	        productMapper.toDtoList(productService.getAllProducts())
	    );
	}

	

	// Создание с файлами
//    @PostMapping(consumes = "multipart/form-data")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Product> create(
//            @RequestPart("product") @Valid ProductCreateDTO dto,
//            @RequestPart(value = "images", required = false) List<MultipartFile> images
//    ) throws IOException, EmptyNameException {
//        Product product = productService.createProductWithImages(dto, images);
//        return ResponseEntity.ok(product);
//    }
//	
	
	
	@PostMapping(consumes = "multipart/form-data")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDTO> create(
	        @RequestPart("product") @Valid ProductCreateDTO dto,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images
	) throws IOException, EmptyNameException {

	    Product product = productService.createProductWithImages(dto, images);
	    return ResponseEntity.ok(productMapper.toDto(product));
	}

	


	
	// Обновление с файлами
//	@PutMapping(value = "/{id}", consumes = "multipart/form-data")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Product> update(
//    		@PathVariable("id") Long id,
//            @RequestPart("product") @Valid ProductUpdateDTO dto,
//            @RequestPart(value = "images", required = false) List<MultipartFile> images
//    ) throws IOException, EmptyNameException {
//        Product product = productService.updateProductWithImages(id, dto, images);
//        return ResponseEntity.ok(product);
//    }

	
	
	@PutMapping(value = "/{id}", consumes = "multipart/form-data")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductDTO> update(
	        @PathVariable("id") Long id,
	        @RequestPart("product") @Valid ProductUpdateDTO dto,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images
	) throws IOException, EmptyNameException {

	    Product product = productService.updateProductWithImages(id, dto, images);
	    return ResponseEntity.ok(productMapper.toDto(product));
	}


	   @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
	        productService.removeProductById(id);
	        return ResponseEntity.noContent().build();
	   }
	   
	 
	   

}
