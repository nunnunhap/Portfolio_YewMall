package com.yewmall.basic.admin.qna;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yewmall.basic.admin.product.ProductVo;
import com.yewmall.basic.common.dto.Criteria;

public interface AdminQnaMapper {
	
	// 상품문의(Q&A) 목록
	List<Map<String, Object>> qna_list(Criteria cri);
	
	// 전체 데이터 개수(페이징)
	int getTotalCount(Criteria cri);
	
	// Q&A 답변 저장
	void answerInsert(@Param("qna_idx") Long qna_idx, @Param("answer") String answer);
	
	// Q&A 답변 수정 저장
	void answerModify(@Param("qna_idx") Long qna_idx, @Param("answer") String answer);
	
	// 개별 답변 삭제
	void answerDelete(Long qna_idx);
	
	// Q&A 일괄 삭제
	void qna_delete_all(List<Long> qna_idx_arr);
	
	
	
	
	
}
