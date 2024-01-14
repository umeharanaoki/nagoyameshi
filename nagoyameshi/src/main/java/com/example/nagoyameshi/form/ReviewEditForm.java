package com.example.nagoyameshi.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
	@NotNull
	private Integer id;
	
	@NotBlank(message = "タイトルを入力してください。")
	private String title;
	
	private MultipartFile imageFile;
	
	@NotNull(message = "評価を選択してください。")
	private Double evaluation;
	
	@NotBlank(message = "コメントを入力してください。")
	private String comment;
}
