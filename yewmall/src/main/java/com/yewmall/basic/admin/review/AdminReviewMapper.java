package com.yewmall.basic.admin.review;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.review.ReviewVo;

public interface AdminReviewMapper {
	
	// 상품구매후기(리뷰) 목록
	List<Map<String, Object>> rev_list(@Param("rev_rate") Integer rev_rate, @Param("cri") Criteria cri);
	
	// 전체 데이터 개수(페이징)
	int getTotalCount(@Param("rev_rate") Integer rev_rate, @Param("cri") Criteria cri);
	
	// 리뷰 수정
	void revModify(ReviewVo vo);
	
	// 리뷰 삭제 및 일괄삭제
	void rev_delete(List<Long> rev_code_arr);
	
}
