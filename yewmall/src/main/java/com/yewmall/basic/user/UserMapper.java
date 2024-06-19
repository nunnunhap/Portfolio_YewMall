package com.yewmall.basic.user;

public interface UserMapper {
	
	// 아이디 중복체크
	String idCheck(String mbsp_id);
	
	// 회원가입 저장
	void join(UserVo vo);
	
	// 로그인
	UserVo login(String mbsp_id);
		
	
}
