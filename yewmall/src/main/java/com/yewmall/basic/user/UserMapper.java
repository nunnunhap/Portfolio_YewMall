package com.yewmall.basic.user;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
	
	// 아이디 중복체크
	String idCheck(String mbsp_id);
	
	// 회원가입 저장
	void join(UserVo vo);
	
	// 로그인
	UserVo login(String mbsp_id);
	
	// ID 찾기
	String idfind(@Param("mbsp_name") String mbsp_name, @Param("mbsp_email") String mbsp_email);
	
	// PW 찾기
	String pwfind(@Param("mbsp_id") String mbsp_id, @Param("mbsp_name") String mbsp_name, @Param("mbsp_email") String mbsp_email);
	
	// PW 업데이트 작업(PW 찾기)
	void tempPwUpdate(@Param("mbsp_id") String mbsp_id, @Param("temp_enc_pw") String temp_enc_pw);
	
	// 회원정보 수정
	void modify(UserVo vo);
	
	// PW 변경
	void changePw(@Param("mbsp_id") String mbsp_id, @Param("new_mbsp_password") String new_mbsp_password);
	
	// 회원정보 삭제
	void delete(String mbsp_id);
	
	// SNS 계정 존재유무
	String existsUserInfo(String sns_email);
	
	// SNS USER 중복체크
	String sns_user_check(String sns_email);
	
	// SNS USER 정보 등록
	void sns_user_insert(SNSUserDto dto);
	
	
	
}
