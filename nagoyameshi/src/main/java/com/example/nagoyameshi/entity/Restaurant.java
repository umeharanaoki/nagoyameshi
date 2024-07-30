package com.example.nagoyameshi.entity;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "restaurants")
@Data
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@JoinColumn(name = "category_id")
	@ManyToOne
	private Category category;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "postal_code")
	private String postalCode;
	    
	@Column(name = "address")
	private String address;
	    
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "opening_time")
	private LocalTime openingTime;
	    
	@Column(name = "closing_time")
	private LocalTime closingTime;
	
	@Column(name = "min_budget")
	private Integer minBudget;
	
	@Column(name = "max_budget")
	private Integer maxBudget;
	    
	@Column(name = "explanation")
	private String explanation;
	    
	@Column(name = "seating_capacity")
	private Integer seatingCapacity;
	
	@Column(name = "keyword")
	private String keyword;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
	
	// レビュー点数でレストランを検索できるようにフィールドを追加
	@OneToMany(mappedBy = "restaurant")
	private List<Review> reviews;
	
	// 評価の平均を取得するメソッド（オプション）
    @Transient
    public Double getAverageEvaluation() {
        return reviews.stream()
                      .filter(review -> !review.isHidden())  // 隠されたレビューを除外
                      .mapToDouble(Review::getEvaluation)
                      .average()
                      .orElse(0.0);
    }
}
