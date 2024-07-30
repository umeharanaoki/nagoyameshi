package com.example.nagoyameshi.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupForm {
	@NotBlank(message = "氏名を入力してください。")
	private String name;
	    
	@NotBlank(message = "フリガナを入力してください。")
	private String furigana;    
	    
	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;

	@NotBlank(message = "住所を入力してください。")
	private String address;
	    
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
	
	private LocalDate birthday;
	
	@NotBlank(message = "メールアドレスを入力してください。")
	private String email;
	    
	@NotBlank(message = "パスワードを入力してください。")
	private String password;  
	
	@NotBlank(message = "パスワード（確認用）を入力してください。")
	private String passwordConfirmation;
}
