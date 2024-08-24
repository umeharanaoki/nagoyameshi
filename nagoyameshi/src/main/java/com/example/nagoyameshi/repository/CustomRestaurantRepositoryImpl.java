package com.example.nagoyameshi.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class CustomRestaurantRepositoryImpl implements CustomRestaurantRepository{
	@PersistenceContext  // JPAのEntityManagerをDIする
    private EntityManager entityManager;  // JPAでデータベース操作を実行するためのインターフェース

	@Override
	public Page<Restaurant> findRestaurantsByCriteriaOrderByAverageEvaluation(String keyword, Integer minBudget, Integer maxBudget, Category category, Pageable pageable) {
	    // 基本的なクエリ構築
	    StringBuilder queryBuilder = new StringBuilder("SELECT r, COALESCE(AVG(rev.evaluation), 0) as avgEval FROM Restaurant r LEFT JOIN r.reviews rev");
	    StringBuilder whereClause = new StringBuilder(" WHERE 1=1");
	    
	    // 検索条件の追加
	    if (keyword != null) {
	        whereClause.append(" AND r.name LIKE :keyword");
	    }
	    if (minBudget != null) {
	        whereClause.append(" AND r.minBudget >= :minBudget");
	    }
	    if (maxBudget != null) {
	        whereClause.append(" AND r.maxBudget <= :maxBudget");
	    }
	    if (category != null) {
	        whereClause.append(" AND r.category = :category");
	    }
	    
	    queryBuilder.append(whereClause);
	    queryBuilder.append(" GROUP BY r.id ORDER BY avgEval DESC, r.id");
	    
	    // クエリの実行
	    TypedQuery<Object[]> query = entityManager.createQuery(queryBuilder.toString(), Object[].class);
	    
	    // パラメータの設定
	    if (keyword != null) {
	        query.setParameter("keyword", "%" + keyword + "%");
	    }
	    if (minBudget != null) {
	        query.setParameter("minBudget", minBudget);
	    }
	    if (maxBudget != null) {
	        query.setParameter("maxBudget", maxBudget);
	    }
	    if (category != null) {
	        query.setParameter("category", category);
	    }
	    
	    // 結果の取得
	    int totalRows = query.getResultList().size();
	    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
	    query.setMaxResults(pageable.getPageSize());
	    
	    List<Object[]> results = query.getResultList();
	    List<Restaurant> restaurants = results.stream()
	        .map(result -> (Restaurant) result[0])
	        .collect(Collectors.toList());
	    
	    return new PageImpl<>(restaurants, pageable, totalRows);
	}
}