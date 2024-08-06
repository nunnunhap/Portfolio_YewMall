package com.yewmall.basic.admin.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.user.UserVo;

public interface AdminUserMapper {
	
	// 회원목록
	List<UserVo> user_list(Criteria cri);
	
	// 회원 목록 전체 데이터 개수(페이징)
	int getTotalCount(Criteria cri);
	
	// 메일발송 목록
	List<MailMngVo> getMailInfoList(Criteria cri);
	
	// 메일발송 목록 전체 데이터 개수(페이징)
	int getMailListCount(Criteria cri);
	
	// 메일발송 폼 저장
	void mailingsave(MailMngVo vo);
	
	// 메일발송 폼 수정 저장
	void mailingmodify(MailMngVo vo);
	
	// 저장된 메일발송 폼 불러오기
	MailMngVo mailingFormByIdx(Integer m_idx);
	
	// 메일발송 폼 삭제
	void mailingdelete(Integer m_idx);
	
	// 필터링 적용된 메일발송대상 회원목록
	List<UserVo> SendingMailUserList(@Param("mbsp_receive") String mbsp_receive, @Param("sns_login_type") String sns_login_type);
	
	// 메일 발송횟수 업데이트
	void mailSendCountUpdate(Integer m_idx);
	
	
	
}
