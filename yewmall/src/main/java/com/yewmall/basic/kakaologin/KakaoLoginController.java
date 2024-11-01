package com.yewmall.basic.kakaologin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.yewmall.basic.user.SNSUserDto;
import com.yewmall.basic.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth2/*")
public class KakaoLoginController {

	// DI
	private final KakaoLoginService kakaoLoginService;
	private final UserService userService;
	
	
	@Value("${kakao.client.id}")
	private String clientid;
	
	@Value("${kakao.redirect.uri}")
	private String redirectUri;
	
	@Value("${kakao.client.secret}")
	private String clientSecret;
	
	
	// Step1 카카오로그인 API Server에게 인가코드 요청작업
	@GetMapping("kakaologin")
	public String connect() {
		StringBuffer url = new StringBuffer();
		url.append("https://kauth.kakao.com/oauth/authorize?");
		url.append("response_type=code");
		url.append("&client_id=" + clientid);
		url.append("&redirect_uri=" + redirectUri);
//		url.append("&prompt=login"); // 추가옵션 : 재 로그인 시 절차를 다시 밟도록해주는 기능
		
		log.info("인가코드 : " + url.toString());
		
		return "redirect:" + url.toString();
	}
	
	// Step2 카카오 로그인 API에서 현재 개발사이트 callback 주소 호출
	@GetMapping("callback/kakao")
	public String callback(String code, HttpSession session) {
		// code : 토큰 받기 요청에 필요한 인가코드
		log.info("code :" + code);
		
		String accessToken = "";
		KakaoUserInfo kakaoUserInfo = null;
		
		try {
			accessToken = kakaoLoginService.getAccessToken(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			kakaoUserInfo = kakaoLoginService.getKakaoUserInfo(accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(kakaoUserInfo != null) {
			log.info("사용자 정보 : " + kakaoUserInfo);
			
			// 세션작업
			session.setAttribute("kakao_status", kakaoUserInfo);
			session.setAttribute("accessToken", accessToken);
			
			String sns_email = kakaoUserInfo.getEmail();
			String sns_login_type = userService.existsUserInfo(sns_email);
			
			if(userService.existsUserInfo(sns_email) == null && userService.sns_user_check(sns_email) == null) {
				SNSUserDto dto = new SNSUserDto();
				dto.setId(kakaoUserInfo.getId().toString());
				dto.setEmail(kakaoUserInfo.getEmail());
				dto.setName(kakaoUserInfo.getNickname());
				dto.setSns_type("kakao");
				
				userService.sns_user_insert(dto);
			}
		}
		return "redirect:/";
	}
	
	// Kakao 계정 로그아웃
	@GetMapping("kakaologout")
	public String kakaologout(HttpSession session) {
		String accessToken = (String) session.getAttribute("accessToken");
		
		if(accessToken != null && !"".equals(accessToken)) {
			try {
				kakaoLoginService.kakaologout(accessToken);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
			session.removeAttribute("kakao_status");
			session.removeAttribute("accessToken");
		}
		
		return "redirect:/";
	}
	
	
}
