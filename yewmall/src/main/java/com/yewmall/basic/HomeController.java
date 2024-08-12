package com.yewmall.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yewmall.basic.admin.product.ProductVo;
import com.yewmall.basic.common.constants.Constants;
import com.yewmall.basic.common.util.FileManagerUtils;
import com.yewmall.basic.product.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	
	// DI
	private final ProductService productService;
	
	
	@GetMapping("/")
	public String index(Model model) {
		// log.info("기본주소 /");
		
		int amount = Constants.CUSTOMER_MAINPAGE_AMOUNT;
		
		// 최신 상품 순서
		List<ProductVo> latest_pro_list = productService.main_latestproduct(amount);
		latest_pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		model.addAttribute("latest_pro_list", latest_pro_list);
		
		// 리뷰 많은 순서
		List<ProductVo> review_pro_list = productService.main_reviewproduct(amount);
		review_pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		model.addAttribute("review_pro_list", review_pro_list);
		
		return "index";
	}
	
	
	
	
	
}
