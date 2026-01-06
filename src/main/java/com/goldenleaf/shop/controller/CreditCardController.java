package com.goldenleaf.shop.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldenleaf.shop.dto.CategoryDTO;
import com.goldenleaf.shop.dto.CreditCardDTO;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.model.Category;
import com.goldenleaf.shop.model.CreditCard;
import com.goldenleaf.shop.service.CategoryService;
import com.goldenleaf.shop.service.CreditCardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/creditcards")
@RequiredArgsConstructor
public class CreditCardController {
private final CreditCardService creditCardService;
	
	public CreditCardController(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<CreditCard> get(@PathVariable Long id) {
	
		return ResponseEntity.ok(creditCardService.getCardById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<CreditCard> create(@RequestBody CreditCard card) throws EmptyNameException {
		// Implementation goes here
		return ResponseEntity.ok(creditCardService.addCreditCard(card));
	}


	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		creditCardService.removeCreditCardById(id);
		return ResponseEntity.noContent().build();
	}
}
