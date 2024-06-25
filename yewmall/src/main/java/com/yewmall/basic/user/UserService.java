package com.yewmall.basic.user;

import java.util.UUID;

import org.apache.ibatis.annotations.Param;
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
	String pwfind(String mbsp_id, String mbsp_name, String mbsp_email) {
		return userMapper.pwfind(mbsp_id, mbsp_name, mbsp_email);
	}
	
	// 임시 비밀번호 10자리
	public String getTempPw() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}
	
	// PW 업데이트 작업(PW 찾기)
	public void tempPwUpdate(String mbsp_id, String temp_enc_pw) {
		userMapper.tempPwUpdate(mbsp_id, temp_enc_pw);
	}
	
	// 회원정보 수정
	public void modify(UserVo vo) {
		userMapper.modify(vo);
	}
	
	// PW 변경
	public void changePw(String mbsp_id, String new_mbsp_password) {
		userMapper.changePw(mbsp_id, new_mbsp_password);
	}
	
	// 회원정보 삭제
	public void delete(String mbsp_id) {
		userMapper.delete(mbsp_id);
	}
	
	
	
}
