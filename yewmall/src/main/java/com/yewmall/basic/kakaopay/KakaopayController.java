package com.yewmall.basic.kakaopay;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yewmall.basic.cart.CartProductVo;
import com.yewmall.basic.cart.CartService;
import com.yewmall.basic.order.OrderService;
import com.yewmall.basic.order.OrderVo;
import com.yewmall.basic.user.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/kakao/*")
public class KakaopayController {
	
	// DI
	private final KakaopayService kakaopayService;
	private final CartService cartService;
	private final OrderService orderService;
	
	
	// Global Variable
	private OrderVo vo;
	private String mbsp_id;
	
	@GetMapping("kakaoPayRequest")
	public void kakaoPayRequest() {
		
	}
	
	// 결제준비(ready)
	@ResponseBody
	@GetMapping("kakaoPay")
	public ReadyResponse kakaoPay(OrderVo vo, HttpSession session) {
		log.info("ready 주문자 정보 :" + vo);
		
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		this.mbsp_id = mbsp_id;
		
		List<CartProductVo> cart_list = cartService.cart_list(mbsp_id);
		
		String partnerOrderId = mbsp_id;
		String partnerUserId = mbsp_id;
		String itemName = "";
		int quantity = 0; 
		int totalAmount = 0;
		int taxFreeAmount = 0;
		
		for(int i = 0; i < cart_list.size(); i++) {
			itemName += cart_list.get(i).getPro_name() + "/";
			quantity += cart_list.get(i).getCart_amount();
			totalAmount += cart_list.get(i).getPro_price() * cart_list.get(i).getCart_amount();
		}
		
		ReadyResponse readyResponse = kakaopayService.ready(partnerOrderId, partnerUserId, itemName, quantity, totalAmount, taxFreeAmount);
		log.info("응답데이터 : " + readyResponse);
		
		// 주문정보
		this.vo = vo;
		
		return readyResponse;
	}
	
	// 성공
	@GetMapping("approval")
	public void approval(String pg_token) {
		log.info("pg_token : " + pg_token);
		
		// 결제 승인 요청
		String approveResponse = kakaopayService.approve(pg_token);
		log.info("최종결과 : " + approveResponse);
		
		// 트랜잭션으로 처리 : 주문테이블, 주문상세테이블, 결제테이블, 장바구니 비우기
		if(approveResponse.contains("aid")) {
			log.info("주문자 정보 성공 : " + vo);
			orderService.order_process(vo, mbsp_id, "kakaopay", "완납", "kakaopay");
		}
	}
	
	// 취소
	@GetMapping("cancel")
	public void cancel() {
		
	}
	
	// 실패
	@GetMapping("fail")
	public void fail() {
		
	}
	
}
