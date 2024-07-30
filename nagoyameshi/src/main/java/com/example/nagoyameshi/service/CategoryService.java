package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.form.CategoryEditForm;
import com.example.nagoyameshi.form.CategoryRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final RestaurantRepository restaurantRepository;
	
	public CategoryService(CategoryRepository categoryRepository, RestaurantRepository restaurantRepository) {
		this.categoryRepository = categoryRepository;
		this.restaurantRepository = restaurantRepository;
	}
	
	// カテゴリを登録
	@Transactional
	public void create(CategoryRegisterForm categoryRegisterForm) {
		Category category = new Category();
		category.setName(categoryRegisterForm.getName());
		
		categoryRepository.save(category);
	}
	
	// カテゴリ名を更新
	@Transactional
	public void update(CategoryEditForm categoryEditForm) {
		Category category = categoryRepository.getReferenceById(categoryEditForm.getId());
		category.setName(categoryEditForm.getName());
		
		categoryRepository.save(category);
	}
	
	// 関連のあるレストランのカテゴリIDを変更し、カテゴリを削除する
	@Transactional
    public void deleteCategoryAndUpdateRestaurants(Integer categoryId, Integer unclassifiedCategoryId) {
        // 1. レストランのカテゴリIDを「未分類」に更新
        restaurantRepository.updateCategoryToUnclassified(categoryId, unclassifiedCategoryId);

        // 2. カテゴリを削除
        categoryRepository.deleteById(categoryId);
    }
}
