package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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
	
	// レビューをデータベースに登録
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
        
        System.out.println("test");       
        reviewRepository.save(review);
    }
	
	// データベースの更新
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
	
	// レストラン評価の平均点算出
	// レストランのIDとそのレストランの平均点をマッピング
	public Map<Integer, String> calculateAverageEvaluations(Iterable<Restaurant> restaurants) {  // List型とPage型のどちらも引数に取れるようにする
        Map<Integer, String> averageEvaluationMap = new HashMap<>();
        
        for (Restaurant restaurant : restaurants) {
        	// 平均点を求める
            Double averageEvaluation = reviewRepository.findAverageEvaluationByRestaurantAndHiddenFalse(restaurant.getId());
            // レビューがない店舗でエラーが出ないようnullチェックする
            if(averageEvaluation != null) {
            	// 小数点以下第二位までに変換
	            averageEvaluation = averageEvaluation * 100;
	            long averageEvaluationRound = Math.round(averageEvaluation);
	            averageEvaluation = (double)averageEvaluationRound/100;
            } else {
            	averageEvaluation = 0.00;
            }
            
            // 小数点以下第二位に数値を揃えるためにフォーマットをStringに(4.20等）
            String formattedAverage = String.format("%.2f", averageEvaluation);
            averageEvaluationMap.put(restaurant.getId(), formattedAverage);
        }
        
        return averageEvaluationMap;
    }
	
	// レビューを非表示にする
	@Transactional
	public void hidden(Integer reviewId) {
		Review review = reviewRepository.getReferenceById(reviewId);
		// 1をsetすることで非表示にするreviewを区別する
		review.setHidden((byte)1);
		
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
