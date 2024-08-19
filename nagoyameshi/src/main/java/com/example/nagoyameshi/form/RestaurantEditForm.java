package com.example.nagoyameshi.form;

import java.time.LocalTime;

import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantEditForm {
	@NotNull
	private Integer id;
	
	@NotBlank(message = "店舗名を入力してください。")
	private String name;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;
	    
	@NotBlank(message = "住所を入力してください。")
	private String address;
	    
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
	
	@NotNull(message = "営業開始時間を選択してください。")
	private LocalTime openingTime;
	    
	@NotNull(message = "営業終了時間を選択してください。")
	private LocalTime closingTime;
	
	@NotNull(message = "予算の下限を選択してください。")
	private Integer minBudget;
	
	@NotNull(message = "予算の上限を選択してください。")
	private Integer maxBudget;
	    
	@NotBlank(message = "店の説明を入力してください。")
	private String explanation;
	
	@NotNull(message = "カテゴリを選択してください。")
	private Category category;
	    
	@NotNull(message = "席数を入力してください。")
	private Integer seatingCapacity;
	
	private String keyword;
}
