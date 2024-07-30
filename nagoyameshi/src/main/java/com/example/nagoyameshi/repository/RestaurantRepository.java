package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.nagoyameshi.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>, JpaSpecificationExecutor<Restaurant> {
	// トップページの新着順
	public List<Restaurant> findTop6ByOrderByCreatedAtDesc();
	
	// レストランをレビューの平均点の高い順にリスト化
	@Query("SELECT r FROM Restaurant r LEFT JOIN r.reviews rev GROUP BY r.id ORDER BY AVG(rev.evaluation) DESC")
	List<Restaurant> findTop6ByAverageEvaluation(Pageable pageable);
	
	// レストランをレビューの平均点の高い順にリスト化
//	@Query("SELECT r FROM Restaurant r LEFT JOIN r.reviews rev GROUP BY r.id ORDER BY AVG(rev.evaluation) DESC")
//	Page<Restaurant> findAllByOrderByAverageEvaluationDescPage(Pageable pageable);
	
	// カテゴリIDで検索してカテゴリIDを未分類の者に変更する（カテゴリ削除処理用）
	@Modifying
	@Query("UPDATE Restaurant r SET r.category.id = :unclassifiedCategoryId WHERE r.category.id = :categoryId")
	void updateCategoryToUnclassified(@Param("categoryId") Integer categoryId, @Param("unclassifiedCategoryId") Integer unclassifiedCategoryId);
}
