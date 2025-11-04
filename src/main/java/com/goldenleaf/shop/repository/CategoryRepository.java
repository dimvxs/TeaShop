package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
Optional<Category> findByName(String name);

}
