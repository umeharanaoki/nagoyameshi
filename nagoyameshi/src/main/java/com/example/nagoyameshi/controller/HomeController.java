package com.example.nagoyameshi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.service.RestaurantService;
import com.example.nagoyameshi.service.ReviewService;

@Controller
public class HomeController {
	private final RestaurantRepository restaurantRepository;
	private final RestaurantService restaurantService;
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	
	public HomeController(RestaurantRepository restaurantRepository, RestaurantService restaurantService, CategoryRepository categoryRepository, ReviewRepository reviewRepository, ReviewService reviewService) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantService = restaurantService;
		this.categoryRepository = categoryRepository;
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		// 評価順
		List<Restaurant> restaurantScoreTop6 = restaurantService.getTop6RestaurantsByAverageEvaluation();
		
		// カテゴリから探す
		List<Category> categories = categoryRepository.findAll();
		
		// 新着順
		List<Restaurant> restaurantCreatedTop6 = restaurantRepository.findTop6ByOrderByCreatedAtDesc();
		
		// カードに表示するためのレビュー点数をmapで管理
		Map<Integer, String> topPageRestaurantScoreMap = reviewService.calculateAverageEvaluations(restaurantScoreTop6);
		// serviceクラスで作成したメソッドではhashマップが新規作成されるので別途追加（他クラスでの使用状況によってはメソッドを変える？）
		for (Restaurant restaurant : restaurantCreatedTop6) {
        	// 平均点を求める
            Double averageEvaluation = reviewRepository.findAverageEvaluationByRestaurantAndHiddenFalse(restaurant.getId());
            // レビューがない店舗でエラーが出ないようnullチェックする
            if(averageEvaluation != null) {
            	// 小数点以下第二位までに変換
	            averageEvaluation = averageEvaluation * 100;
	            long averageEvaluationRound = Math.round(averageEvaluation);
	            averageEvaluation = (double)averageEvaluationRound/100;
            } else {
            	averageEvaluation = 0.00;
            }
            // 小数点以下第二位に数値を揃えるためにフォーマットをStringに(4.20等）
            String formattedAverage = String.format("%.2f", averageEvaluation);
            topPageRestaurantScoreMap.put(restaurant.getId(), formattedAverage);
        }
		
		model.addAttribute("restaurantScoreTop6", restaurantScoreTop6);
		model.addAttribute("categories", categories);
		model.addAttribute("restaurantCreatedTop6", restaurantCreatedTop6);
		model.addAttribute("topPageRestaurantScoreMap", topPageRestaurantScoreMap);
		
		return "index";
	}
}
