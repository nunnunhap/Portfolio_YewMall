package com.yewmall.basic.admin.wishlist;

import java.util.List;
import java.util.Map;

import com.yewmall.basic.common.dto.Criteria;

public interface AdminWishListMapper {
	
	// 위시리스트 목록
	List<Map<String, Object>> wish_list(Criteria cri);

	// 전체 데이터 개수
	int getTotalCount();
	
	// 위시리스트 삭제
	void deleteWish(Long wish_idx);
	
	
}
