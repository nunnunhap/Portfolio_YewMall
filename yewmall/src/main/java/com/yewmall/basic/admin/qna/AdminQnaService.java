package com.yewmall.basic.admin.qna;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.yewmall.basic.admin.product.ProductDTO;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.qna.QnaVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminQnaService {
	
	// DI
	private final AdminQnaMapper adminQnaMapper;
	
	
	// 상품문의(Q&A) 목록
	List<Map<String, Object>> qna_list(Criteria cri) {
		return adminQnaMapper.qna_list(cri);
	}
	
	// 전체 데이터 개수(페이징)
	int getTotalCount(Criteria cri) {
		return adminQnaMapper.getTotalCount(cri);
	}
	
	// qna 답변 저장
	void answerInsert(Long qna_idx, String answer) {
		adminQnaMapper.answerInsert(qna_idx, answer);
	}
	
	// Q&A 답변 수정 저장
	void answerModify(Long qna_idx, String answer) {
		adminQnaMapper.answerModify(qna_idx, answer);
	}
	
	// 개별 답변 삭제
	void answerDelete(Long qna_idx) {
		adminQnaMapper.answerDelete(qna_idx);
	}
	
	// Q&A 일괄 삭제
	void qna_delete_all(List<Long> qna_idx_arr) {
		adminQnaMapper.qna_delete_all(qna_idx_arr);
	}
	
	
	
	
}
