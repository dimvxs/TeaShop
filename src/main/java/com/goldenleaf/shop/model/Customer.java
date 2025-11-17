package com.goldenleaf.shop.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.goldenleaf.shop.exception.EmptyAuthorException;
import com.goldenleaf.shop.exception.EmptyContentException;
import com.goldenleaf.shop.exception.EmptyProductException;
import com.goldenleaf.shop.exception.EmptyShoppingCartException;
import com.goldenleaf.shop.exception.IncorrectBonusPointsValue;
import com.goldenleaf.shop.exception.IncorrectEmailException;
import com.goldenleaf.shop.exception.IncorrectMobileException;
import com.goldenleaf.shop.exception.IncorrectRatingException;
import com.goldenleaf.shop.exception.NullCustomerException;
import com.goldenleaf.shop.exception.NullPaymentException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends User {

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> payments = new ArrayList<>();

    private int bonusPoints;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private ShoppingCart shoppingCart;

    public Customer() {
        bonusPoints = 0;
    }

    public Customer(
            String login,
            String passwordHash,
            String name,
            LocalDate lastActivity,
            String mobile,
            String email,
            int bonusPoints,
            ShoppingCart shoppingCart,
            List<CreditCard> payments
    ) throws IncorrectMobileException, IncorrectBonusPointsValue, IncorrectEmailException, EmptyShoppingCartException, NullPaymentException{
        super(login, passwordHash, name, lastActivity);

        if (!checkField(mobile)) {
            throw new IncorrectMobileException("Incorrect mobile");
        }
        if (!checkField(email)) {
            throw new IncorrectEmailException("Incorrect email");
        }
        if (bonusPoints < 0) {
            throw new IncorrectBonusPointsValue("Bonus points cannot be less than 0");
        }
        if (shoppingCart == null) {
            throw new EmptyShoppingCartException("Shopping cart cannot be null");
        }
        if (payments == null) {
            throw new NullPaymentException("Payment cannot be null");
        }

        this.mobile = mobile;
        this.email = email;
        this.bonusPoints = bonusPoints;
        this.shoppingCart = shoppingCart;
        this.payments = payments;
    }

    private boolean checkField(String value) {
        return value != null && !value.isBlank() && value.trim().length() >= 3;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) throws IncorrectMobileException{
        if (!checkField(mobile)) {
            throw new IncorrectMobileException("Incorrect mobile");
        }
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IncorrectEmailException{
        if (!checkField(email)) {
            throw new IncorrectEmailException("Incorrect email");
        }
        this.email = email;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonuses) {
        if (bonuses > 0) {
            this.bonusPoints = bonuses;
        }
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        if (shoppingCart != null) {
            shoppingCart.setCustomer(this);
        }
    }

    public void addBonusPoints(int bonuses) {
        if (bonuses > 0) {
            this.bonusPoints += bonuses;
        }
    }

    public void makeReview(Product product, String content, int rating) throws IncorrectRatingException, EmptyAuthorException, EmptyContentException, EmptyProductException {
        Review review = new Review(this, content, rating, product);
        product.addReview(review);
    }

    public void addPayment(CreditCard card) throws NullCustomerException {
        card.setCustomer(this);
        payments.add(card);
    }

    public void removePayment(CreditCard card) throws NullCustomerException {
        payments.remove(card);
        card.setCustomer(null);
    }
}

