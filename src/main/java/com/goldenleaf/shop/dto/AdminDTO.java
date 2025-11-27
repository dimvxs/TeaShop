package com.goldenleaf.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating or updating an {@link com.goldenleaf.shop.model.Admin}.
 * <p>
 * Extends {@link UserDTO} and adds admin-specific fields.
 * Used only for input (registration, update) — never for output responses.
 * </p>
 * <p>
 * <strong>Security:</strong> The {@code secretWord} must NEVER be returned in API responses.
 * </p>
 *
 * @author GoldenLeaf Team
 * @since 1.0
 */
public class AdminDTO extends UserDTO {

    @NotBlank(message = "Secret word is mandatory")
    @Size(min = 4, max = 100, message = "Secret word must be between 4 and 100 characters")
    private String secretWord;

    private boolean isSuperAdmin;

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "secretWord='[PROTECTED]'" +
                ", isSuperAdmin=" + isSuperAdmin +
                "} " + super.toString();
    }
}