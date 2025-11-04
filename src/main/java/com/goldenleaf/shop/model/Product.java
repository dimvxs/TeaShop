package com.goldenleaf.shop.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;

@Entity
public class Product {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String brand;
private double price;
@ElementCollection
private List<String> imageUrls = new ArrayList<>();
@ManyToMany
@JoinTable(
    name = "product_category",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id")
)
private Set<Category> categories;

@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Review> reviews = new ArrayList<>();


public Product() {}

public Product(String name, String brand, double price, Set<Category> categories, List<String> imageUrls, List<Review> reviews)

{

		if(name == null || name.isBlank())
		{
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		
		if(brand == null || brand.isBlank())
		{
			throw new IllegalArgumentException("Brand cannot be null or empty");
		}
		  
		if(price < 0)
		{
			throw new IllegalArgumentException("Price cannot be negative");
		}
		
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.categories = categories;
		this.imageUrls = imageUrls;
		this.reviews = reviews;
		

}


public Long getId() {
    return id;
}

public String getName() { return name; }

public void setName(String name) { 
	
	if(name == null || name.isBlank())
	{
		throw new IllegalArgumentException("Name cannot be null or empty");
	}
	
	this.name = name;
	}

public String getBrand() { return brand; }
public void setBrand(String brand) { this.brand = brand; }

public double getPrice() { return price; }
public void setPrice(double price) { this.price = price; }

public Set<Category> getCategories() { return categories; }
public void setCategories(Set<Category> categories) { this.categories = categories; }

public List<String> getImageUrls() { return imageUrls; }
public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

public List<Review> getReviews() { return reviews; }
public void setReviews(List<Review> reviews) { this.reviews = reviews; }

public void addReview(Review review)
{
	reviews.add(review);
}

}
