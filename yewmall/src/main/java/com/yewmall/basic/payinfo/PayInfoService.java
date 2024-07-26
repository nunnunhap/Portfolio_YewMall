package com.yewmall.basic.payinfo;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayInfoService {

	// DI
	private final PayInfoMapper payInfoMapper;
	
	
	// modal : 결제정보
	public PayInfoVo ord_pay_info(Long ord_code) {
		return payInfoMapper.ord_pay_info(ord_code);
	}
	
	// 결제테이블 주문금액 변경
	public void pay_tot_price_change(Long ord_code) {
		payInfoMapper.pay_tot_price_change(ord_code);
	}
	
	
	
}
