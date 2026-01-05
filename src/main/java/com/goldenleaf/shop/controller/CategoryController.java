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

import com.goldenleaf.shop.dto.CategoryDTO;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.model.Category;
import com.goldenleaf.shop.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> get(@PathVariable Long id) {
	
		return ResponseEntity.ok(categoryService.getCategoryById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> create(@RequestBody CategoryDTO dto) throws EmptyNameException {
		// Implementation goes here
		return ResponseEntity.ok(categoryService.create(dto));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody CategoryDTO dto) throws EmptyNameException {
		// Implementation goes here
		return ResponseEntity.ok(categoryService.update(id, dto));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		categoryService.removeCategoryById(id);
		return ResponseEntity.noContent().build();
	}
	


}
