package com.example.nagoyameshi.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.nagoyameshi.entity.User;

public class UserSpecifications {
	// 名前で部分検索
	public static Specification<User> nameContains(String name) {
		return StringUtils.hasLength(name) ? (root, query, cb) -> {
			return cb.like(root.get("name"), "%" + name + "%");
		} : null;
	}
	//フリガナで部分検索
	public static Specification<User> furiganaContains(String furigana) {
		return StringUtils.hasLength(furigana) ? (root, query, cb) -> {
			return cb.like(root.get("furigana"), "%" + furigana + "%");
		} : null;
	}
	// メールアドレスで部分検索
	public static Specification<User> emailContains(String email) {
		return StringUtils.hasLength(email) ? (root, query, cb) -> {
			return cb.like(root.get("email"), "%" + email + "%");
		} : null;
	}
	// 電話番号で部分検索
	public static Specification<User> phoneNumberContains(String phoneNumber) {
		return StringUtils.hasLength(phoneNumber) ? (root, query, cb) -> {
			return cb.like(root.get("phoneNumber"), "%" + phoneNumber + "%");
		} : null;
	}
	//郵便番号で部分検索
		public static Specification<User> postalCodeContains(String postalCode) {
			return StringUtils.hasLength(postalCode) ? (root, query, cb) -> {
				return cb.like(root.get("postalCode"), "%" + postalCode + "%");
			} : null;
		}
}
