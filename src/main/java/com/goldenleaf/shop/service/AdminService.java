package com.goldenleaf.shop.service;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.Admin;
import com.goldenleaf.shop.repository.AdminRepository;

@Service
public class AdminService {
private final AdminRepository adminRepository;

public AdminService(AdminRepository repo)
{
	this.adminRepository = repo;
}

public Admin getAdminBySecretWord(String secretWord)
{
	return adminRepository.findBySecretWord(secretWord)
	 .orElseThrow(() -> new RuntimeException("Admin not found with secret word: " + secretWord));
}

public void removeAdminBySecretWord(String secretWord)
{
	adminRepository.findBySecretWord(secretWord)
    .ifPresentOrElse(
    		adminRepository::delete,
        () -> { throw new RuntimeException("Admin not found with secretWord: " + secretWord); }
    );
}

}
