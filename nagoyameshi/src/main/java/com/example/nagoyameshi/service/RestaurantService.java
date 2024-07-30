package com.example.nagoyameshi.service;

import static com.example.nagoyameshi.specification.RestaurantSpecifications.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.CustomRestaurantRepositoryImpl;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;
	private final CustomRestaurantRepositoryImpl customRestaurantRepositoryImpl;
	
	public RestaurantService(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, CustomRestaurantRepositoryImpl customRestaurantRepositoryImpl) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
		this.customRestaurantRepositoryImpl = customRestaurantRepositoryImpl;
	}
	
	// データベースに新規店舗を登録する処理
	@Transactional
    public void create(RestaurantRegisterForm restaurantRegisterForm) {
        Restaurant restaurant = new Restaurant();
        MultipartFile imageFile = restaurantRegisterForm.getImageFile();
        // フォームから送られてきたcategoryオブジェクトからgetNameでnameを取り出して検索をかける
        Category category = categoryRepository.findByName(restaurantRegisterForm.getCategory().getName());
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            restaurant.setImageName(hashedImageName);
        }
        
        restaurant.setName(restaurantRegisterForm.getName());
        restaurant.setPostalCode(restaurantRegisterForm.getPostalCode());
        restaurant.setAddress(restaurantRegisterForm.getAddress());
        restaurant.setPhoneNumber(restaurantRegisterForm.getPhoneNumber());
        restaurant.setOpeningTime(restaurantRegisterForm.getOpeningTime());
        restaurant.setClosingTime(restaurantRegisterForm.getClosingTime());
        restaurant.setMinBudget(restaurantRegisterForm.getMinBudget());
        restaurant.setMaxBudget(restaurantRegisterForm.getMaxBudget());
        restaurant.setExplanation(restaurantRegisterForm.getExplanation());
        restaurant.setSeatingCapacity(restaurantRegisterForm.getSeatingCapacity());
        restaurant.setCategory(category);
        restaurant.setKeyword(restaurantRegisterForm.getKeyword());
                    
        restaurantRepository.save(restaurant);
    }
	
	// データベースに保存されている店舗情報を更新する処理
	public void update(RestaurantEditForm restaurantEditForm) {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantEditForm.getId());
		MultipartFile imageFile = restaurantEditForm.getImageFile();
        // フォームから送られてきたcategoryオブジェクトからgetNameでnameを取り出して検索をかける
        Category category = categoryRepository.findByName(restaurantEditForm.getCategory().getName());
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            restaurant.setImageName(hashedImageName);   
        }
        
        restaurant.setName(restaurantEditForm.getName());
        restaurant.setPostalCode(restaurantEditForm.getPostalCode());
        restaurant.setAddress(restaurantEditForm.getAddress());
        restaurant.setPhoneNumber(restaurantEditForm.getPhoneNumber());
        restaurant.setOpeningTime(restaurantEditForm.getOpeningTime());
        restaurant.setClosingTime(restaurantEditForm.getClosingTime());
        restaurant.setMinBudget(restaurantEditForm.getMinBudget());
        restaurant.setMaxBudget(restaurantEditForm.getMaxBudget());
        restaurant.setExplanation(restaurantEditForm.getExplanation());
        restaurant.setSeatingCapacity(restaurantEditForm.getSeatingCapacity());
        restaurant.setCategory(category);
        restaurant.setKeyword(restaurantEditForm.getKeyword());
                    
        restaurantRepository.save(restaurant);
	}
    
	// 管理者用の店舗一覧ページのための検索機能
	// 検索結果をpage型にして返す
	public Page<Restaurant> findAdminRestaurants(String keyword, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Specification<Restaurant> spec = Specification
				 .where(nameContains(keyword))
				 .or(postalCodeContains(keyword))
				 .or(addressContains(keyword))
				 .or(phoneNumberContains(keyword));
	    Page<Restaurant> adminRestaurantPage = restaurantRepository.findAll(spec, pageable);
	    return adminRestaurantPage;
	}
	
	// 一般用店舗一覧ページのための検索・並び替え機能
	public Page<Restaurant> findRestaurants(String keyword, Integer minBudget, Integer maxBudget, Category category, String order, Pageable pageable) {
		Page<Restaurant> restaurantPage;
		// 検索条件をspecに入れる
		Specification<Restaurant> spec = Specification
				// 店名とキーワードは同じ検索窓なので引数を同じにしている
				.where(nameContains(keyword))
				.or(keywordContains(keyword))
				.and(minBudgetGreaterThanEqual(minBudget))
				.and(maxBudgetLessThanEqual(maxBudget))
				.and(categoryEqual(category));
		
		// 並び替え条件の指定
		Sort sort = Sort.unsorted();
		if("createdAtDesc".equals(order)) {
			// 新着順
			sort = Sort.by("createdAt").descending();
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
			restaurantPage = restaurantRepository.findAll(spec, pageable);
		} else if("evaluationDesc".equals(order)) {
			// 評価順
			restaurantPage = customRestaurantRepositoryImpl.findRestaurantsByCriteriaOrderByAverageEvaluation(keyword, minBudget, maxBudget, category, pageable);
			System.out.println(pageable);
		} else {
			sort = Sort.by("createdAt").descending();
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
			restaurantPage = restaurantRepository.findAll(spec, pageable);
		}
		return restaurantPage;
	}
		
	// トップページに表示する店舗数の制限
	public List<Restaurant> getTop6RestaurantsByAverageEvaluation() {
		PageRequest pageRequest = PageRequest.of(0, 6);
		
		return restaurantRepository.findTop6ByAverageEvaluation(pageRequest);
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
