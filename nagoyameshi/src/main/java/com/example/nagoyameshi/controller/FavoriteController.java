package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;

@Controller
public class FavoriteController {
	public final FavoriteRepository favoriteRepository;
	public final RestaurantRepository restaurantRepository;
	
	FavoriteController(FavoriteRepository favoriteRepository, RestaurantRepository restaurantRepository) {
		this.favoriteRepository = favoriteRepository;
		this.restaurantRepository = restaurantRepository;
	}
	
	@GetMapping("/favorites")
	public String index(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						Model model) {
		User user = userDetailsImpl.getUser();
		// statusが1の
		Page<Favorite> favoritePage = favoriteRepository.findByUserAndStatus(user, 1, pageable);
				
		model.addAttribute("favoritePage", favoritePage);
		return "favorites/index";
	}
}
