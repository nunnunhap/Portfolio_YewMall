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
	
	// 상품수정
	ProductVo pro_edit(Integer pro_num);
	
	// 상품수정 저장
	void pro_edit_ok(ProductVo vo);
	
	// 개별 상품 삭제
	void pro_delete(Integer pro_num);
	
	// 일괄 상품 삭제
	void pro_delete_all(List<ProductDTO> pro_delete_list);
	
}
