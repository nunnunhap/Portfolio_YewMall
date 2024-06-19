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
	
	
	
	
}
