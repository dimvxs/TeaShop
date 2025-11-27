package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for {@link com.goldenleaf.shop.model.Category} entity.
 * <p>
 * Used for:
 * <ul>
 *   <li>Creating or updating product categories via REST API</li>
 *   <li>Returning category information in responses (list, details)</li>
 *   <li>Transferring data between layers (controller ↔ service)</li>
 * </ul>
 * </p>
 * <p>
 * Category names should be unique and descriptive (e.g., "Smartphones", "Laptops", "Books").
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    private String name;

    /** Default constructor required for JSON deserialization (Jackson, Gson) */
    public CategoryDTO() {}

    /**
     * Convenience constructor for manual object creation or mapping.
     *
     * @param id   category identifier (may be null for new categories)
     * @param name category name
     */
    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO that)) return false;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}