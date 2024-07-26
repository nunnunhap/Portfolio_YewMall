package com.yewmall.basic.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yewmall.basic.admin.product.ProductVo;
import com.yewmall.basic.common.dto.Criteria;

public interface ReviewMapper {

	// 리뷰(상품구매후기) 목록
	List<ReviewVo> rev_list(@Param("pro_num") Integer pro_num, @Param("cri") Criteria cri);
	
	// 총 데이터 갯수(페이징)
	int getCountReviewByPro_num(Integer pro_num);
	
	// 리뷰 저장
	void review_save(ReviewVo vo);
	
	// 리뷰 삭제
	void review_delete(Long rev_code);
	
	// 리뷰 수정 폼
	ReviewVo review_modify(Long rev_code);
	
	// 리뷰 수정 저장
	void review_update(ReviewVo vo);
	
	// 리뷰 저장 시 리뷰갯수 증가
	void plus_revcount(Integer pro_num);
	
	// 리뷰 삭제 시 리뷰갯수 감소
	void minus_revcount(Long rev_code);
	
}
