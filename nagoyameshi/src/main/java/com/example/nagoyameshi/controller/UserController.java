package com.example.nagoyameshi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.form.UserPasswordForm;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userDetailsImpl.getUser();
		
		model.addAttribute("user", user);
		
		return "user/index";
	}
	
	@GetMapping("/edit")
	public String edit(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userDetailsImpl.getUser();
		UserEditForm userEditForm = new UserEditForm(user.getId(), user.getName(), user.getFurigana(), user.getPostalCode(), user.getAddress(), user.getPhoneNumber(), user.getBirthday(), user.getEmail());
		model.addAttribute("userEditForm", userEditForm);
		return "user/edit";
	}
	
	@PostMapping("/update")
    public String update(@ModelAttribute @Validated UserEditForm userEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // メールアドレスが変更されており、かつ登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
        if (userService.isEmailChanged(userEditForm) && userService.isEmailRegistered(userEditForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
            bindingResult.addError(fieldError);                       
        }
        
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        
        userService.update(userEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "会員情報を編集しました。");
        
        return "redirect:/user";
    }
	
	@GetMapping("/change-pass")
	public String changePass(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userDetailsImpl.getUser();
		UserPasswordForm userPasswordForm = new UserPasswordForm();
		
		model.addAttribute("user", user);
		model.addAttribute("userPasswordForm", userPasswordForm);
		
		return "user/change-pass";
	}
	
	@PostMapping("/update-pass")
	public String updatePass(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
							 @ModelAttribute @Validated UserPasswordForm userPasswordForm,
							 BindingResult bindingResult,
							 RedirectAttributes redirectAttributes) {
		User user = userDetailsImpl.getUser();
		
		// 入力されたパスワードが古いパスワードに一致しなければエラーを表示
		if (!userService.isSamePasswordEncode(userPasswordForm.getOldPassword(), user.getPassword())) {
			System.out.println(userPasswordForm.getOldPassword());
			System.out.println(user.getPassword());
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "oldPassword", "元のパスワードが間違っています。");
            bindingResult.addError(fieldError);
        }
		// 新しいパスワードと新しいパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加する
        if (!userService.isSamePassword(userPasswordForm.getNewPassword(), userPasswordForm.getNewPasswordConfirmation())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "newPassword", "新しいパスワードが一致しません。");
            bindingResult.addError(fieldError);
            System.out.println(userPasswordForm.getNewPassword());
            System.out.println(userPasswordForm.getNewPasswordConfirmation());
        }
        
        userService.updatePass(user, userPasswordForm);
        redirectAttributes.addFlashAttribute("successMessage", "パスワードを変更しました。");
        
        return "redirect:/user";
	}
	
	@GetMapping("/api/user-status")
	public UserStatusResponse getUserStatus(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
		User user = userDetailsImpl.getUser();
		boolean isPremium = userService.isUserPremium(user);
		
		return new UserStatusResponse(isPremium);
	}
	
	public static class UserStatusResponse {
		private boolean isPremium;
		
		public UserStatusResponse(boolean isPremium) {
			this.isPremium = isPremium;
		}
		public boolean isPremium() {
			return isPremium;
		}
	}
}
