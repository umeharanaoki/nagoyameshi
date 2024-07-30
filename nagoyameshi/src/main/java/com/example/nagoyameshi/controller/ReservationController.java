package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReservationService;

@Controller
public class ReservationController {
	private final ReservationRepository reservationRepository;
	private final ReservationService reservationService;
	private final RestaurantRepository restaurantRepository;
	private final UserRepository userRepository;
	
	ReservationController(ReservationRepository reservationRepository, ReservationService reservationService, RestaurantRepository restaurantRepository, UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.reservationService = reservationService;
		this.restaurantRepository = restaurantRepository;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/reservations")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
		User user = userDetailsImpl.getUser();
		Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
		
		model.addAttribute("reservationPage", reservationPage);
		
		return "reservations/index";
	}
	
	// 予約内容確認ページ
	@GetMapping("/restaurants/{restaurant_id}/reservations/confirm")
	public String confirm(@PathVariable(name = "restaurant_id") Integer restaurantId,
						  @ModelAttribute ReservationForm reservationForm,
						  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						  Model model) {
		// Restaurantのidをurlから取得
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		// User情報をログイン情報から取得
		User user = userDetailsImpl.getUser();
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("user", user);
		model.addAttribute("reservationForm", reservationForm);
		
		return "reservations/confirm";
	}
	
	// 確認した予約内容をデータベースに登録
	@PostMapping("/restaurants/{restaurant_id}/reservations/create")
	public String create(@PathVariable(name = "restaurant_id") Integer restaurantId,
						 @ModelAttribute ReservationForm reservationForm,
						 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						 Model model) {
		// Restaurantのidをurlから取得
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
		// User情報をログイン情報から取得
		User user = userDetailsImpl.getUser();
		
		reservationService.create(restaurant, user, reservationForm);
		
		return "redirect:/reservations?reserved";
	}	
}
