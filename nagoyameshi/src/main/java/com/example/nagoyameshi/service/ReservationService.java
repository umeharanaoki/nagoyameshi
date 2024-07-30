package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final RestaurantRepository restaurantRepository;
	
	public ReservationService(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository) {
		this.reservationRepository = reservationRepository;
		this.restaurantRepository = restaurantRepository;
	}
	
	// 予約フォームの情報をもとにデータベースに登録
	// フォームにはrestaurantとuserの情報がないので別途渡す
	@Transactional
	public void create(Restaurant restaurant, User user, ReservationForm reservationForm) {
		// インスタンスを作成
		Reservation reservation = new Reservation();
		// インスタンスに情報を入れる
		reservation.setRestaurant(restaurant);
		reservation.setUser(user);
		reservation.setVisitingDate(reservationForm.getVisitingDate());
		reservation.setVisitingTime(reservationForm.getVisitingTime());
		reservation.setNumberOfPeople(reservationForm.getNumberOfPeople());
		
		reservationRepository.save(reservation);
	}
}
