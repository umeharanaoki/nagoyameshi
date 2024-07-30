package com.example.nagoyameshi.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.service.SubscriptionService;
import com.example.nagoyameshi.service.UserService;

@Controller
public class AdminSalesController {
	private final SubscriptionService subscriptionService;
	private final UserService userService;
	
	public AdminSalesController(SubscriptionService subscriptionService, UserService userService) {
		this.subscriptionService = subscriptionService;
		this.userService = userService;
	}
	
	@GetMapping("/admin/sales")
	public String sales(Model model) {
		// 期間別売上
		int currentYear = LocalDate.now().getYear();
		List<Integer> lastFiveYears = IntStream.rangeClosed(currentYear - 4, currentYear)
											   .boxed()
											   .collect(Collectors.toList());
		Map<Integer, Map<String, Integer>> subscriptionDataMap = subscriptionService.getSubscriptionCountsForYears(lastFiveYears);
		
		// 年代別ユーザー数
		Map<String, Integer> subscriptionUserGenerationMap = userService.calculateAgeGroups();

		model.addAttribute("subscriptionDataMap", subscriptionDataMap);
		model.addAttribute("subscriptionUserGenerationMap", subscriptionUserGenerationMap);
		
		return "admin/sales/index";
	}
}
