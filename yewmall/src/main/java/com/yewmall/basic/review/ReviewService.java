package com.yewmall.basic.review;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.yewmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	// DI
	private final ReviewMapper reviewMapper;
	
	
	// 리뷰(상품구매후기) 목록
	List<ReviewVo> rev_list(Integer pro_num, Criteria cri) {
		return reviewMapper.rev_list(pro_num, cri);
	}
	
	// 총 데이터 갯수
	public int getCountReviewByPro_num(Integer pro_num) {
		return reviewMapper.getCountReviewByPro_num(pro_num);
	}
	
	// 리뷰 저장
	void review_save(ReviewVo vo) {		
		reviewMapper.review_save(vo);
		// ProductVo의 revcount + 1
		reviewMapper.plus_revcount(vo.getPro_num());
	}
	
	// 리뷰 삭제
	void review_delete(Long rev_code) {
		// ProductVo의 revcount - 1
		reviewMapper.minus_revcount(rev_code);

		reviewMapper.review_delete(rev_code);
	}
	
	// 리뷰 수정 폼
	ReviewVo review_modify(Long rev_code) {
		return reviewMapper.review_modify(rev_code);
	}
	
	// 리뷰 수정 저장
	void review_update(ReviewVo vo) {
		reviewMapper.review_update(vo);
	}
	
	
	// 마이페이지 내 상품구매후기(리뷰) 목록
	List<Map<String, Object>> rev_list_user(@Param("mbsp_id") String mbsp_id, @Param("cri") Criteria cri) {
		return reviewMapper.rev_list_user(mbsp_id, cri);
	}
	
	// 마이페이지 내 전체 데이터 개수
	int getRevTotalCount(String mbsp_id) {
		return reviewMapper.getRevTotalCount(mbsp_id);
	}
	
	
}
