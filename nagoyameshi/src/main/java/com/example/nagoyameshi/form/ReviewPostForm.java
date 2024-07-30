package com.example.nagoyameshi.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewPostForm {
	
	@NotBlank(message = "タイトルを入力してください。")
	private String title;
	
	private MultipartFile imageFile;
	
	@NotNull(message = "評価を入力してください。")
	private Double evaluation;
	
//	@NotBlank(message = "コメントを入力してください。")
	private String comment;
}
