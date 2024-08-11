package com.yewmall.basic.admin.wishlist;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yewmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminWishListService {
	
	// DI
	private final AdminWishListMapper adminWishListMapper;
	
	
	// 위시리스트 목록
	List<Map<String, Object>> wish_list(Criteria cri) {
		return adminWishListMapper.wish_list(cri);
	}

	// 전체 데이터 개수
	int getTotalCount() {
		return adminWishListMapper.getTotalCount();
	}
	
	// 위시리스트 삭제
	void deleteWish(Long wish_idx) {
		adminWishListMapper.deleteWish(wish_idx);
	}
	
}
