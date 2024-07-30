package com.example.nagoyameshi.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;

public class RestaurantSpecifications {
	// 店名で部分検索
	public static Specification<Restaurant> nameContains(String name) {
		return StringUtils.hasLength(name) ? (root, query, cb) -> {
			return cb.like(root.get("name"), "%" + name + "%");
		} : null;
    }
	//郵便番号で部分検索
	public static Specification<Restaurant> postalCodeContains(String postalCode) {
		return StringUtils.hasLength(postalCode) ? (root, query, cb) -> {
			return cb.like(root.get("postalCode"), "%" + postalCode + "%");
		} : null;
    }
	// 住所で部分検索
	public static Specification<Restaurant> addressContains(String address) {
		return StringUtils.hasLength(address) ? (root, query, cb) -> {
			return cb.like(root.get("address"), "%" + address + "%");
		} : null;
    }
	// 電話番号で部分検索
	public static Specification<Restaurant> phoneNumberContains(String phoneNumber) {
		return StringUtils.hasLength(phoneNumber) ? (root, query, cb) -> {
			return cb.like(root.get("phoneNumber"), "%" + phoneNumber + "%");
		} : null;
    }
	
	// 最低予算が下限より上か
	public static Specification<Restaurant> minBudgetGreaterThanEqual(Integer minBudget) {
		return minBudget == null ? null : (root, query, cb) -> {
			return cb.greaterThanOrEqualTo(root.get("minBudget"), minBudget);
		};
	}
	
	// 最高予算が上限より下か
	public static Specification<Restaurant> maxBudgetLessThanEqual(Integer maxBudget) {
		return maxBudget == null ? null : (root, query, cb) -> {
			return cb.lessThanOrEqualTo(root.get("minBudget"), maxBudget);
		};
	}
	
	// カテゴリ検索
	public static Specification<Restaurant> categoryEqual(Category category) {
		return ObjectUtils.isEmpty(category) ? null : (root, query, cb) -> {
			return cb.equal(root.get("category").get("id"), category.getId());
		};
	}
	// キーワードで部分検索
	public static Specification<Restaurant> keywordContains(String keyword) {
		return StringUtils.hasLength(keyword) ? (root, query, cb) -> {
			return cb.like(root.get("keyword"), "%" + keyword + "%");
		} : null;
    }
}
