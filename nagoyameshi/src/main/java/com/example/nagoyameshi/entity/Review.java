package com.example.nagoyameshi.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "reviews")
@Data
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@JoinColumn(name = "restaurant_id")
	@ManyToOne
	private Restaurant restaurant;
	
	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "evaluation")
	private Double evaluation;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "hidden")
	private Byte hidden;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
	
	// hiddenフィールドの値をbooleanとして返すメソッド
    public boolean isHidden() {
        return hidden != null && hidden == 1;
    }
}
