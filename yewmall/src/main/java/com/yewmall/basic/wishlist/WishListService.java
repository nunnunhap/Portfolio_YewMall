package com.yewmall.basic.wishlist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.yewmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishListService {
	
	// DI
	private final WishListMapper wishListMapper;

	// 위시리스트 목록
	List<Map<String, Object>> wish_list(Criteria cri, String mbsp_id) {
		return wishListMapper.wish_list(cri, mbsp_id) ;
	}

	// 전체 데이터 개수
	int getTotalCount(String mbsp_id) {
		return wishListMapper.getTotalCount(mbsp_id) ;
	}
	
	// 위시리스트 표시
	public Long getWish(String mbsp_id, Integer pro_num) {
		return wishListMapper.getWish(mbsp_id, pro_num);
	}
	
	// 위시리스트 추가
	void insertWish(String mbsp_id, Integer pro_num) {
		wishListMapper.insertWish(mbsp_id, pro_num);
	}
	
	// 위시리스트 삭제
	void deleteWish(Long wish_idx) {
		wishListMapper.deleteWish(wish_idx);
	}
	
	
	
	
	
	
}
