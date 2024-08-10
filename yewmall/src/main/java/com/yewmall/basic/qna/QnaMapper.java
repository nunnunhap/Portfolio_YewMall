package com.yewmall.basic.qna;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yewmall.basic.common.dto.Criteria;

public interface QnaMapper {
	
	// Q&A 목록
	List<QnaVo> qna_list(@Param("pro_num") int pro_num, @Param("cri") Criteria cri);
	
	// 총 데이터 갯수(페이징)
	int getCountQnaByPro_num(int pro_num);
	
	// Q&A 작성 후 저장
	void qna_save(QnaVo vo);
	
	// Q&A 삭제
	void qna_delete(Long qno);
	
	// Q&A 수정 폼
	QnaVo qna_modify(Long qno);
	
	// Q&A 수정 저장
	void qna_update(QnaVo vo);
	
	
	// 상품문의(Q&A) 목록
	List<Map<String, Object>> qna_list_user(@Param("mbsp_id") String mbsp_id, @Param("cri") Criteria cri);
	
	// 전체 데이터 개수(페이징)
	int getQnaTotalCount(String mbsp_id);	
	
}
