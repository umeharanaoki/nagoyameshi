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

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
	private final UserRepository userRepository;
	
	public AdminUserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {
		Page<User> userPage;
		
		// keyword（検索ワード）があるとき
		if (keyword != null && !keyword.isEmpty()) {
				// keywordをもとに作成した独自メソッドで部分検索を行う
		    userPage = userRepository.findByNameLike("%" + keyword + "%", pageable);                
		} else {
				// それ以外（keywordがない）ときはfindAllメソッドですべて表示する
		    userPage = userRepository.findAll(pageable);
		}
		
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
