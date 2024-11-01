package com.yewmall.basic.admin;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

	// DI
	private final AdminMapper adminMapper;
	
	
	// Admin 로그인
	public AdminVo loginOk(String admin_id) {
		log.info("서비스 쪽에서 loginOk" + adminMapper.loginOk(admin_id));
		return adminMapper.loginOk(admin_id);
	}
	
	
}
