package com.yewmall.basic.payinfo;

public interface PayInfoMapper {
	
	// 결제정보 저장
	void payInfo_insert(PayInfoVo vo);
	
	// 주문삭제
	void ord_pay_delete(Long ord_code);
	
	// modal : 결제정보
	PayInfoVo ord_pay_info(Long ord_code);
	
	// 결제테이블 주문금액 변경
	void pay_tot_price_change(Long ord_code);
	
	
	
	
}
