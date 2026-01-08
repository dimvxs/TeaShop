package com.goldenleaf.shop.model;

import java.time.LocalDate;

import com.goldenleaf.shop.exception.EmptyLastActivityException;
import com.goldenleaf.shop.exception.EmptyLoginException;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * Abstract base entity representing a user in the system.
 *
 * <p>This is the root of a {@link InheritanceType#JOINED} inheritance hierarchy.
 * Concrete user types such as {@code Customer} and {@code Admin} extend this class.
 * Common fields are stored in the {@code users} table, while type-specific columns
 * are stored in their own dedicated tables.</p>
 *
 * <p>The {@link #login} field is unique across all user types and is used as the primary
 * credential identifier. The {@link #passwordHash} stores the hashed password (e.g. BCrypt).
 * The {@link #lastActivity} field is updated on every successful login or meaningful action.</p>
 *
 * @author Your Name / GoldenLeaf Team
 * @since  1.0.0
 *
 * @see    Customer
 * @see    Admin
 * @see    EmptyLoginException
 * @see    EmptyLastActivityException
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

	/**
     * Primary key, auto-generated from the {@code user_seq} sequence.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    
    /**
     * Unique login (username) of the user.
     * Must be at least 3 characters long.
     *
     * @throws EmptyLoginException if an attempt is made to set a null or blank login
     */
    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 3)
    private String login;
    
    /**
     * Hashed password (e.g. BCrypt, Argon2). Plain-text passwords are never stored.
     */
    @Column(nullable = false)
    private String passwordHash;
    
    /**
     * Full/display name of the user.
     */
    @Column(nullable = false)
    private String name;
    
    /**
     * Date of the user's last activity in the system.
     * Updated automatically on login or relevant actions.
     *
     * @throws EmptyLastActivityException if an attempt is made to set {@code null}
     */
    @Column(nullable = false)
    private LocalDate lastActivity;
    

    /** Default constructor required by JPA. */
    public User() {}
    
    
    /**
     * Convenience constructor for creating a new user instance.
     *
     * @param login         unique login
     * @param passwordHash  already hashed password
     * @param name          display name
     * @param lastActivity  date of last activity (usually {@code LocalDate.now()} on creation)
     *
     * @throws EmptyLoginException        if login is blank
     * @throws EmptyLastActivityException if lastActivity is null
     */
    public User(String login, String passwordHash, String name, LocalDate lastActivity) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.name = name;
        this.lastActivity = lastActivity;
    }
    
    public User(String login, String passwordHash) {
		this.login = login;
		this.passwordHash = passwordHash;
		this.name = null;
		this.lastActivity = LocalDate.now();
	}

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
		this.id = id;
		
	}
    
    
    /**
     * Returns the user's login.
     *
     * @return the login
     * @throws EmptyLoginException if login is null or blank (should never happen in a persisted entity)
     */
    public String getLogin() throws EmptyLoginException {
        if (login == null || login.isBlank()) {
            throw new EmptyLoginException("Login is empty");
        }
        return login;
    }
    
    
    /**
     * Sets the user's login.
     *
     * @param login new login value
     * @throws EmptyLoginException if the provided login is null or blank
     */
    public void setLogin(String login) throws EmptyLoginException {
        if (login == null || login.isBlank()) {
            throw new EmptyLoginException("Login cannot be empty");
        }
        this.login = login;
    }

    public void setPassword(String hashedPassword) {
       this.passwordHash = hashedPassword;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }

    public LocalDate getLastActivity() {
        return lastActivity;
    }

    
   
    /**
     * Updates the last activity date.
     *
     * @param lastActivity new activity date
     * @throws EmptyLastActivityException if {@code null} is provided
     */
    public void setLastActivity(LocalDate lastActivity) throws EmptyLastActivityException {
        if (lastActivity == null) {
            throw new EmptyLastActivityException("Last activity cannot be null");
        }
        this.lastActivity = lastActivity;
    }
    
    
    public void setName(String name) {
    	if(name != null || !name.isBlank()) {
	
		 this.name = name;
    	}
    }
  
}


