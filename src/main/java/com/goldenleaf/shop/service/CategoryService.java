package com.goldenleaf.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.Category;
import com.goldenleaf.shop.model.User;
import com.goldenleaf.shop.repository.CategoryRepository;

@Service
public class CategoryService {
private final CategoryRepository categoryRepository;

public CategoryService(CategoryRepository repo)
{
	this.categoryRepository = repo;
}


public List<Category> getAllCategories()
{
	return categoryRepository.findAll();
}

public Category getCategoryById(Long id)
{
	 return categoryRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
}


public Category getCategoryByName(String name)
{
	return categoryRepository.findByName(name)
			.orElseThrow(() -> new RuntimeException("Category not found by name: " + name));
}



public void addCategory(Category category)
{
	categoryRepository.save(category);
}


public void removeCategory(Category category)
{
    if (category != null && categoryRepository.existsById(category.getId())) {
    	categoryRepository.delete(category);
    }
}

public void removeCategoryById(Long id)
{

	categoryRepository.deleteById(id);
}

public void removeCategoryByName(String name)
{

	categoryRepository.findByName(name)
     .ifPresentOrElse(
    		 categoryRepository::delete,
         () -> { throw new RuntimeException("Category not found with name: " + name); }
     );
}


public void editCategory(Category category)
{
   if (category.getId() == null || !categoryRepository.existsById(category.getId())) {
        throw new RuntimeException("Category not found");
    }
   categoryRepository.save(category); 
}

}
