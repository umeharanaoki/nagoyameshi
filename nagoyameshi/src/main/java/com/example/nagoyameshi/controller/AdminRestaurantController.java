package com.example.nagoyameshi.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.service.RestaurantService;

@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;
	private final RestaurantService restaurantService;
	private final FavoriteRepository favoriteRepository;
	private final ReviewRepository reviewRepository;
	
	AdminRestaurantController(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, RestaurantService restaurantService, FavoriteRepository favoriteRepository, ReviewRepository reviewRepository) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
		this.restaurantService = restaurantService;
		this.favoriteRepository = favoriteRepository;
		this.reviewRepository = reviewRepository;
	}
	
	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {
		Page<Restaurant> restaurantPage = restaurantService.findAdminRestaurants(keyword, pageable);
		
		model.addAttribute("restaurantPage", restaurantPage);
		model.addAttribute("keyword", keyword);
		
		return "admin/restaurants/index";
	}
	
	// 管理者用店舗詳細ページ
	@GetMapping("/{restaurant_id}")
	public String show(Model model, @PathVariable(name = "restaurant_id") Integer restaurantId) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		List<Category> category = categoryRepository.findAll();
		Integer favoriteUsers = favoriteRepository.countFavoriteUsersByRestaurantId(restaurantId);
		// 最新のレビューを2件表示する
		List<Review> reviews = reviewRepository.findByRestaurantIdAndHiddenFalseOrderByCreatedAtDesc(restaurantId, PageRequest.of(0, 2));
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("category", category);
		model.addAttribute("favoriteUsers", favoriteUsers);
		model.addAttribute("reviews", reviews);
		
		return "admin/restaurants/show";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("restaurantRegisterForm", new RestaurantRegisterForm());
		addCategoryListToModel(model);
		return "admin/restaurants/register";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated RestaurantRegisterForm restaurantRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			// バリデーションエラー時にもカテゴリリストを渡す
			addCategoryListToModel(model);
            return "admin/restaurants/register";
        }
        
        restaurantService.create(restaurantRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "店舗情報を登録しました。");    
        
        return "redirect:/admin/restaurants";
	}
	
	@GetMapping("/{restaurant_id}/edit")
	public String edit(@PathVariable(name = "restaurant_id") Integer restaurantId ,Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		String imageName = restaurant.getImageName();
		List<Category> category = categoryRepository.findAll();
		RestaurantEditForm restaurantEditForm = new RestaurantEditForm(restaurant.getId(), restaurant.getName(), null, restaurant.getPostalCode(), restaurant.getAddress(), restaurant.getPhoneNumber(), restaurant.getOpeningTime(), restaurant.getClosingTime(), restaurant.getMinBudget(), restaurant.getMaxBudget(), restaurant.getExplanation(), restaurant.getCategory(), restaurant.getSeatingCapacity(), restaurant.getKeyword());
		
		model.addAttribute("categories", category);
		model.addAttribute("imageName", imageName);
		model.addAttribute("restaurantEditForm", restaurantEditForm);
		
		return "admin/restaurants/edit";
	}
	
	@PostMapping("/{restaurant_id}/update")
	public String update(@ModelAttribute @Validated RestaurantEditForm restaurantEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		// エラーがないか確認
		if(bindingResult.hasErrors()) {
			// バリデーションエラー時にもカテゴリリストを渡す
			addCategoryListToModel(model);
			return "admin/restaurants/edit";
		}
		
		// エラーがない場合、UserServiceのupdateメソッドにFormを渡して更新処理をする
		restaurantService.update(restaurantEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗情報を更新しました。");
		
		return "redirect:/admin/restaurants";
	}
	
	@PostMapping("/{restaurant_id}/delete")
	public String delete(@PathVariable(name = "restaurant_id") Integer restaurantId, RedirectAttributes redirectAttributes) {
		restaurantRepository.deleteById(restaurantId);
		
		redirectAttributes.addFlashAttribute("successMessage", "店舗情報を削除しました。");
		
		return "redirect:/admin/restaurants";
	}
	
	// カテゴリリスト作成用の共通メソッド
	private void addCategoryListToModel(Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
	}
}
