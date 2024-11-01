package com.yewmall.basic.qna;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.yewmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {
	
	// DI
	private final QnaMapper qnaMapper;
	
	
	// 리뷰(상품구매후기) 목록
	List<QnaVo> qna_list(int pro_num, Criteria cri) {
		return qnaMapper.qna_list(pro_num, cri);
	}
	
	// 총 데이터 갯수(페이징)
	int getCountQnaByPro_num(int pro_num) {
		return qnaMapper.getCountQnaByPro_num(pro_num);
	}
	
	// Q&A 작성 후 저장
	void qna_save(QnaVo vo) {
		qnaMapper.qna_save(vo);
	}
	
	// Q&A 삭제
	void qna_delete(Long qno) {
		qnaMapper.qna_delete(qno);
	}
	
	// Q&A 수정 폼
	QnaVo qna_modify(Long qno) {
		return qnaMapper.qna_modify(qno);
	}
	
	// Q&A 수정 저장
	void qna_update(QnaVo vo) {
		qnaMapper.qna_update(vo);
	}
	
	
	// 상품문의(Q&A) 목록
	List<Map<String, Object>> qna_list_user(String mbsp_id, Criteria cri) {
		return qnaMapper.qna_list_user(mbsp_id, cri);
	}
	
	// 전체 데이터 개수(페이징)
	int getQnaTotalCount(String mbsp_id) {
		return qnaMapper.getQnaTotalCount(mbsp_id);
	}
	
}
