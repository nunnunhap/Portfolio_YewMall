package com.yewmall.basic.admin.product;

import java.util.List;

import com.yewmall.basic.common.dto.Criteria;

public interface AdminProductMapper {

	// 판매상품등록
	void pro_insert(ProductVo vo);
	
	// 상품목록
	List<ProductVo> pro_list(Criteria cri);
	
	// 전체 데이터 개수(페이징)
	int getTotalCount(Criteria cri);
	
	
	
	
	
}
