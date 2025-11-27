package com.goldenleaf.shop.model;

import java.time.LocalDate;

import jakarta.persistence.*;

/**
 * Entity representing an administrative user of the online shop system.
 * <p>
 * Extends the base {@link User} entity and adds admin-specific attributes:
 * a secret recovery word and a flag indicating super administrator privileges.
 * </p>
 * <p>
 * Super administrators have elevated rights such as managing other admins,
 * accessing audit logs, and modifying global system settings.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see User
 * @since 1.0
 */
@Entity
@Table(name = "admins")
public class Admin extends User {

    /**
     * Secret word/phrase used as a second authentication factor or for account recovery.
     * <p>
     * Must be treated as highly sensitive data. Never log or expose in APIs.
     * </p>
     */
    @Column(name = "secret_word", length = 100)
    private String secretWord;

    /**
     * Flag indicating whether this admin has super administrator privileges.
     * <p>
     * Default value is {@code false}.
     * </p>
     */
    @Column(name = "is_super_admin", nullable = false)
    private boolean isSuperAdmin;

    /** Default constructor required by JPA. Creates a regular (non-super) admin. */
    public Admin() {
        this.isSuperAdmin = false;
        this.secretWord = "default";
    }

    /**
     * Full constructor for creating a fully initialized admin account.
     *
     * @param login           admin login/username
     * @param passwordHash    securely hashed password
     * @param name            full name of the administrator
     * @param lastActivity    date of last activity
     * @param secretWord      secret recovery phrase (can be null only during initialization)
     * @param isSuperAdmin    {@code true} if this is a super administrator
     */
    public Admin(String login,
                 String passwordHash,
                 String name,
                 LocalDate lastActivity,
                 String secretWord,
                 boolean isSuperAdmin) {
        super(login, passwordHash, name, lastActivity);
        this.secretWord = secretWord != null ? secretWord : "default";
        this.isSuperAdmin = isSuperAdmin;
    }

    /**
     * Returns the secret recovery word.
     * <p>
     * <strong>Security:</strong> This is sensitive data. Do not log or expose.
     * </p>
     *
     * @return the secret word (never {@code null} after object construction)
     */
    public String getSecretWord() {
        return this.secretWord;
    }

    /**
     * Updates the secret word.
     * <p>
     * Null values are ignored to prevent accidental clearing of the secret word.
     * </p>
     *
     * @param secretWord new secret word (must not be {@code null})
     */
    public void setSecretWord(String secretWord) {
        if (secretWord != null && !secretWord.isBlank()) {
            this.secretWord = secretWord.trim();
        }
    }

    /**
     * Checks whether this admin has super administrator privileges.
     *
     * @return {@code true} if this is a super admin, {@code false} otherwise
     */
    public boolean getSuperAdmin() {
        return this.isSuperAdmin;
    }

    /**
     * Grants or revokes super administrator status.
     *
     * @param isSuperAdmin {@code true} to promote admin to super admin
     */
    public void setSuperAdmin(boolean isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

 


}