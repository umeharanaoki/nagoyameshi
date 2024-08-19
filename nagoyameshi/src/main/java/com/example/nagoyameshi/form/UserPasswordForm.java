package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserPasswordForm {
//	@NotNull
//	private User user;
	
	@NotBlank(message = "変更前のパスワードを入力してください。")
	private String oldPassword;
	
	@NotBlank(message = "新しいパスワードを入力してください。")
	private String newPassword;  
	
	@NotBlank(message = "新しいパスワード（確認用）を入力してください。")
	private String newPasswordConfirmation;
}
