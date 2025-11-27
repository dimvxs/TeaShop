package com.goldenleaf.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldenleaf.shop.model.CreditCard;

/**
 * Spring Data JPA repository for {@link CreditCard} entities.
 * <p>
 * Provides standard CRUD operations inherited from {@link JpaRepository}
 * and specialized query/deletion methods for payment method management.
 * </p>
 * <p>
 * Credit cards are sensitive data. Although the full card number is stored in this example
 * (for simplicity), in production systems it is strongly recommended to:
 * <ul>
 *   <li>Store only the last 4 digits + masked version</li>
 *   <li>Use tokenization (e.g., Stripe, Adyen, bank vault)</li>
 *   <li>Encrypt at rest and never log full card numbers</li>
 * </ul>
 * </p>
 * <p>
 * This repository is automatically implemented by Spring at runtime — no manual implementation needed.
 * </p>
 *
 * @author GoldenLeaf Team
 * @see CreditCard
 * @see JpaRepository
 * @since 1.0
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    /**
     * Finds a credit card by its full card number.
     * <p>
     * Used primarily during:
     * <ul>
     *   <li>Checkout when customer selects a previously saved card</li>
     *   <li>Duplicate card detection before saving a new payment method</li>
     *   <li>Admin or support tools looking up a card by number (with proper authorization)</li>
     * </ul>
     * </p>
     * <p>
     * <strong>Security note:</strong> Full card numbers are PCI-sensitive data.
     * Access to this method should be strictly limited and audited.
     * </p>
     *
     * @param number the full credit card number (digits only, no spaces/dashes)
     * @return an {@link Optional} containing the matching {@link CreditCard} if found,
     *         or {@link Optional#empty()} if no card with this number exists
     */
    Optional<CreditCard> findByNumber(String number);

    /**
     * Deletes a credit card by its full card number.
     * <p>
     * Provides a convenient way to remove a saved payment method without first loading the entity.
     * Useful in scenarios such as:
     * <ul>
     *   <li>User explicitly removing a saved card from their account</li>
     *   <li>Admin/support permanently deleting a compromised card</li>
     *   <li>Scheduled cleanup of expired or flagged cards</li>
     * </ul>
     * </p>
     * <p>
     * <strong>Security & auditing:</strong> This is a destructive operation on sensitive data.
     * Should be protected by proper authorization and logged for compliance.
     * </p>
     *
     * @param number the full card number of the card to delete
     */
    void deleteByCardNumber(String number);

    /**
     * Optional: more secure alternative — search by masked number or token (recommended in production).
     * <p>
     * Example: findByLastFourDigitsAndCustomerId(String lastFour, Long customerId)
     * </p>
     */
    /*
    Optional<CreditCard> findByLastFourAndCustomerId(String lastFour, Long customerId);
    */
}