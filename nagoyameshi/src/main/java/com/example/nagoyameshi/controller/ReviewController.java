package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewPostForm;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReviewService;

@RequestMapping("/restaurants")
@Controller
public class ReviewController {
	private final RestaurantRepository restaurantRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	
	public ReviewController(RestaurantRepository restaurantRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
		this.restaurantRepository = restaurantRepository;
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
	}
	
	// レビュー一覧用のメソッド
	@GetMapping("/{id}/reviews")
	public String index(@PathVariable(name = "id") Integer restaurantId,
						@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
						Model model) 
	{
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		// restaurantIdをもとにレビューを表示
		Page<Review> reviewPage = reviewRepository.findByRestaurant(restaurantRepository.getReferenceById(restaurantId), pageable);
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("reviewPage",reviewPage);
		
		return "reviews/index";
	}
	
	// フォームクラスのインスタンスをビューに渡す
	@GetMapping("/{id}/reviews/post")
	public String post(@PathVariable(name = "id") Integer restaurantId, Model model) {
		// idから民宿情報をゲット
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("reviewPostForm", new ReviewPostForm());
		return "reviews/post";
	}
	
	// フォームの送信先を担当
	@PostMapping("/{id}/reviews/create")
	public String create(@ModelAttribute @Validated ReviewPostForm reviewPostForm,
						 @PathVariable(name = "id") Integer restaurantId,
						 BindingResult bindingResult, 
						 RedirectAttributes redirectAttributes,
						 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						 Model model) {
		
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		User user = userDetailsImpl.getUser();
		
		// エラーがあるときフォーム画面を返す
		if(bindingResult.hasErrors()) {
			return "reviews/post";
		}
		
		reviewService.create(reviewPostForm, restaurant, user);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");

		return "redirect:/restaurants/{id}";
	}
	
	
	// レビュー編集用
	@GetMapping("/{restaurantId}/reviews/{reviewId}/edit")
	public String edit(@PathVariable(name = "restaurantId") Integer restaurantId,
					   @PathVariable(name = "reviewId") Integer reviewId,
					   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
					   Model model) 
	{
		Review review = reviewRepository.getReferenceById(reviewId);
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		User user = userDetailsImpl.getUser();
		
		ReviewEditForm reviewEditForm = new ReviewEditForm(reviewId, review.getTitle(), null, review.getEvaluation(), review.getComment());
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("user", user);
		model.addAttribute("reviewEditForm", reviewEditForm);
		return "reviews/edit";
	}
	
	// フォームの送信先を担当
	@PostMapping("/{id}/reviews/{reviewId}/update")
	public String update(@ModelAttribute @Validated ReviewEditForm reviewEditForm,
						 @PathVariable(name = "id") Integer restaurantId,
						 @PathVariable(name = "reviewId") Integer reviewId,
						 BindingResult bindingResult, 
						 RedirectAttributes redirectAttributes,
						 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						 Model model) 
	{
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		User user = userDetailsImpl.getUser();
		
		// エラーがあるときフォーム画面を返す
		if(bindingResult.hasErrors()) {
			return "reviews/edit";
		}
		
		reviewService.update(reviewEditForm, restaurant, user);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを更新しました。");
		return "redirect:/restaurants/{id}";
	}
	
	@PostMapping("/{id}/reviews/{reviewId}/delete")
	public String delete(@PathVariable(name = "id") Integer restaurantId, RedirectAttributes redirectAttributes) {
		reviewRepository.deleteById(restaurantId);
		
		redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");
		return "redirect:/restaurants/{id}";
	}
}
