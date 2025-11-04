package com.goldenleaf.shop.service;

import java.util.List;

import com.goldenleaf.shop.model.Review;
import com.goldenleaf.shop.model.Customer;
import com.goldenleaf.shop.model.Product;
import com.goldenleaf.shop.repository.ReviewRepository;

public class ReviewService {
private final ReviewRepository reviewRepository;

public ReviewService(ReviewRepository repo)
{
	this.reviewRepository = repo;
}




public List<Review> getAllReviews()
{
	return reviewRepository.findAll();
}

public List<Review> getAllReviewsByProduct(Product product)
{
	return reviewRepository.findByProduct(product);
}

public List<Review> getAllReviewsOfCustomer(Customer customer)
{
	return reviewRepository.findByCustomer(customer);
}
public List<Review> getAllReviewsOfCustomerLogin(String login)
{
	return reviewRepository.findByCustomerLogin(login);
}


public Review getReviewById(Long id)
{
	 return reviewRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
}




public void addReview(Review review)
{
	reviewRepository.save(review);
}


public void removeReview(Review review)
{
    if (review != null && reviewRepository.existsById(review.getId())) {
    	reviewRepository.delete(review);
    }
}

public void removeReviewById(Long id)
{

	reviewRepository.deleteById(id);
}


public void editReview(Review review)
{
   if (review.getId() == null || !reviewRepository.existsById(review.getId())) {
        throw new RuntimeException("Review not found");
    }
   reviewRepository.save(review); 
}

public List<Review> getReviewsByCustomer(Customer customer)
{
	return reviewRepository.findByCustomer(customer);
}

public List<Review> getReviewsByCustomerLogin(String login)
{
	return reviewRepository.findByCustomerLogin(login);
}

public List<Review> getReviewsByProduct(Product product)
{
	return reviewRepository.findByProduct(product);
}

public List<Review> getReviewsByProductName(String name)
{
	return reviewRepository.findByProductName(name);
}

public void removebyProduct(Product product)
{
	reviewRepository.deleteByProduct(product);
}

public void removebyProductName(String name)
{
	reviewRepository.deleteByProductName(name);
}

}
