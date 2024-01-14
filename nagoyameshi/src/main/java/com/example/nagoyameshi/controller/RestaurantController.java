package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
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
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.RestaurantService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;
	private final RestaurantService restaurantService;
	private final FavoriteRepository favoriteRepository;
	
	RestaurantController(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, RestaurantService restaurantService, FavoriteRepository favoriteRepository) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
		this.restaurantService = restaurantService;
		this.favoriteRepository = favoriteRepository;
	}
	
	@GetMapping
	public String index(Model model, 
						@RequestParam(name = "keyword", required = false) String keyword, 
						@RequestParam(name = "minBudget", required = false) Integer minBudget, 
						@RequestParam(name = "maxBudget", required = false) Integer maxBudget, 
						@RequestParam(name = "category", required = false) Category category, 
						@PageableDefault(page = 0, size = 10) Pageable pageable) {
		Page<Restaurant> restaurantPage = restaurantService.findRestaurants(keyword, minBudget, maxBudget, category, pageable);
		List<Category> categories = categoryRepository.findAll();
		
		model.addAttribute("restaurantPage", restaurantPage);
		model.addAttribute("categories", categories);
		model.addAttribute("keyword", keyword);
		model.addAttribute("minBudget", minBudget);
		model.addAttribute("maxBudget", maxBudget);
		model.addAttribute("category", category);
		
		return "restaurants/index";
	}
	
	@GetMapping("/{restaurant_id}")
	public String show(@PathVariable(name = "restaurant_id") Integer restaurantId,
					   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
					   Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		Category category = categoryRepository.getReferenceById(restaurant.getCategory().getId());
		User user = userDetailsImpl.getUser();
		Favorite favorite = favoriteRepository.findByUserAndRestaurant(user, restaurant);
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("category", category);
		model.addAttribute("user", user);
		model.addAttribute("favorite", favorite);
		
		return "restaurants/show";
	}
}
