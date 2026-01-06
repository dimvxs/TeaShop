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

import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.model.Review;
import com.goldenleaf.shop.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Review> get(@PathVariable Long id) {
	
		return ResponseEntity.ok(reviewService.getReviewById(id));
	}
	

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Review> create(@RequestBody Review review) {
		return ResponseEntity.ok(reviewService.addReview(review));
	}
	

	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
			public ResponseEntity<Review> update(@RequestBody Review review) throws EmptyNameException {
				return ResponseEntity.ok(reviewService.editReview(review));
			}
	
	

	   @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
		   reviewService.removeReviewById(id);
	        return ResponseEntity.noContent().build();
	   }
	   
}
