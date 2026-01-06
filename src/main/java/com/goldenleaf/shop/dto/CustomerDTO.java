package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data Transfer Object for creating, updating or returning customer information.
 * <p>
 * Extends {@link UserDTO} to inherit common fields (id, login, name, lastActivity)
 * and adds customer-specific properties.
 * </p>
 * <p>
 * Used in:
 * <ul>
 *   <li>User registration / profile update</li>
 *   <li>Admin panel – viewing customer details</li>
 *   <li>API responses after login or profile fetch</li>
 *   <li>Order processing – retrieving customer context</li>
 * </ul>
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
public class CustomerDTO extends UserDTO {

    @Pattern(regexp = "^\\+?[0-9\\s\\-()]{10,20}$", 
             message = "Invalid mobile phone number format")
    @Size(max = 20, message = "Mobile number too long")
    private String mobile;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email too long")
    private String email;
    
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;  // не @NotBlank — при update может быть пустым

    @Min(value = 0, message = "Bonus points cannot be negative")
    private int bonusPoints = 0;

    private Long shoppingCartId;

    // Never return full card details — only IDs!
    private List<Long> paymentIds = Collections.emptyList();

    /** Default constructor required for JSON deserialization */
    public CustomerDTO() {}

    /**
     * Full constructor for manual mapping or testing.
     */
    public CustomerDTO(Long id, String login, String name, LocalDate lastActivity,
                       String mobile, String email, int bonusPoints,
                       Long shoppingCartId, List<Long> paymentIds) {
        super(id, login, name, lastActivity);
        this.mobile = mobile;
        this.email = email;
        this.bonusPoints = bonusPoints;
        this.shoppingCartId = shoppingCartId;
        this.paymentIds = paymentIds != null ? List.copyOf(paymentIds) : Collections.emptyList();
    }
    
    
    public CustomerDTO(Long id, String login, String email, String mobile, String password)
    {
    	 super(id, login);
    	 this.mobile = mobile;
		 this.email = email;
		 this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile != null ? mobile.trim() : null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public String getPassword() {
        return password;
    }


    /**
     * Returns an unmodifiable list of payment method IDs.
     * Prevents external modification and ensures immutability in responses.
     */
    public List<Long> getPaymentIds() {
        return paymentIds != null ? Collections.unmodifiableList(paymentIds) : Collections.emptyList();
    }

    public void setPaymentIds(List<Long> paymentIds) {
        this.paymentIds = paymentIds != null ? List.copyOf(paymentIds) : Collections.emptyList();
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id=" + getId() +
                ", login='" + getLogin() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", bonusPoints=" + bonusPoints +
                ", shoppingCartId=" + shoppingCartId +
                ", paymentIdsCount=" + (paymentIds != null ? paymentIds.size() : 0) +
                ", lastActivity=" + getLastActivity() +
                '}';
    }
}