package com.goldenleaf.shop.service;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.Admin;
import com.goldenleaf.shop.repository.AdminRepository;

/**
 * Service class providing business logic for working with {@link Admin} entities.
 * <p>
 * This service interacts with the {@link AdminRepository} to retrieve and remove
 * admin accounts using a unique secret word.
 * </p>
 *
 * @see Admin
 * @see AdminRepository
 */
@Service
public class AdminService {

    /**
     * Repository used for accessing {@link Admin} data.
     */
    private final AdminRepository adminRepository;

    /**
     * Creates an instance of {@code AdminService}.
     *
     * @param repo the repository used to perform CRUD operations on admin entities
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see AdminRepository
     */
    public AdminService(AdminRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("AdminRepository cannot be null");
        }
        this.adminRepository = repo;
    }

    /**
     * Finds an {@link Admin} by its secret word.
     *
     * @param secretWord the secret word used to identify the admin
     * @return the matching {@link Admin}
     * @throws RuntimeException if no admin with the provided secret word exists
     *
     * @see AdminRepository#findBySecretWord(String)
     */
    public Admin getAdminBySecretWord(String secretWord) {
        return adminRepository.findBySecretWord(secretWord)
            .orElseThrow(() ->
                new RuntimeException("Admin not found with secret word: " + secretWord));
    }

    /**
     * Deletes an {@link Admin} identified by its secret word.
     *
     * @param secretWord the secret word of the admin to delete
     * @throws RuntimeException if no admin with the given secret word is found
     *
     * @see AdminRepository#delete(Object)
     * @see AdminRepository#findBySecretWord(String)
     */
    public void removeAdminBySecretWord(String secretWord) {
        adminRepository.findBySecretWord(secretWord)
            .ifPresentOrElse(
                adminRepository::delete,
                () -> { throw new RuntimeException("Admin not found with secretWord: " + secretWord); }
            );
    }
}
