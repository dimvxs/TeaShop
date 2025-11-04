package com.goldenleaf.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenleaf.shop.model.CreditCard;
import com.goldenleaf.shop.model.User;
import com.goldenleaf.shop.repository.CreditCardRepository;
import com.goldenleaf.shop.repository.CustomerRepository;

@Service
public class CreditCardService {

	private final CreditCardRepository creditCardRepository;
	
	public CreditCardService(CreditCardRepository repo)
	{
		this.creditCardRepository = repo;
	}

	
	public List<CreditCard> getAllCards()
	{
		return creditCardRepository.findAll();
	}
	
	public CreditCard getCardById(Long id)
	{
		return creditCardRepository.findById(id)
				 .orElseThrow(() -> new RuntimeException("Credit card not found with id: " + id));
	}
	
	public CreditCard getCardByNumber(String number)
	{
		return creditCardRepository.findByNumber(number)
				 .orElseThrow(() -> new RuntimeException("Credit card not found with number: " + number));
	}
	
	
	

public void addCreditCard(CreditCard card)
{
	creditCardRepository.save(card);
}


public void removeCreditCard(CreditCard card)
{
    if (card != null && creditCardRepository.existsById(card.getId())) {
    	creditCardRepository.delete(card);
    }
}

public void removeCreditCardById(Long id)
{

	creditCardRepository.deleteById(id);
}

public void removeCreditCardByNumber(String number)
{

	creditCardRepository.findByNumber(number)
     .ifPresentOrElse(
    		 creditCardRepository::delete,
         () -> { throw new RuntimeException("Credit card not found with number: " + number); }
     );
}

public void editCreditCard(CreditCard card)
{
   if (card.getId() == null || !creditCardRepository.existsById(card.getId())) {
        throw new RuntimeException("Credit card not found");
    }
   creditCardRepository.save(card); 
}

}
