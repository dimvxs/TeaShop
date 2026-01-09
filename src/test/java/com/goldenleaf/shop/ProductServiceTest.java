package com.goldenleaf.shop;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.model.Category;
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.repository.CategoryRepository;
import com.goldenleaf.shop.repository.ProductRepository;
import com.goldenleaf.shop.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        productService = new ProductService(productRepository, categoryRepository);
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = List.of(
                new Product(), new Product()
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Found() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productService.getProductById(999L));
        assertEquals("Product not found with id: 999", ex.getMessage());
    }

    @Test
    void testAddProduct() throws EmptyNameException {
        Product product = new Product();
        product.setName("Green Tea");
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product);
        assertEquals("Green Tea", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testRemoveProductById() {
        doNothing().when(productRepository).deleteById(1L);

        productService.removeProductById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

}
