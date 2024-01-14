package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
	private final UserRepository userRepository;
	private final UserService userService;
	
	public AdminUserController(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {
		Page<User> userPage = userService.findAdminUsers(keyword, pageable);
		
		model.addAttribute("userPage", userPage);
		model.addAttribute("keyword", keyword);
		
		return "admin/users/index";
	}
	
	@GetMapping("/{user_id}")
	public String show(Model model, @PathVariable(name = "user_id") Integer userId) {
		User user = userRepository.getReferenceById(userId);
		model.addAttribute("user", user);
		return "admin/users/show";
	}
}
