package com.yewmall.basic.wishlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yewmall.basic.common.constants.Constants;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.order.OrderVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/wish/*")
public class WishListController {
	
	// DI
	private final WishListService wishListService;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	
	// 위시리스트 목록/페이징
	@GetMapping("wish_list")
	public void wish_list(Criteria cri, Model model) throws Exception {
		cri.setAmount(Constants.CUSTOMER_WISHLIST_AMOUNT);
		
		/*
		List<OrderVo> order_list = adminOrderService.order_list(cri, start_date, end_date);
		int totalCount = adminOrderService.getTotalCount(cri, start_date, end_date);
		
		model.addAttribute("wish_list", wish_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		*/
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
