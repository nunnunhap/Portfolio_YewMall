package com.yewmall.basic.order;

import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
	
	// 주문 테이블 저장
	void order_insert(OrderVo vo);
	
	// 주문 상세 테이블 저장
	void orderDetail_insert(@Param("ord_code") Long ord_code, @Param("mbsp_id") String mbsp_id);
	
}
