package com.yewmall.basic.admin.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.user.UserVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {
	
	// DI
	private final AdminUserMapper adminUserMapper;
	
	
	// 회원목록
	List<UserVo> user_list(Criteria cri) {
		return adminUserMapper.user_list(cri);
	}
	
	// 회원 목록 전체 데이터 개수(페이징)
	int getTotalCount(Criteria cri) {
		return adminUserMapper.getTotalCount(cri);
	}
	
	// 메일발송 목록
	List<MailMngVo> getMailInfoList(Criteria cri) {
		return adminUserMapper.getMailInfoList(cri);
	}
	
	// 메일발송 목록 전체 데이터 개수(페이징)
	int getMailListCount(Criteria cri) {
		return adminUserMapper.getMailListCount(cri);
	}
	
	// 메일발송 폼 저장
	void mailingsave(MailMngVo vo) {
		adminUserMapper.mailingsave(vo);
	}
	
	// 메일발송 폼 수정 저장
	void mailingmodify(MailMngVo vo) {
		adminUserMapper.mailingmodify(vo);
	}
	
	// 저장된 메일발송 폼 불러오기
	MailMngVo mailingFormByIdx(Integer m_idx) {
		return adminUserMapper.mailingFormByIdx(m_idx);
	}
	
	// 메일발송 폼 삭제
	void mailingdelete(Integer m_idx) {
		adminUserMapper.mailingdelete(m_idx);
	}
	
	// 필터링 적용된 메일발송대상 회원목록
	List<UserVo> SendingMailUserList(String mbsp_receive, String sns_login_type) {
		return adminUserMapper.SendingMailUserList(mbsp_receive, sns_login_type);
	}
	
	// 메일 발송횟수 업데이트
	void mailSendCountUpdate(Integer m_idx) {
		adminUserMapper.mailSendCountUpdate(m_idx);
	}
	
	
	
}
