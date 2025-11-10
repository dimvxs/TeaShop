package com.goldenleaf.shop.dto;

import java.time.YearMonth;

public class CreditCardDTO {

    private Long id;
    private String holderName;
    private String cardNumber;
    private YearMonth expiry;
    private Long customerId;

    public CreditCardDTO() {}

    public CreditCardDTO(Long id, String holderName, String cardNumber,
                         YearMonth expiry, Long customerId) {
        this.id = id;
        this.holderName = holderName;
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public YearMonth getExpiry() {
        return expiry;
    }

    public void setExpiry(YearMonth expiry) {
        this.expiry = expiry;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
