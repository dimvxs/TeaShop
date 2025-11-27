package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Admin;

/**
 * Spring Data JPA repository for {@link Admin} entities.
 * <p>
 * Provides CRUD operations inherited from {@link JpaRepository} and
 * additional query methods specific to administrator management.
 * </p>
 * <p>
 * The repository is automatically implemented by Spring at runtime
 * (no manual implementation required).
 * </p>
 * <p>
 * Key custom query:
 * <ul>
 *   <li>{@link #findBySecretWord(String)} – locates an admin by their secret recovery word/phrase.
 *       Used primarily for privileged operations or emergency authentication flows.</li>
 * </ul>
 * </p>
 *
 * @author GoldenLeaf Team
 * @see Admin
 * @see JpaRepository
 * @since 1.0
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Finds an administrator by their unique secret word (recovery phrase).
     * <p>
     * The secret word is treated as a secondary authentication factor.
     * This method is typically used in high-privilege scenarios such as:
     * <ul>
     *   <li>Password reset for administrators</li>
     *   <li>Emergency access / account recovery</li>
     *   <li>Two-step verification for destructive actions</li>
     * </ul>
     * </p>
     * <p>
     * <strong>Security note:</strong> The secret word must be stored securely
     * (e.g., hashed if feasible) and never logged or exposed in plain text.
     * </p>
     *
     * @param secretWord the secret recovery word/phrase to search for
     * @return an {@link Optional} containing the matching {@link Admin} if found,
     *         or {@link Optional#empty()} if no admin has this secret word
     */
    Optional<Admin> findBySecretWord(String secretWord);

    /**
     * Alternative explicit query version (optional – can be used instead of derived query above).
     * <p>
     * Kept for clarity and future expansion (e.g., case-insensitive search).
     * </p>
     *
     * @param secretWord the secret word to match
     * @return optional admin with the given secret word
     */
    /*
    @Query("SELECT a FROM Admin a WHERE a.secretWord = :secretWord")
    Optional<Admin> findBySecretWord(@Param("secretWord") String secretWord);
    */
}