package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;

// ページ遷移したくないときは@RestControllerを指定
@RestController
public class AjaxFavoriteController {
	private final FavoriteService favoriteService;
	private final FavoriteRepository favoriteRepository;
	private final RestaurantRepository restaurantRepository;
	
	AjaxFavoriteController(FavoriteService favoriteService, FavoriteRepository favoriteRepository, RestaurantRepository restaurantRepository) {
		this.favoriteService = favoriteService;
		this.favoriteRepository = favoriteRepository;
		this.restaurantRepository = restaurantRepository;
	}
	
	// Ajaxからのリクエストを受け取る
	@PostMapping("/restaurants/{id}/favorites/toggle")
	public Integer toggleFavorite(@PathVariable(name = "id") Integer restaurantId, 
								  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl)
	{
		User user = userDetailsImpl.getUser();
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		// Serviceのメソッドを使用してデータベースの操作
		favoriteService.toggleFavorite(user, restaurant);
		
		Favorite favorite = favoriteRepository.findByUserAndRestaurant(user, restaurant);
		// ビューではなくデータを返す
		return favorite.getStatus();
	}
}