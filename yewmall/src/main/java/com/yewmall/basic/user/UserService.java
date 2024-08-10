package com.yewmall.basic.user;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.review.ReviewVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	public String pwfind(String mbsp_id, String mbsp_name, String mbsp_email) {
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
	
	// SNS 계정 존재유무
	public String existsUserInfo(String sns_email) {
		return userMapper.existsUserInfo(sns_email);
	}
	
	// SNS USER 중복체크
	public String sns_user_check(String sns_email) {
		return userMapper.sns_user_check(sns_email);
	}
	
	// SNS USER 정보 등록
	public void sns_user_insert(SNSUserDto dto) {
		userMapper.sns_user_insert(dto);
	}
	
	
	
}
