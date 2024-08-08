package com.yewmall.basic.admin.review;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.review.ReviewVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReviewService {
	
	// DI
	private final AdminReviewMapper adminReviewMapper;
	
	
	// 상품구매후기(리뷰) 목록
	List<Map<String, Object>> rev_list(Integer rev_rate, Criteria cri) {
		return adminReviewMapper.rev_list(rev_rate, cri);
	}
	
	// 전체 데이터 개수(페이징)
	int getTotalCount(Integer rev_rate, Criteria cri) {
		return adminReviewMapper.getTotalCount(rev_rate, cri);
	}
	
	// 리뷰 수정
	void revModify(ReviewVo vo) {
		adminReviewMapper.revModify(vo);
	}
	
	// 리뷰 삭제 및 일괄삭제
	void rev_delete(List<Long> rev_code_arr) {
		adminReviewMapper.rev_delete(rev_code_arr);
	}
	
	
	
	
	
	
	
	
	
}
