package com.example.nagoyameshi.form;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationForm {
	
	// restaurantとuserはフォーム以外の箇所から情報をひっぱるのでフォームにはいらない
//	private Integer restaurantId;
//	private Integer userId;
	
	// LocalDateやLocalTimeは非言語型なのでNotNull
	@NotNull(message = "来店日を選択してください。")
	private LocalDate visitingDate;
	
	@NotNull(message = "来店時間を選択してください。")
	private LocalTime visitingTime;
	
	@NotNull(message = "来店人数を入力してください。")
	@Min(value = 1, message = "宿泊人数は1人以上設定してください。")
	private Integer numberOfPeople;
}
