package com.goldenleaf.shop.service;


import java.util.List;
import org.springframework.stereotype.Service;

import com.goldenleaf.shop.dto.AdminDTO;
import com.goldenleaf.shop.dto.CategoryDTO;
import com.goldenleaf.shop.exception.EmptyLastActivityException;
import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.model.Admin;
import com.goldenleaf.shop.model.Category;
import com.goldenleaf.shop.repository.CategoryRepository;

/**
 * Service class for managing {@link Category} entities.
 * <p>
 * Provides business logic for retrieving, adding, updating, and deleting categories.
 * Acts as an intermediary between controllers and the {@link CategoryRepository}.
 * </p>
 *
 * @see Category
 * @see CategoryRepository
 */
@Service
public class CategoryService {

    /**
     * Repository used for performing CRUD operations on {@link Category} entities.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Constructs a new {@code CategoryService} with the provided repository.
     *
     * @param repo the repository used to perform operations on categories
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see CategoryRepository
     */
    public CategoryService(CategoryRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("CategoryRepository cannot be null");
        }
        this.categoryRepository = repo;
    }

    /**
     * Retrieves all categories from the database.
     *
     * @return a {@link List} of all {@link Category} entities
     *
     * @see CategoryRepository#findAll()
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieves a {@link Category} by its unique ID.
     *
     * @param id the ID of the category
     * @return the {@link Category} with the specified ID
     * @throws RuntimeException if no category with the given ID exists
     *
     * @see CategoryRepository#findById(Object)
     */
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    /**
     * Retrieves a {@link Category} by its unique name.
     *
     * @param name the name of the category
     * @return the {@link Category} with the specified name
     * @throws RuntimeException if no category with the given name exists
     *
     * @see CategoryRepository#findByName(String)
     */
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found by name: " + name));
    }

    /**
     * Adds a new {@link Category} to the database.
     *
     * @param category the category to add
     *
     * @see CategoryRepository#save(Object)
     */
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
    
    public Category create(CategoryDTO dto) throws EmptyNameException {
		Category category = new Category(
			dto.getName()
		);

		return categoryRepository.save(category);
	}
    
 
    
    public Category update(Long id, CategoryDTO dto) throws EmptyNameException {
    	
    			Category category = getCategoryById(id);
    			if (dto.getName() != null && !dto.getName().isBlank()) {
					category.setName(dto.getName());
				}
    			return categoryRepository.save(category);
    }
    
 

    /**
     * Removes an existing {@link Category} from the database.
     *
     * <p>If the category exists (by ID), it will be deleted; otherwise, nothing happens.</p>
     *
     * @param category the category to remove
     *
     * @see CategoryRepository#delete(Object)
     * @see CategoryRepository#existsById(Object)
     */
    public void removeCategory(Category category) {
        if (category != null && categoryRepository.existsById(category.getId())) {
            categoryRepository.delete(category);
        }
    }

    /**
     * Removes a {@link Category} by its ID.
     *
     * @param id the ID of the category to delete
     *
     * @see CategoryRepository#deleteById(Object)
     */
    public void removeCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Removes a {@link Category} by its name.
     *
     * @param name the name of the category to delete
     * @throws RuntimeException if no category with the given name exists
     *
     * @see CategoryRepository#findByName(String)
     * @see CategoryRepository#delete(Object)
     */
    public void removeCategoryByName(String name) {
        categoryRepository.findByName(name)
                .ifPresentOrElse(
                        categoryRepository::delete,
                        () -> { throw new RuntimeException("Category not found with name: " + name); }
                );
    }

    /**
     * Updates an existing {@link Category}.
     *
     * <p>The category must have a valid ID that exists in the database. Otherwise,
     * a {@link RuntimeException} is thrown.</p>
     *
     * @param category the category to update
     * @throws RuntimeException if the category does not exist or ID is null
     *
     * @see CategoryRepository#save(Object)
     * @see CategoryRepository#existsById(Object)
     */
    public void editCategory(Category category) {
        if (category.getId() == null || !categoryRepository.existsById(category.getId())) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.save(category);
    }
}
