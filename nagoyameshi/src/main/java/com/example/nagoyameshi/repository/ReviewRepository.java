package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	// 店舗で検索し、新着順に並べる（民宿詳細用）
	List<Review> findByRestaurantOrderByCreatedAtDesc(Restaurant restaurant, PageRequest of);
		
	// 店舗で検索し、新着順に並べてページネーションを作る（レビュー一覧用）
	Page<Review> findByRestaurant(Restaurant restaurant, Pageable pageable);
	
	// レストランのレビューの平均点を求める
	@Query("SELECT AVG(r.evaluation) FROM Review r WHERE r.restaurant = :restaurant")
    Double findAverageEvaluationByRestaurant(@Param("restaurant") Restaurant restaurant);
}
