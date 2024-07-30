package com.example.nagoyameshi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReviewService;

@Controller
public class FavoriteController {
	public final FavoriteRepository favoriteRepository;
	public final RestaurantRepository restaurantRepository;
	public final ReviewService reviewService;
	
	FavoriteController(FavoriteRepository favoriteRepository, RestaurantRepository restaurantRepository, ReviewService reviewService) {
		this.favoriteRepository = favoriteRepository;
		this.restaurantRepository = restaurantRepository;
		this.reviewService	= reviewService;
	}
	
	@GetMapping("/favorites")
	public String index(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						Model model) {
		// 誰のお気に入りを検索するか
		User user = userDetailsImpl.getUser();
		// お気に入りに関連するrestaurantの情報を引き出すためにリスト化
		List<Restaurant> restaurants = restaurantRepository.findAll();
		// statusが1(お気に入り登録されている）のデータを抽出してPage化
		Page<Favorite> favoritePage = favoriteRepository.findByUserAndStatus(user, 1, pageable);
		// 各店のレビューの平均点をMapで管理
		Map<Integer, String> averageEvaluationMap = reviewService.calculateAverageEvaluations(restaurants);
				
		model.addAttribute("restauratns", restaurants);
		model.addAttribute("favoritePage", favoritePage);
		// レビュー表示用
		model.addAttribute("averageEvaluationMap", averageEvaluationMap);
		return "favorites/index";
	}
}
 