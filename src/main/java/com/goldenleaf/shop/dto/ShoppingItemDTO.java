package com.goldenleaf.shop.dto;
import jakarta.validation.constraints.*;

public class ShoppingItemDTO {

	    private Long id;
	    private Long productId;
	    private String productName;
	    private int quantity;
	    private double pricePerUnit;
	    private String imageUrl;

    public ShoppingItemDTO() {}

    public ShoppingItemDTO(Long id, Long productId, String productName, int quantity, double pricePerUnit, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.imageUrl = imageUrl;
        
    }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

	public double getPricePerUnit() {
		// TODO Auto-generated method stub
		return this.pricePerUnit;
	}
	
	public Long getProductId() {
	    return productId;
	}

	public void setProductId(Long productId) {
	    this.productId = productId;
	}

	public String getProductName() {
	    return productName;
	}

	public void setProductName(String productName) {
	    this.productName = productName;
	}

}
