package com.example.nagoyameshi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.RestaurantService;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;
	private final RestaurantService restaurantService;
	private final FavoriteRepository favoriteRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	
	RestaurantController(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, RestaurantService restaurantService, FavoriteRepository favoriteRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
		this.restaurantService = restaurantService;
		this.favoriteRepository = favoriteRepository;
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
	}
	
	// 店舗一覧ページ
	@GetMapping
	public String index(Model model, 
						@RequestParam(name = "keyword", required = false) String keyword, 
						@RequestParam(name = "minBudget", required = false) Integer minBudget, 
						@RequestParam(name = "maxBudget", required = false) Integer maxBudget, 
						@RequestParam(name = "categoryId", required = false) Integer categoryId, 
						@RequestParam(name = "order", required = false) String order,
						@PageableDefault(page = 0, size = 10) Pageable pageable) {
		
		// 検索条件としてカテゴリが選択されている場合、categoryオブジェクトを準備
		Category category = null;
		if(categoryId != null) {  // カテゴリがnullでない(カテゴリが選択されている）なら処理を実行
			category = categoryRepository.getReferenceById(categoryId);
		}
		
		// 店舗一覧(検索機能付き）
		Page<Restaurant> restaurantPage = restaurantService.findRestaurants(keyword, minBudget, maxBudget, category, order, pageable);
		// 検索のためのカテゴリ一覧
		List<Category> categories = categoryRepository.findAll();
		// 各店のレビューの平均点をMapで管理
		Map<Integer, String> averageEvaluationMap = reviewService.calculateAverageEvaluations(restaurantPage);
		
		// 検索条件をもとにページを返す
		model.addAttribute("restaurantPage", restaurantPage);
		// 選択肢として表示するcategoryオブジェクトの一覧
		model.addAttribute("categories", categories);
		// 検索条件をページ遷移後も保持するための変数
		model.addAttribute("keyword", keyword);
		model.addAttribute("minBudget", minBudget);
		model.addAttribute("maxBudget", maxBudget);
		model.addAttribute("category", category);  // オブジェクト型
		model.addAttribute("order", order);
		// レビュー表示用
		model.addAttribute("averageEvaluationMap", averageEvaluationMap);
		
		return "restaurants/index";
	}
	
	// 店舗詳細ページ
	@GetMapping("/{restaurant_id}")
	public String show(@PathVariable(name = "restaurant_id") Integer restaurantId,
					   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
					   Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		Category category = categoryRepository.getReferenceById(restaurant.getCategory().getId());
		// ログインしていないユーザーがアクセスする場合に
		User user = null;
		Favorite favorite = null;
		if(userDetailsImpl != null) {
			user = userDetailsImpl.getUser();
			favorite = favoriteRepository.findByUserAndRestaurant(user, restaurant);
		}
		
		// 予約フォーム
		ReservationForm reservationForm = new ReservationForm();
		// 新着レビュー2つ
		PageRequest pageRequest = PageRequest.of(0, 2);
		List<Review> twoReviews =  reviewRepository.findByRestaurantIdAndHiddenFalseOrderByCreatedAtDesc(restaurantId, pageRequest);
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("category", category);
		model.addAttribute("user", user);
		model.addAttribute("favorite", favorite);
		model.addAttribute("reservationForm", reservationForm);
		model.addAttribute("twoReviews", twoReviews);
		
		return "restaurants/show";
	}
}
