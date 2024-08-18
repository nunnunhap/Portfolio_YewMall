package com.yewmall.basic.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yewmall.basic.common.interceptor.LoginInterceptor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMvcConfig implements WebMvcConfigurer {
	
	// DI
	private final LoginInterceptor loginInterceptor;
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns(
								"/user/mypage/**",
								"/user/modify",
								"/user/changepw",
								"/user/delete",
								"/cart/cart_list",
								"/order/**",
								"/qna/qna_list_mypage",
								"/review/review_list_mypage",
								"/wish/wish_list_mypage"
								);
	}
	
	
	
}
