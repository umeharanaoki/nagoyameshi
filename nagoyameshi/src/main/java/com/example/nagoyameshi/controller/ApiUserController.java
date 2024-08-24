package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;

@Controller
public class ApiUserController {
	private final UserService userService;
	
	public ApiUserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/api/user-status")
	public UserStatusResponse getUserStatus(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		boolean isPremium = userService.isUserPremium(user);
		
		return new UserStatusResponse(isPremium);
	}
	
	public static class UserStatusResponse {
		private boolean isPremium;
		
		public UserStatusResponse(boolean isPremium) {
			this.isPremium = isPremium;
		}
		public boolean isPremium() {
			return isPremium;
		}
	}
}
