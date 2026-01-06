package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.*;
import java.time.YearMonth;

/**
 * Data Transfer Object for creating or updating a credit card payment method.
 * <p>
 * <strong>WARNING:</strong> This DTO is intended <strong>only for input</strong>
 * (receiving data from the client). It must <strong>never</strong> be used in API responses
 * to return full card details — this would violate PCI DSS compliance.
 * </p>
 * <p>
 * For output, use a masked version (e.g., {@code CreditCardResponseDTO}) that shows only:
 * <ul>
 *   <li>Last 4 digits</li>
 *   <li>Card brand (Visa, MasterCard, etc.)</li>
 *   <li>Expiry date</li>
 *   <li>Holder name</li>
 * </ul>
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
public class CreditCardDTO {

    private Long id;

    @NotBlank(message = "Cardholder name is required")
    @Size(min = 2, max = 100, message = "Cardholder name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Cardholder name must contain only letters and spaces")
    private String holderName;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "^[0-9]{13,19}$", message = "Card number must contain 13–19 digits only")
    private String cardNumber;

    @NotNull(message = "Expiry date is required")
    @FutureOrPresent(message = "Card must not be expired")
    private YearMonth expiry;

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be positive")
    private Long customerId;

    /** Default constructor required for JSON binding */
    public CreditCardDTO() {}

    public CreditCardDTO(Long id, String holderName, String cardNumber,
                         YearMonth expiry, Long customerId) {
        this.id = id;
        this.holderName = holderName;
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.customerId = customerId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public YearMonth getExpiry() { return expiry; }
    public void setExpiry(YearMonth expiry) { this.expiry = expiry; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
   

    @Override
    public String toString() {
        return "CreditCardDTO{" +
                "id=" + id +
                ", holderName='" + holderName + '\'' +
                ", cardNumber='**** **** **** " + (cardNumber != null ? cardNumber.substring(cardNumber.length() - 4) : "null") + '\'' +
                ", expiry=" + expiry +
                ", customerId=" + customerId +
                '}';
    }
}