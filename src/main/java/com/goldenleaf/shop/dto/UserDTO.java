package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Abstract base Data Transfer Object for all user types in the system.
 * <p>
 * Provides common fields shared by:
 * <ul>
 *   <li>{@link CustomerDTO} – regular shoppers</li>
 *   <li>{@link AdminDTO} – administrators</li>
 * </ul>
 * </p>
 * <p>
 * Used in:
 * <ul>
 *   <li>Authentication responses</li>
 *   <li>User profile endpoints</li>
 *   <li>Admin panel – user management</li>
 *   <li>Audit logs and activity tracking</li>
 * </ul>
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
public abstract class UserDTO {

    @Positive(message = "User ID must be positive")
    private Long id;

    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 50, message = "Login must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Login can only contain letters, digits and underscore")
    private String login;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @PastOrPresent(message = "Last activity date cannot be in the future")
    private LocalDate lastActivity;

    /** Required for JSON deserialization (Jackson/Gson) */
    protected UserDTO() {}

    /**
     * Convenience constructor used by subclasses and mappers.
     *
     * @param id           user identifier (may be null for new users)
     * @param login        unique login/username
     * @param name         full name
     * @param lastActivity date of last user action
     */
    protected UserDTO(Long id, String login, String name, LocalDate lastActivity) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.lastActivity = lastActivity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDate lastActivity) {
        this.lastActivity = lastActivity;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", lastActivity=" + lastActivity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}