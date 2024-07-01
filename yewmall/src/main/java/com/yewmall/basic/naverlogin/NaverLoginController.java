package com.yewmall.basic.naverlogin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yewmall.basic.user.SNSUserDto;
import com.yewmall.basic.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth2/*")
public class NaverLoginController {
	
	// DI
	private final NaverLoginService naverLoginService;
	private final UserService userService;
	
	
	// STEP1
	@GetMapping("naverlogin")
	public String connect() throws UnsupportedEncodingException {
		String url = naverLoginService.getNaverAuthorizationUrl();
		
		return "redirect:" + url;
	}
	
	// STEP2
	// callback주소 생성작업.  http://localhost:9095/oauth2/callback/naver
	// API 요청 성공시 : http://콜백URL/redirect?code={code값}&state={state값}
	// API 요청 실패시 : http://콜백URL/redirect?state={state값}&error={에러코드값}&error_description={에러메시지}
	@GetMapping("callback/naver")
	public String callBack(NaverCallback callback, HttpSession session) throws IOException {
		
		if(callback.getError() != null) {
			log.info(callback.getError_description());
		}
		
		String responseToken = naverLoginService.getNaverTokenUrl(callback);
		
		ObjectMapper objectMapper = new ObjectMapper();
		NaverToken naverToken = objectMapper.readValue(responseToken, NaverToken.class);
		
		log.info("토큰정보 : " + naverToken.toString());
		
		// Access Token을 이용한 사용자 정보 받아오기
		String responseUser = naverLoginService.getNaverUserByToken(naverToken);
		NaverResponse naverResponse = objectMapper.readValue(responseUser, NaverResponse.class); 
		
		log.info("사용자 정보 : " + naverResponse.toString());
		
		String sns_email = naverResponse.getResponse().getEmail();
		
		if(naverResponse != null) {
			session.setAttribute("naver_status", naverResponse);
			session.setAttribute("accessToken", naverToken.getAccess_token());
			
			if(userService.existsUserInfo(sns_email) == null && userService.sns_user_check(sns_email) == null) {
				SNSUserDto dto = new SNSUserDto();
				dto.setId(naverResponse.getResponse().getId());
				dto.setEmail(naverResponse.getResponse().getEmail());
				dto.setName(naverResponse.getResponse().getName());
				dto.setSns_type("naver");
				
				log.info("Insert내용 : " + dto);
				userService.sns_user_insert(dto);
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("naverlogout")
	public String naverLogout(HttpSession session) {
		String accessToken = (String) session.getAttribute("accessToken");
		
		naverLoginService.getNaverTokenDelete(accessToken);
		
		if(accessToken != null && !"".equals(accessToken)) {
			session.removeAttribute("naver_status");
			session.removeAttribute("accessToken");
		}
		
		return "redirect:/";
	}

	
}