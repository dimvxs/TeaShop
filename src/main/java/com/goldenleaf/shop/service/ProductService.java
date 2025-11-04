package com.goldenleaf.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.model.User;
import com.goldenleaf.shop.repository.ProductRepository;

@Service
public class ProductService {
private final ProductRepository productRepository;

public ProductService(ProductRepository repo)
{
	this.productRepository = repo;
}


public List<Product> getAllProducts()
{
	return productRepository.findAll();
}

public Product getProductById(Long id)
{
	 return productRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
}


public Product getProductByName(String name)
{
	return productRepository.findByName(name)
			.orElseThrow(() -> new RuntimeException("Product not found by name: " + name));
}

public Product getProductByBrand(String brand)
{
	return productRepository.findByBrand(brand)
			.orElseThrow(() -> new RuntimeException("Product not found by brand: " + brand));
}



public void addProduct(Product product)
{
	productRepository.save(product);
}


public void removeProduct(Product product)
{
    if (product != null && productRepository.existsById(product.getId())) {
    	productRepository.delete(product);
    }
}

public void removeProductById(Long id)
{

	productRepository.deleteById(id);
}

public void removeProductByName(String name)
{

	productRepository.deleteByName(name);
}

public void editProduct(Product product)
{
   if (product.getId() == null || !productRepository.existsById(product.getId())) {
        throw new RuntimeException("Product not found");
    }
   productRepository.save(product); 
}

}
