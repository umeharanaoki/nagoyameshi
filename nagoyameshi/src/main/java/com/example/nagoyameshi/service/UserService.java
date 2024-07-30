package com.example.nagoyameshi.service;

import static com.example.nagoyameshi.specification.UserSpecifications.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;        
        this.passwordEncoder = passwordEncoder;
    }    
    
    // フォーム入力内容をデータベースに保存するためのメソッド
    @Transactional
    public User create(SignupForm signupForm) {
        User user = new User();
        Role role = roleRepository.findByName("ROLE_GENERAL_FREE");
//        String birthday = signupForm.getBirthday().equals("") ? null : signupForm.getBirthday();
        
        user.setName(signupForm.getName());
        user.setFurigana(signupForm.getFurigana());
        user.setPostalCode(signupForm.getPostalCode());
        user.setAddress(signupForm.getAddress());
        user.setPhoneNumber(signupForm.getPhoneNumber());
        //　birthdayはnullになる可能性あり→売上処理のとき注意
        user.setBirthday(signupForm.getBirthday());
        user.setEmail(signupForm.getEmail());
        user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        user.setRole(role);
        user.setEnabled(false);        
        
        return userRepository.save(user);
    }    
    
    @Transactional
    public void update(UserEditForm userEditForm) {
    	User user = userRepository.getReferenceById(userEditForm.getId());
    	
    	user.setName(userEditForm.getName());
    	user.setFurigana(userEditForm.getFurigana());
    	user.setPostalCode(userEditForm.getPostalCode());
        user.setAddress(userEditForm.getAddress());
        user.setPhoneNumber(userEditForm.getPhoneNumber());
        user.setBirthday(userEditForm.getBirthday());      
        user.setEmail(userEditForm.getEmail());      
        
        userRepository.save(user);
    }
    
    // プレミアムプラン加入
    @Transactional
    public void joinPremium(Integer userId) {
    	User user = userRepository.getReferenceById(userId);
    	Role role = roleRepository.findByName("ROLE_GENERAL_PREMIUM");
    	
    	user.setRole(role);
    	
    	userRepository.save(user);
    }
    
    // 管理者用一覧の検索機能
    // or検索なので引数は全てkeyword
    public Page<User> findAdminUsers(String keyword, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Specification<User> spec = Specification
				 .where(nameContains(keyword))
				 .or(furiganaContains(keyword))
				 .or(emailContains(keyword))
				 .or(phoneNumberContains(keyword))
		.or(postalCodeContains(keyword));
	    Page<User> adminUserPage = userRepository.findAll(spec, pageable);
	    return adminUserPage;
	}
    
    // メールアドレスが登録済みかどうかをチェックする
    public boolean isEmailRegistered(String email) {
        User user = userRepository.findByEmail(email);  
        return user != null;
    }  
    
    // パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
    public boolean isSamePassword(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }
    
    // ユーザーを有効にする
    @Transactional
    public void enableUser(User user) {
        user.setEnabled(true); 
        userRepository.save(user);
    }
    
    // メールアドレスが変更されたかどうか確認
    public boolean isEmailChanged(UserEditForm userEditForm) {
    	User currentUser = userRepository.getReferenceById(userEditForm.getId());
    	return !userEditForm.getEmail().equals(currentUser.getEmail());
    }
    
    // 各世代の人数を集計
    public Map<String, Integer> calculateAgeGroups() {
//        List<User> users = userRepository.findAll();
    	// プレミアムユーザーを抽出
    	List<User> premiumUsers = userRepository.findByRoleId(2);
        Map<String, Integer> ageGroups = new HashMap<>();

        // 事前にすべての年代カテゴリーを初期化
        initializeAgeGroups(ageGroups);

        for (User user : premiumUsers) {
            String ageGroup = determineAgeGroup(user.getBirthday());
            ageGroups.put(ageGroup, ageGroups.getOrDefault(ageGroup, 0) + 1);
        }

        return ageGroups;
    }

    private void initializeAgeGroups(Map<String, Integer> ageGroups) {
        ageGroups.put("10s and under", 0);
        ageGroups.put("20s", 0);
        ageGroups.put("30s", 0);
        ageGroups.put("40s", 0);
        ageGroups.put("50s", 0);
        ageGroups.put("60s", 0);
        ageGroups.put("70s and above", 0);
        ageGroups.put("Unknown", 0);
    }

    private String determineAgeGroup(LocalDate birthDay) {
        if (birthDay == null) {
            return "Unknown";
        }

        int age = calculateAge(birthDay, LocalDate.now());

        if (age < 20) {
            return "10s and under";
        } else if (age < 30) {
            return "20s";
        } else if (age < 40) {
            return "30s";
        } else if (age < 50) {
            return "40s";
        } else if (age < 60) {
            return "50s";
        } else if (age < 70) {
            return "60s";
        } else {
            return "70s and above";
        }
    }

    private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if (birthDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
    
}
