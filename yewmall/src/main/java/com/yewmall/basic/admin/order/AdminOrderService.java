package com.yewmall.basic.admin.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.order.OrderVo;
import com.yewmall.basic.payinfo.PayInfoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminOrderService {
	
	// DI
	private final AdminOrderMapper adminOrderMapper;
	private final PayInfoService payInfoService; 
	
	
	// 주문목록
	List<OrderVo> order_list(Criteria cri, String start_date, String end_date) {
		return adminOrderMapper.order_list(cri, start_date, end_date);
	}
	
	// 전체 데이터 개수(페이징)
	int getTotalCount(Criteria cri, String start_date, String end_date) {
		return adminOrderMapper.getTotalCount(cri, start_date, end_date);
	}
	
	// 주문 삭제
	void ord_delete(Long ord_code) {
		adminOrderMapper.ord_delete(ord_code);
	}
	
	// modal : 주문자(수령인) 정보
	OrderVo order_info(Long ord_code) {
		return adminOrderMapper.order_info(ord_code);
	}
	
	// modal : 주문상품정보
	List<OrderDetailInfoVo> order_detail_info(Long ord_code) {
		return adminOrderMapper.order_detail_info(ord_code);
	}
	
	// modal : 주문상품 개별삭제
	@Transactional // 트랜잭션 처리 : 트랜잭션 기능이 적용된 프록시 객체가 생성되며, 성공 여부에 따라 Commit 또는 Rollback 작업.
	void order_product_delete(Long ord_code, Integer pro_num) {
		// 주문상품 개별삭제
		adminOrderMapper.order_product_delete(ord_code, pro_num);
		
		// 주문테이블 주문금액 변경
		adminOrderMapper.order_tot_price_change(ord_code);
		
		// 결제테이블 주문금액 변경
		payInfoService.pay_tot_price_change(ord_code);
	}
	
	// modal : 주문상품정보 수정
	void order_basic_modify(OrderVo vo) {
		adminOrderMapper.order_basic_modify(vo);
	}
	
	
	
	
	
	
}
