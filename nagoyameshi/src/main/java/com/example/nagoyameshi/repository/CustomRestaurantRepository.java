package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;

public interface CustomRestaurantRepository {
	Page<Restaurant> findRestaurantsByCriteriaOrderByAverageEvaluation(String keyword, Integer minBudget, Integer maxBudget, Category category, Pageable pageable);
}
