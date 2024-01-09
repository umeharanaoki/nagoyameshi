package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryEditForm;
import com.example.nagoyameshi.form.CategoryRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.service.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	private final CategoryRepository categoryRepository;
	private final CategoryService categoryService;
	
	public AdminCategoryController(CategoryRepository categoryRepository, CategoryService categoryService) {
		this.categoryRepository = categoryRepository;
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Category> category = categoryRepository.findAll();
		CategoryEditForm categoryEditForm = new CategoryEditForm();
		model.addAttribute("categories", category);
		model.addAttribute("categoryRegisterForm", new CategoryRegisterForm());
		model.addAttribute("categoryEditForm", categoryEditForm);
		return "admin/categories/index";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated CategoryRegisterForm categoryRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "admin/categories/index";
		}
		
		categoryService.create(categoryRegisterForm);
		redirectAttributes.addFlashAttribute("createSuccessMessage", "カテゴリを追加しました。");
		
		return "redirect:/admin/categories";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute @Validated CategoryEditForm categoryEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "admin/categories/index";
		}
		
		categoryService.update(categoryEditForm);
		redirectAttributes.addFlashAttribute("updateSuccessMessage", "カテゴリを編集しました。");
		
		return "redirect:/admin/categories";
	}
	
//	@PostMapping("/delete")
//	public String delete()
	
	
}
