package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findBySecretWord(String secretWord);
	
}
