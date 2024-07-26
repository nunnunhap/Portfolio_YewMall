package com.yewmall.basic.admin.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.order.OrderVo;

public interface AdminOrderMapper {

	// 주문목록
	List<OrderVo> order_list(@Param("cri") Criteria cri, @Param("start_date") String start_date, @Param("end_date") String end_date);
	
	// 전체 데이터 개수(페이징)
	int getTotalCount(@Param("cri") Criteria cri, @Param("start_date") String start_date, @Param("end_date") String end_date);
	
	// 주문 삭제
	void ord_delete(Long ord_code);
	
	// modal : 주문자(수령인) 정보
	OrderVo order_info(Long ord_code);
	
	// modal : 주문상품정보
	List<OrderDetailInfoVo> order_detail_info(Long ord_code);
	
	// modal : 주문상품 개별삭제
	void order_product_delete(@Param("ord_code")Long ord_code, @Param("pro_num") Integer pro_num);

	// 주문 테이블의 총 금액 변경
	void order_tot_price_change(Long ord_code);
	
	// modal : 주문 기본정보 수정
	void order_basic_modify(OrderVo vo);
	

	
}
