package com.goldenleaf.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.repository.ProductRepository;

/**
 * Service class for managing {@link Product} entities.
 * <p>
 * Provides business logic for retrieving, adding, updating, and deleting products.
 * Acts as an intermediary between controllers and the {@link ProductRepository}.
 * </p>
 *
 * @see Product
 * @see ProductRepository
 */
@Service
public class ProductService {

    /**
     * Repository used for performing CRUD operations on {@link Product} entities.
     */
    private final ProductRepository productRepository;

    /**
     * Constructs a new {@code ProductService} with the provided repository.
     *
     * @param repo the repository used to perform operations on products
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see ProductRepository
     */
    public ProductService(ProductRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("ProductRepository cannot be null");
        }
        this.productRepository = repo;
    }

    /**
     * Retrieves all products from the database.
     *
     * @return a {@link List} of all {@link Product} entities
     *
     * @see ProductRepository#findAll()
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a {@link Product} by its unique ID.
     *
     * @param id the ID of the product
     * @return the {@link Product} with the specified ID
     * @throws RuntimeException if no product with the given ID exists
     *
     * @see ProductRepository#findById(Object)
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    /**
     * Retrieves a {@link Product} by its name.
     *
     * @param name the name of the product
     * @return the {@link Product} with the specified name
     * @throws RuntimeException if no product with the given name exists
     *
     * @see ProductRepository#findByName(String)
     */
    public Product getProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found by name: " + name));
    }

    /**
     * Retrieves a {@link Product} by its brand.
     *
     * @param brand the brand of the product
     * @return the {@link Product} with the specified brand
     * @throws RuntimeException if no product with the given brand exists
     *
     * @see ProductRepository#findByBrand(String)
     */
    public Product getProductByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .orElseThrow(() -> new RuntimeException("Product not found by brand: " + brand));
    }

    /**
     * Adds a new {@link Product} to the database.
     *
     * @param product the product to add
     *
     * @see ProductRepository#save(Object)
     */
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    /**
     * Removes an existing {@link Product} from the database.
     *
     * <p>If the product exists (by ID), it will be deleted; otherwise, nothing happens.</p>
     *
     * @param product the product to remove
     *
     * @see ProductRepository#delete(Object)
     * @see ProductRepository#existsById(Object)
     */
    public void removeProduct(Product product) {
        if (product != null && productRepository.existsById(product.getId())) {
            productRepository.delete(product);
        }
    }

    /**
     * Removes a {@link Product} by its ID.
     *
     * @param id the ID of the product to delete
     *
     * @see ProductRepository#deleteById(Object)
     */
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Removes a {@link Product} by its name.
     *
     * @param name the name of the product to delete
     *
     * @see ProductRepository#deleteByName(String)
     */
    public void removeProductByName(String name) {
        productRepository.deleteByName(name);
    }

    /**
     * Updates an existing {@link Product}.
     *
     * <p>The product must have a valid ID that exists in the database. Otherwise,
     * a {@link RuntimeException} is thrown.</p>
     *
     * @param product the product to update
     * @throws RuntimeException if the product does not exist or ID is null
     *
     * @see ProductRepository#save(Object)
     * @see ProductRepository#existsById(Object)
     */
    public void editProduct(Product product) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            throw new RuntimeException("Product not found");
        }
        productRepository.save(product);
    }
}
