package com.yewmall.basic.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	// DI
	private final UserMapper userMapper;
	
	// 아이디 중복체크
	public String idCheck(String mbsp_id) {
		return userMapper.idCheck(mbsp_id);
	}
	
	// 회원가입 저장
	public void join(UserVo vo) {
		userMapper.join(vo);
	}
	
	// 로그인
	public UserVo login(String mbsp_id) {
		return userMapper.login(mbsp_id);
	}
	
	
	// ID 찾기
	public String idfind(String mbsp_name, String mbsp_email) {
		return userMapper.idfind(mbsp_name, mbsp_email);
	}
	
	// PW 찾기
	
	
	
	
	// 회원정보 수정
	public void modify(UserVo vo) {
		userMapper.modify(vo);
	}
	
	
}
