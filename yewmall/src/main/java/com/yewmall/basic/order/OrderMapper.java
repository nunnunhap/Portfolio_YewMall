package com.yewmall.basic.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yewmall.basic.admin.order.OrderDetailInfoVo;
import com.yewmall.basic.common.dto.Criteria;

public interface OrderMapper {
	
	// 주문 테이블 저장
	void order_insert(OrderVo vo);
	
	// 주문 상세 테이블 저장
	void orderDetail_insert(@Param("ord_code") Long ord_code, @Param("mbsp_id") String mbsp_id);
	
	// 마이페이지 내 주문목록
	List<OrderVo> order_list(@Param("cri") Criteria cri, @Param("mbsp_id") String mbsp_id);
	
	// 마이페이지 내 전체 데이터 개수(페이징)
	int getTotalCount(String mbsp_id);
	
	// modal : 주문자(수령인) 정보
	OrderVo order_info(Long ord_code);
	
	// modal : 주문상품정보
	List<OrderDetailInfoVo> order_detail_info(Long ord_code);
	
	
}
