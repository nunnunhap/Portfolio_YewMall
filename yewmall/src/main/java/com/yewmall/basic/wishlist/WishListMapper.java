package com.yewmall.basic.wishlist;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yewmall.basic.common.dto.Criteria;

public interface WishListMapper {
	
	// 위시리스트 목록
	List<Map<String, Object>> wish_list(@Param("cri") Criteria cri, @Param("mbsp_id") String mbsp_id);

	// 전체 데이터 개수
	int getTotalCount(String mbsp_id);	
	
	// 위시리스트 표시
	Long getWish(@Param("mbsp_id") String mbsp_id, @Param("pro_num") Integer pro_num);
	
	// 위시리스트 추가
	void insertWish(@Param("mbsp_id") String mbsp_id, @Param("pro_num") Integer pro_num);
	
	// 위시리스트 삭제
	void deleteWish(Long wish_idx);
	
}
