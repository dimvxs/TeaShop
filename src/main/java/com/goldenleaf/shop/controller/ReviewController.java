package com.goldenleaf.shop.controller;
import java.util.List;

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

import com.goldenleaf.shop.dto.ReviewDTO;
import com.goldenleaf.shop.exception.EmptyAuthorException;
import com.goldenleaf.shop.exception.EmptyContentException;
import com.goldenleaf.shop.exception.EmptyLoginException;
import com.goldenleaf.shop.exception.EmptyNameException;
import com.goldenleaf.shop.exception.EmptyProductException;
import com.goldenleaf.shop.exception.IncorrectRatingException;
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
	
	
//	@GetMapping("/{id}")
//	public ResponseEntity<Review> get(@PathVariable("id") Long id) {
//	
//		return ResponseEntity.ok(reviewService.getReviewById(id));
//	}
//	

	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<ReviewDTO>> getByProduct(@PathVariable("productId") Long productId) {
	    return ResponseEntity.ok(reviewService.getReviewsDtoByProduct(productId));
	}

	
	@PostMapping
	@PreAuthorize("isAuthenticated()") // или ADMIN если надо
	public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO dto) throws IncorrectRatingException, EmptyAuthorException, EmptyContentException, EmptyProductException, EmptyLoginException {
		System.out.println("DTO IN: " + dto);
		return ResponseEntity.ok(reviewService.addReview(dto));
	}

	


	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
			public ResponseEntity<Review> update(@RequestBody Review review) throws EmptyNameException {
				return ResponseEntity.ok(reviewService.editReview(review));
			}
	
	

	   @DeleteMapping("/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		   reviewService.removeReviewById(id);
	        return ResponseEntity.noContent().build();
	   }
	   
	   
	

	    // Добавить отзыв (только авторизованные)
//	    @PostMapping
//	    @PreAuthorize("isAuthenticated()")
//	    public ResponseEntity<Review> create(@RequestBody ReviewDTO dto) {
//	        return ResponseEntity.ok(reviewService.addReview(dto));
//	    }
	   
}
