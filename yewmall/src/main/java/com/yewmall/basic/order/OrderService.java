package com.yewmall.basic.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yewmall.basic.cart.CartMapper;
import com.yewmall.basic.payinfo.PayInfoMapper;
import com.yewmall.basic.payinfo.PayInfoVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
	
	// DI
	private final OrderMapper orderMapper;
	private final PayInfoMapper payInfoMapper;
	private final CartMapper cartMapper;
	
	
	@Transactional // 트랜잭션
	public void order_process(OrderVo vo, String mbsp_id, String paymethod, String p_status, String payinfo) {
		log.info("order_process : " + vo);
		
		// 1) 주문 테이블(INSERT)
		vo.setMbsp_id(mbsp_id);
		orderMapper.order_insert(vo);
		
		// 2) 주문 상세 테이블(INSERT)
		orderMapper.orderDetail_insert(vo.getOrd_code(), mbsp_id);
		
		// 3) 결제 테이블(INSERT)
		PayInfoVo p_vo = PayInfoVo.builder()
				.ord_code(vo.getOrd_code())
				.mbsp_id(mbsp_id)
				.p_price(vo.getOrd_price())
				.paymethod(paymethod)
				.payinfo(payinfo)
				.p_status(p_status)
				.build();
		
		payInfoMapper.payInfo_insert(p_vo);
		
		// 4) 장바구니 테이블 삭제(DELETE)
		cartMapper.cart_empty(mbsp_id);
	}
	
	
}
