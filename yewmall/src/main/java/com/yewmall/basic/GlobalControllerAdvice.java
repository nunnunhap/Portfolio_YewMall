package com.yewmall.basic;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.yewmall.basic.admin.category.AdminCategoryService;
import com.yewmall.basic.admin.category.AdminCategoryVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(basePackages = {"com.yewmall.basic"})
@Slf4j
@RequiredArgsConstructor
public class GlobalControllerAdvice {
	
	// DI
	private final AdminCategoryService adminCategoryService;
	
	
	@ModelAttribute // 클라이언트로부터 일반 HTTP 요청 파라미터나 multipart/form-data 형태의 파라미터를 받아 객체로 사용하고 싶을 때 이용
	public void comm_test(Model model) {
		log.info("공통코드 실행");
		List<AdminCategoryVo> user_cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("user_cate_list", user_cate_list);
	}
	
}
