package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	// ユーザーがその店をお気に入り登録しているかを判断するための情報
	public Favorite findByUserAndRestaurant(User user, Restaurant restaurant);
	// お気に入り登録者数を計算するためのメソッド
	public Integer countFavoriteUsersByRestaurantId(Integer restaurantId);
	// お気に入り一覧ページ
	public Page<Favorite> findByUserAndStatus(User user, Integer status, Pageable pageable);
}
