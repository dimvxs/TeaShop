package com.goldenleaf.shop.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.goldenleaf.shop.exception.*;

import jakarta.persistence.*;

/**
 * Entity representing a customer (registered user) of the online shop.
 * <p>
 * Extends the base {@link User} class and adds customer-specific attributes:
 * mobile phone, unique email, bonus points, a shopping cart and payment methods.
 * </p>
 * <p>
 * A customer can:
 * <ul>
 *   <li>accumulate and spend bonus points</li>
 *   <li>have exactly one active {@link ShoppingCart}</li>
 *   <li>attach multiple {@link CreditCard}s</li>
 *   <li>leave {@link Review}s on {@link Product}s</li>
 * </ul>
 * </p>
 *
 * @author GoldenLeaf Team
 * @see User
 * @see ShoppingCart
 * @see CreditCard
 * @see Review
 * @since 1.0
 */
@Entity
@Table(name = "customers")
public class Customer extends User {

    /** Customer's mobile phone number. Mandatory field, minimum 3 characters after trimming. */
    @Column(nullable = false)
    private String mobile;

    /**
     * Customer's email address.
     * <p>Must be unique across the system and contain at least 3 non-whitespace characters.</p>
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * List of payment cards belonging to this customer.
     * <p>One-to-Many relationship with cascade-all and orphan removal enabled.</p>
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> payments = new ArrayList<>();

    /** Number of bonus points accumulated by the customer. Cannot be negative. */
    private int bonusPoints;

    /**
     * The shopping cart associated with this customer.
     * <p>One-to-One relationship with cascade-all operations.</p>
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private ShoppingCart shoppingCart;

    /** Default constructor. Initializes bonus points to zero. */
    public Customer() {
        bonusPoints = 0;
    }

    /**
     * Full constructor creating a customer with all required fields.
     *
     * @param login           user login
     * @param passwordHash    hashed password
     * @param name            customer's full name
     * @param lastActivity    date of the last user activity
     * @param mobile          mobile phone number (must be valid)
     * @param email           email address (must be valid and unique)
     * @param bonusPoints     initial bonus points (must be greater than or equal to 0)
     * @param shoppingCart    customer's shopping cart (cannot be {@code null})
     * @param payments        list of payment cards (cannot be {@code null})
     *
     * @throws IncorrectMobileException      if the mobile number is invalid
     * @throws IncorrectEmailException       if the email address is invalid
     * @throws IncorrectBonusPointsValue     if bonus points are negative
     * @throws EmptyShoppingCartException    if shopping cart is {@code null}
     * @throws NullPaymentException          if the payments list is {@code null}
     *
     * @see #checkField(String)
     */
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
    ) throws IncorrectMobileException, IncorrectBonusPointsValue, IncorrectEmailException,
             EmptyShoppingCartException, NullPaymentException {
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
    
    public Customer(String login, String passwordHash, String phone, String email ) 
			throws IncorrectMobileException, IncorrectEmailException {
		super(login, passwordHash, login, LocalDate.now());
		
		if (!checkField(phone)) {
			throw new IncorrectMobileException("Incorrect mobile");
		}
		if (!checkField(email)) {
			throw new IncorrectEmailException("Incorrect email");
		}
		
		this.mobile = phone;
		this.email = email;
		this.bonusPoints = 0;
		ShoppingCart cart = new ShoppingCart();
	    cart.setCustomer(this); // <-- важно!
	    this.shoppingCart = cart;
        this.payments = new ArrayList<>();
	}

    /**
     * Utility method to validate string fields (mobile, email).
     * <p>A field is considered valid when it is not {@code null}, not blank,
     * and contains at least 3 characters after trimming.</p>
     *
     * @param value the string value to check
     * @return {@code true} if the value is valid, {@code false} otherwise
     */
    private boolean checkField(String value) {
        return value != null && !value.isBlank() && value.trim().length() >= 3;
    }

    /**
     * Returns the customer's mobile phone number.
     *
     * @return mobile phone number
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets a new mobile phone number.
     *
     * @param mobile new mobile number
     * @throws IncorrectMobileException if the provided mobile number is invalid
     */
    public void setMobile(String mobile) throws IncorrectMobileException {
        if (!checkField(mobile)) {
            throw new IncorrectMobileException("Incorrect mobile");
        }
        this.mobile = mobile;
    }

    /**
     * Returns the customer's email address.
     *
     * @return email address
     */
    public String getEmail() {
        return email;
    }
    
    public Customer getCurrentCustomer() {
    			return this;
    }

    /**
     * Sets a new email address.
     *
     * @param email new email address
     * @throws IncorrectEmailException if the provided email is invalid
     */
    public void setEmail(String email) throws IncorrectEmailException {
        if (!checkField(email)) {
            throw new IncorrectEmailException("Incorrect email");
        }
        this.email = email;
    }

    /**
     * Returns the current amount of bonus points.
     *
     * @return bonus points count
     */
    public int getBonusPoints() {
        return bonusPoints;
    }

    
  
    /**
     * Sets the exact amount of bonus points.
     * <p>Only positive values are accepted; otherwise the operation is ignored.</p>
     *
     * @param bonuses new bonus points value (must be greater than 0)
     */
    public void setBonusPoints(int bonuses) {
        if (bonuses > 0) {
            this.bonusPoints = bonuses;
        }
    }

    /**
     * Adds the specified number of bonus points to the current balance.
     * <p>Only positive values are added.</p>
     *
     * @param bonuses number of points to add (must be greater than 0)
     */
    public void addBonusPoints(int bonuses) {
        if (bonuses > 0) {
            this.bonusPoints += bonuses;
        }
    }

    /**
     * Returns the customer's shopping cart.
     *
     * @return associated {@link ShoppingCart}, may be {@code null} if not yet assigned
     */
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Assigns a shopping cart to this customer and establishes bidirectional relationship.
     *
     * @param shoppingCart the shopping cart to set (can be {@code null} to detach)
     */
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        if (shoppingCart != null) {
            shoppingCart.setCustomer(this);
        }
    }

    /**
     * Returns the list of payment cards linked to this customer.
     *
     * @return unmodifiable view of payment cards is not provided - direct list access
     */
    public List<CreditCard> getPayments() {
        return payments;
    }

    /**
     * Creates and adds a review for the specified product.
     *
     * @param product  the product being reviewed (cannot be {@code null})
     * @param content  review text (cannot be blank)
     * @param rating   rating from 1 to 5
     * @throws IncorrectRatingException if rating is not in range 1-5
     * @throws EmptyAuthorException     if author (current customer) is invalid
     * @throws EmptyContentException    if review content is blank
     * @throws EmptyProductException    if product is {@code null}
     *
     * @see Review
     * @see Product#addReview(Review)
     */
    public void makeReview(Product product, String content, int rating)
            throws IncorrectRatingException, EmptyAuthorException,
                   EmptyContentException, EmptyProductException {
        Review review = new Review(this, content, rating, product);
        product.addReview(review);
    }

    /**
     * Adds a credit card to the customer's payment methods and sets the bidirectional link.
     *
     * @param card the credit card to add (cannot be {@code null})
     * @throws NullCustomerException if the card's customer side cannot be set
     */
    public void addPayment(CreditCard card) throws NullCustomerException {
        card.setCustomer(this);
        payments.add(card);
    }

    /**
     * Removes a credit card from the customer's payment methods and clears the bidirectional link.
     *
     * @param card the credit card to remove
     * @throws NullCustomerException if the card's customer reference cannot be cleared
     */
    public void removePayment(CreditCard card) throws NullCustomerException {
        payments.remove(card);
        card.setCustomer(null);
    }

	public String getName()

    {
		return this.email;
		}
}