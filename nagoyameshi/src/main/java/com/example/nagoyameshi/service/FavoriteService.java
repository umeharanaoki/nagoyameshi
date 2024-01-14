package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;
		
	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}
	
	@Transactional
	public void toggleFavorite(User user, Restaurant restaurant) {
		Favorite favorite = favoriteRepository.findByUserAndRestaurant(user, restaurant);
		
		if(favorite != null) {
			// すでに値がある場合、statusを切り替える
			favorite.setStatus(1 - favorite.getStatus());
		} else {
			// まだ値がない（そのユーザーがその店舗のお気に入りボタンを初めて押す）とき
			favorite = new Favorite();
			favorite.setUser(user);
			favorite.setRestaurant(restaurant);
			favorite.setStatus(1);
		}
		favoriteRepository.save(favorite);
	}
}