package com.goldenleaf.shop.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;


@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 @ManyToOne
	 @JoinColumn(name = "user_id")
	private Customer author;
	@Column(length = 1000)
	private String content;
	@Column(nullable=false)
	@Min (1)
	@Max (5)
	private int rating;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	
	public Review() {}
	
	public Review(Customer author, String content, int rating, Product product)
	{
		if(author == null)
		{
			throw new IllegalArgumentException("Author should be specified");
		}
		
		if(content == null || content.isBlank())
		{
			throw new IllegalArgumentException("Content must be set");
		}
		
		if(rating < 1 || rating > 5)
		{
			throw new IllegalArgumentException("Rating should be between 1 and 5");
		}
		
		if(product == null)
		{
			throw new IllegalArgumentException("Product must be set");
		}

		this.author = author;
		this.content = content;
		this.rating = rating;
		this.product = product;
		
	}
	
	
	
	 public Long getId() {
	        return id;
	    }

	    public Customer getAuthor() {
	        return author;
	    }

	    public String getContent() {
	        return content;
	    }

	    public int getRating() {
	        return rating;
	    }

	   
	    public void setAuthor(Customer author) {
	        if (author == null) {
	            throw new IllegalArgumentException("Author should be specified");
	        }
	        this.author = author;
	    }

	    public void setContent(String content) {
	        if (content == null || content.isBlank()) {
	            throw new IllegalArgumentException("Content must be set");
	        }
	        this.content = content;
	    }

	    public void setRating(int rating) {
	        if (rating < 1 || rating > 5) {
	            throw new IllegalArgumentException("Rating should be between 1 and 5");
	        }
	        this.rating = rating;
	    }
	
	
}
