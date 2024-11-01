package com.yewmall.basic.mail;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/email/*")
public class EmailController {
	
	// DI
	private final EmailService emailService;
	
	// 메일 인증 발송
	@GetMapping("authcode")
	public ResponseEntity<String> authcode(String type, EmailDTO dto, HttpSession session) {
		log.info("dto : " + dto);
		ResponseEntity<String> entity = null;
		
		String authcode = "";
		for(int i = 0; i < 6; i++) {
			authcode += String.valueOf((int) (Math.random() * 10));
		}
		
		log.info("인증코드 : " + authcode);
		
		session.setAttribute("authcode", authcode);
		
		try {
			// 메일 제목 변경
			if(type.equals("emailJoin")) {
				dto.setSubject("YewMall 회원가입 메일 인증코드입니다.");
			} else if(type.equals("emailId")) {
				dto.setSubject("YewMall 아이디 인증코드입니다.");
			} else if(type.equals("emailPw")) {
				dto.setSubject("YewMall 비밀번호 인증코드입니다.");
			}
			
			emailService.sendMail(type, dto, authcode);
			entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String> (HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return entity;
	}
	
	// 메일 인증 확인
	@GetMapping("confirm_authcode")
	public ResponseEntity<String> confirm_authcode(String authcode, HttpSession session) {
		ResponseEntity<String> entity = null;
		
		// 세션이 유지되고 있는 동안
		if(session.getAttribute("authcode") != null) {
			if(authcode.equals(session.getAttribute("authcode"))) {
				entity = new ResponseEntity<String> ("success", HttpStatus.OK);
				session.removeAttribute("authcode");	
			} else {
				entity = new ResponseEntity<String> ("fail", HttpStatus.OK);
			}
		} else { // 세션이 소멸되었을 경우
			entity = new ResponseEntity<String> ("request", HttpStatus.OK);
		}

		return entity;
	}
	
	
	
	
}
