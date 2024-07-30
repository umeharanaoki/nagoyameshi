package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.service.ReviewService;

@RequestMapping("/admin/restaurants/{restaurant_id}/reviews")
@Controller
public class AdminReviewController {
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	private final RestaurantRepository restaurantRepository;
	
	AdminReviewController(ReviewRepository reviewRepository, ReviewService reviewService, RestaurantRepository restaurantRepository) {
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
		this.restaurantRepository = restaurantRepository;
	}
	
	// 管理者用レビュー一覧
	@GetMapping
	public String reviews(@PathVariable(name = "restaurant_id") Integer restaurantId, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		Page<Review> reviewPage = reviewRepository.findByRestaurantIdAndHiddenFalse(restaurantId, pageable);
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("reviewPage", reviewPage);
		
		return "admin/restaurants/reviews";
	}
	
	// レビューを非表示にする
	@PostMapping("/{review_id}/hidden")
	public String hidden(@PathVariable(name = "restaurant_id") Integer restaurantId,
						 @PathVariable(name = "review_id") Integer reviewId,
						 RedirectAttributes redirectAttributes) {
		reviewService.hidden(reviewId);
		
		redirectAttributes.addFlashAttribute("successMessage", "レビューを非表示にしました。");
		
		// リダイレクトURLに動的にIDを埋め込む
		return String.format("redirect:/admin/restaurants/%d/reviews", restaurantId);
	}
}
