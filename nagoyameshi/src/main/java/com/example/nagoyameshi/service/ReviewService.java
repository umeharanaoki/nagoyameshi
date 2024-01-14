package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewPostForm;
import com.example.nagoyameshi.repository.ReviewRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	
	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
    public void create(ReviewPostForm reviewPostForm, Restaurant restaurant, User user) {
        Review review = new Review();
        MultipartFile imageFile = reviewPostForm.getImageFile();
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            review.setImageName(hashedImageName);
        }
        
        review.setRestaurant(restaurant);
        review.setUser(user);
        review.setTitle(reviewPostForm.getTitle());
        review.setEvaluation(reviewPostForm.getEvaluation());
        review.setComment(reviewPostForm.getComment());
        
        reviewRepository.save(review);
    }
	
	@Transactional
	public void update(ReviewEditForm reviewEditForm, Restaurant restaurant, User user) {
		Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
		MultipartFile imageFile = reviewEditForm.getImageFile();
		
		if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            review.setImageName(hashedImageName);
        }
		
		review.setRestaurant(restaurant);
        review.setUser(user);
        review.setTitle(reviewEditForm.getTitle());
        review.setEvaluation(reviewEditForm.getEvaluation());
        review.setComment(reviewEditForm.getComment());
        
        reviewRepository.save(review);
		
	}
	
	  // UUIDを使って生成したファイル名を返す
    public String generateNewFileName(String fileName) {
        String[] fileNames = fileName.split("\\.");                
        for (int i = 0; i < fileNames.length - 1; i++) {
            fileNames[i] = UUID.randomUUID().toString();            
        }
        String hashedFileName = String.join(".", fileNames);
        return hashedFileName;
    }     
    
    // 画像ファイルを指定したファイルにコピーする
    public void copyImageFile(MultipartFile imageFile, Path filePath) {           
        try {
            Files.copy(imageFile.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }          
    }
}
