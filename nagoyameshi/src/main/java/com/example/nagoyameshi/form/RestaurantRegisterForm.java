package com.example.nagoyameshi.form;

import java.time.LocalTime;

import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantRegisterForm {
	@NotBlank(message = "店舗名を入力してください。")
	private String name;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;
	    
	@NotBlank(message = "住所を入力してください。")
	private String address;
	    
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
	
	// LocalTime型に合わせたValidationが必要
	// @NotBlank(message = "営業開始時間を入力してください。")
	private LocalTime openingTime;
	    
	// @NotBlank(message = "営業終了時間を入力してください。")
	private LocalTime closingTime;
	
	@NotNull(message = "予算の下限を入力してください。")
	private Integer minBudget;
	
	@NotNull(message = "予算の上限を入力してください。")
	private Integer maxBudget;
	    
	@NotBlank(message = "店の説明を入力してください。")
	private String explanation;
	
	@NotNull(message = "カテゴリを選択してください。")
	private Category category;
	    
	@NotNull(message = "席数を入力してください。")
	private Integer seatingCapacity;
	
	private String keyword;
}
