package com.yewmall.basic.qna;

import java.util.List;

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
	
	
}
