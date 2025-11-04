package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

     Optional<Product> findByName(String name);
     Optional<Product> findByBrand(String brand);
     void deleteByName(String name);
}
