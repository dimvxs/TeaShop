package com.goldenleaf.shop.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.goldenleaf.shop.dto.AdminDTO;
import com.goldenleaf.shop.exception.EmptyLastActivityException;
import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.model.Admin;
import com.goldenleaf.shop.repository.AdminRepository;

import jakarta.persistence.EntityNotFoundException;

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
public class AdminService{

    /**
     * Repository used for accessing {@link Admin} data.
     */
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates an instance of {@code AdminService}.
     *
     * @param repo the repository used to perform CRUD operations on admin entities
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see AdminRepository
     */
    public AdminService(AdminRepository repo, PasswordEncoder passwordEncoder) {
        if (repo == null) {
            throw new IllegalArgumentException("AdminRepository cannot be null");
        }
        this.adminRepository = repo;
        
        if(passwordEncoder == null) {
			throw new IllegalArgumentException("PasswordEncoder cannot be null");
		}
        this.passwordEncoder = passwordEncoder;
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
    

    
    public Admin findById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
    }


    public Admin create(AdminDTO dto) {
        String rawPassword = dto.getPassword();
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password required");
        }

        Admin admin = new Admin(
            dto.getLogin(),
            passwordEncoder.encode(rawPassword),  // хешируем и передаём в конструктор
            dto.getName(),
            LocalDate.now(),
            dto.getSecretWord(),
            dto.isSuperAdmin()
        );

        return adminRepository.save(admin);
    }

    public Admin update(Long id, AdminDTO dto) throws EmptyLastActivityException, EmptyLoginException {
        Admin admin = findById(id);

        // Обновляем только то, что пришло в DTO
        if (dto.getLogin() != null && !dto.getLogin().isBlank()) {
            admin.setLogin(dto.getLogin());  // предполагаем, что в User есть setLogin
        }
        if (dto.getName() != null && !dto.getName().isBlank()) {
            admin.setName(dto.getName());    // предполагаем, что в User есть setName
        }
        if (dto.getLastActivity() != null) {
            admin.setLastActivity(dto.getLastActivity());
        }

        // Пароль обновляем только если передан
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // Поля из Admin
        admin.setSecretWord(dto.getSecretWord());
        admin.setSuperAdmin(dto.isSuperAdmin());

        return adminRepository.save(admin);
    }

    public void deleteById(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new EntityNotFoundException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }

	
}
