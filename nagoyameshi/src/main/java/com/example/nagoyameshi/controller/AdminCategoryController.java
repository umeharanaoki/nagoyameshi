package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	// カテゴリ管理ページを返す
	@GetMapping
	public String index(Model model) {
		List<Category> categories = categoryRepository.findAll();
		
		model.addAttribute("categories", categories);
		model.addAttribute("categoryRegisterForm", new CategoryRegisterForm());
		model.addAttribute("categoryEditForm", new CategoryEditForm());
		
		return "admin/categories/index";
	}
	
	// カテゴリを追加
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated CategoryRegisterForm categoryRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "admin/categories/index";
		}
		
		categoryService.create(categoryRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "カテゴリを追加しました。");
		
		return "redirect:/admin/categories";
	}
	
	// カテゴリを更新
	@PostMapping("/update")
	public String update(@ModelAttribute @Validated CategoryEditForm categoryEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "admin/categories/index";
		}
		
		categoryService.update(categoryEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "カテゴリを編集しました。");
		
		return "redirect:/admin/categories";
	}
	
	// カテゴリを削除
	@PostMapping("/{category_id}/delete")
	public String delete(@PathVariable(name = "category_id") Integer categoryId, RedirectAttributes redirectAttributes) {
		// 未分類のcategory_idを検索
		Integer unclassifiedCategoryId = categoryRepository.findByName("未分類").getId();	
		
		categoryService.deleteCategoryAndUpdateRestaurants(categoryId, unclassifiedCategoryId);
		
		redirectAttributes.addFlashAttribute("successMessage", "カテゴリを削除しました。");
		
		return "redirect:/admin/categories";
	}
	
}
