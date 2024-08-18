package com.yewmall.basic.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yewmall.basic.common.interceptor.AdminInterceptor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminMvcConfig implements WebMvcConfigurer {
	
	// DI
	private final AdminInterceptor adminInterceptor;
	
	
	// 인터셉터 매핑주소 설정에서 제외되는 경로 작업
	private static final String[] EXCLUDE_PATHS = {
			"/admin/",
			"/admin/admin_ok"
	};
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminInterceptor)
				.addPathPatterns("/admin/**")
				.excludePathPatterns(EXCLUDE_PATHS);
	}
	
	
}
