package com.goldenleaf.shop.service;

import com.goldenleaf.shop.dto.ProductCreateDTO;
import com.goldenleaf.shop.dto.ProductUpdateDTO;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.model.Category;
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.repository.CategoryRepository;
import com.goldenleaf.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repo, CategoryRepository categoryRepo) {
		if (repo == null) {
			throw new IllegalArgumentException("ProductRepository cannot be null");
		}
		if (categoryRepo == null) {
			throw new IllegalArgumentException("CategoryRepository cannot be null");
		}
		this.productRepository = repo;
		this.categoryRepository = categoryRepo;
	}

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found by name: " + name));
    }

    public Product getProductByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .orElseThrow(() -> new RuntimeException("Product not found by brand: " + brand));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void removeProduct(Product product) {
        if (product != null && productRepository.existsById(product.getId())) {
            productRepository.delete(product);
        }
    }

    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void removeProductByName(String name) {
        productRepository.deleteByName(name);
    }

    public Product editProduct(Product product) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            throw new RuntimeException("Product not found");
        }
        return productRepository.save(product);
    }
    
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }



    private List<String> saveImages(List<MultipartFile> files) throws IOException {
        List<String> urls = new ArrayList<>();
        
        // Абсолютный путь в корень твоего проекта (или рядом с jar)
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);  // создаст папку, даже если её нет
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = dirPath.resolve(fileName);
            
            // Сохраняем файл
            file.transferTo(filePath.toFile());

            // Возвращаем URL, доступный через статику
            urls.add("/uploads/" + fileName);
        }

        return urls;
    }
    public Product createProductWithImages(ProductCreateDTO dto, List<MultipartFile> images)
            throws IOException, EmptyNameException {

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));

        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setCategories(categories);
        product.setDescription(dto.getDescription());

        if (images != null && !images.isEmpty()) {
            List<String> urls = saveImages(images);
            product.setImageUrls(urls);
        }

        return productRepository.save(product);
    }

    public Product updateProductWithImages(Long id, ProductUpdateDTO dto, List<MultipartFile> newImages)
            throws IOException, EmptyNameException {

        Product product = getProductById(id);

        // Обязательное поле — всегда обновляем
        product.setName(dto.getName());

        // Опциональные поля
        if (dto.getBrand() != null) {
            product.setBrand(dto.getBrand());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getCategoryIds() != null) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
            product.setCategories(categories);
        }

        // Обработка старых изображений
        if (dto.getExistingImageUrls() != null) {
            product.getImageUrls().retainAll(dto.getExistingImageUrls());
        }
        // Если null — оставляем старые фото без изменений

        // Добавление новых изображений
        if (newImages != null && !newImages.isEmpty()) {
            List<String> newUrls = saveImages(newImages);
            product.getImageUrls().addAll(newUrls);
        }
        
        if(dto.getDescription() != null) {
			product.setDescription(dto.getDescription());
		}

        return productRepository.save(product);
    }
    
    
}