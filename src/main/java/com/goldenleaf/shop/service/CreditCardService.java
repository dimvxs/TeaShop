package com.goldenleaf.shop.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.goldenleaf.shop.dto.AdminDTO;
import com.goldenleaf.shop.dto.CategoryDTO;
import com.goldenleaf.shop.dto.CreditCardDTO;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.model.Admin;
import com.goldenleaf.shop.model.Category;
import com.goldenleaf.shop.model.CreditCard;
import com.goldenleaf.shop.repository.CreditCardRepository;

/**
 * Service class for managing {@link CreditCard} entities.
 * <p>
 * Provides business logic for retrieving, adding, updating, and deleting credit cards.
 * Acts as an intermediary between controllers and the {@link CreditCardRepository}.
 * </p>
 *
 * @see CreditCard
 * @see CreditCardRepository
 */
@Service
public class CreditCardService {

    /**
     * Repository used for performing CRUD operations on {@link CreditCard} entities.
     */
    private final CreditCardRepository creditCardRepository;

    /**
     * Constructs a new {@code CreditCardService} with the provided repository.
     *
     * @param repo the repository used to perform operations on credit cards
     * @throws IllegalArgumentException if {@code repo} is {@code null}
     *
     * @see CreditCardRepository
     */
    public CreditCardService(CreditCardRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("CreditCardRepository cannot be null");
        }
        this.creditCardRepository = repo;
    }

    /**
     * Retrieves all credit cards from the database.
     *
     * @return a {@link List} of all {@link CreditCard} entities
     *
     * @see CreditCardRepository#findAll()
     */
    public List<CreditCard> getAllCards() {
        return creditCardRepository.findAll();
    }

    /**
     * Retrieves a {@link CreditCard} by its unique ID.
     *
     * @param id the ID of the credit card
     * @return the {@link CreditCard} with the specified ID
     * @throws RuntimeException if no credit card with the given ID exists
     *
     * @see CreditCardRepository#findById(Object)
     */
    public CreditCard getCardById(Long id) {
        return creditCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit card not found with id: " + id));
    }

    /**
     * Retrieves a {@link CreditCard} by its unique number.
     *
     * @param number the number of the credit card
     * @return the {@link CreditCard} with the specified number
     * @throws RuntimeException if no credit card with the given number exists
     *
     * @see CreditCardRepository#findByNumber(String)
     */
    public CreditCard getCardByNumber(String cardNumber) {
        return creditCardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Credit card not found with number: " + cardNumber));
    }

    /**
     * Adds a new {@link CreditCard} to the database.
     *
     * @param card the credit card to add
     *
     * @see CreditCardRepository#save(Object)
     */
    public CreditCard addCreditCard(CreditCard card) {
       return creditCardRepository.save(card);
    }

    /**
     * Removes an existing {@link CreditCard} from the database.
     *
     * <p>If the credit card exists (by ID), it will be deleted; otherwise, nothing happens.</p>
     *
     * @param card the credit card to remove
     *
     * @see CreditCardRepository#delete(Object)
     * @see CreditCardRepository#existsById(Object)
     */
    public void removeCreditCard(CreditCard card) {
        if (card != null && creditCardRepository.existsById(card.getId())) {
            creditCardRepository.delete(card);
        }
    }

    /**
     * Removes a {@link CreditCard} by its ID.
     *
     * @param id the ID of the credit card to delete
     *
     * @see CreditCardRepository#deleteById(Object)
     */
    public void removeCreditCardById(Long id) {
        creditCardRepository.deleteById(id);
    }

    /**
     * Removes a {@link CreditCard} by its number.
     *
     * @param number the number of the credit card to delete
     * @throws RuntimeException if no credit card with the given number exists
     *
     * @see CreditCardRepository#findByNumber(String)
     * @see CreditCardRepository#delete(Object)
     */
    public void removeCreditCardByNumber(String cardNumber) {
        creditCardRepository.findByCardNumber(cardNumber)
                .ifPresentOrElse(
                        creditCardRepository::delete,
                        () -> { throw new RuntimeException("Credit card not found with number: " + cardNumber); }
                );
    }


    /**
     * Updates an existing {@link CreditCard}.
     *
     * <p>The credit card must have a valid ID that exists in the database. Otherwise,
     * a {@link RuntimeException} is thrown.</p>
     *
     * @param card the credit card to update
     * @throws RuntimeException if the credit card does not exist or ID is null
     *
     * @see CreditCardRepository#save(Object)
     * @see CreditCardRepository#existsById(Object)
     */
    public void editCreditCard(CreditCard card) {
        if (card.getId() == null || !creditCardRepository.existsById(card.getId())) {
            throw new RuntimeException("Credit card not found");
        }
        creditCardRepository.save(card);
    }
}
