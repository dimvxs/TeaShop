package com.goldenleaf.shop.dto;


import java.time.LocalDate;
import java.util.List;

public class CustomerDTO extends UserDTO {

    private String mobile;
    private String email;
    private int bonusPoints;
    private Long shoppingCartId;
    private List<Long> paymentIds;

    public CustomerDTO() {}

    public CustomerDTO(Long id, String login, String name, LocalDate lastActivity,
                       String mobile, String email, int bonusPoints,
                       Long shoppingCartId, List<Long> paymentIds) {
        setId(id);
        setLogin(login);
        setName(name);
        setLastActivity(lastActivity);
        this.mobile = mobile;
        this.email = email;
        this.bonusPoints = bonusPoints;
        this.shoppingCartId = shoppingCartId;
        this.paymentIds = paymentIds;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public List<Long> getPaymentIds() {
        return paymentIds;
    }

    public void setPaymentIds(List<Long> paymentIds) {
        this.paymentIds = paymentIds;
    }
}
