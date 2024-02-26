package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.nagoyameshi.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>, JpaSpecificationExecutor<Restaurant> {
	// トップページの新着順
	public List<Restaurant> findTop10ByOrderByCreatedAtDesc();
	
	// トップページの評価順
	
	// トップページのカテゴリ
}
