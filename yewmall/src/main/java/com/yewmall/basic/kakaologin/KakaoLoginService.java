package com.yewmall.basic.kakaologin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginService {
	
	// DI
	private final KakaoMapper kakaoMapper;
	
	
	@Value("${kakao.client.id}")
	private String clientid;
	
	@Value("${kakao.redirect.uri}")
	private String redirectUri;
	
	@Value("${kakao.client.secret}")
	private String clientSecret;
	
	@Value("${kakao.oauth.tokenuri}")
	private String tokenUri;
	
	@Value("${kakao.oauth.userinfouri}")
	private String userinfoUri;
	
	@Value("${kakao.user.logout}")
	private String kakaologout;
	
	// 엑세스 토큰을 받기 위한 정보
	/* https://kauth.kakao.com/oauth/token 주소 호출
	 * 요청방식 post
	 * header : Content-type: application/x-www-form-urlencoded;charset=utf-8
	 * body :
	 * grant_type : authorization_code
	 * client_id : 앱 REST API 키
	 * redirect_uri : 인가 코드가 리다이렉트된 URI
	 * code : 인가 코드 받기 요청으로 얻은 인가 코드
	 * client_secret : 토큰 발급 시, 보안을 강화하기 위해 추가 확인하는 코드
	 */
	public String getAccessToken(String code) throws JsonMappingException, JsonProcessingException {
		// 1) Http Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 2) Http Body 생성
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientid);
		body.add("redirect_uri", redirectUri);
		body.add("code", code);
		body.add("client_secret", clientSecret);
		
		// 3) Header + Body 정보를 Entity로 구성
		HttpEntity<MultiValueMap<String, String>> tokenKakaoRequest = new HttpEntity<MultiValueMap<String, String>> (body, headers);
		
		// 4) Http 요청 보내기(API Server에게 통신을 담당하는 기능을 제공하는 클래스)
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(tokenUri, HttpMethod.POST, tokenKakaoRequest, String.class);
		
		// 5) Http 응답(JSON) -> Access Token 파싱작업
		String responseBody = response.getBody();
		log.info("응답데이터 : " + responseBody);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		return jsonNode.get("access_token").asText();
	}
	
	// access Token을 이용한 사용자 정보 받아오기(id, email, nickname)
	public KakaoUserInfo getKakaoUserInfo(String accessToken) throws JsonMappingException, JsonProcessingException {
		
		// 1) Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 2) Body 생성안함. API매뉴얼 상 필수조건 아님.
		
		// 3) Header + Body 정보를 Entity로 구성
		HttpEntity<MultiValueMap<String, String>> userInfoKakaoRequest = new HttpEntity<>(headers);
		
		// 4) Http 요청
		RestTemplate restTemplate = new RestTemplate();
		
		// 5) Http 응답
		ResponseEntity<String> responseEntity = restTemplate.exchange(userinfoUri, HttpMethod.POST, userInfoKakaoRequest, String.class);
		
		String responseBody = responseEntity.getBody();
		
		log.info("응답사용자 정보 : " + responseBody);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		Long id = jsonNode.get("id").asLong();
		String email = jsonNode.get("kakao_account").get("email").asText();
		String nickname = jsonNode.get("properties").get("nickname").asText();
		
		return new KakaoUserInfo(id, nickname, email);
		
	}
	
	// 카카오 로그아웃
	// 헤더 Authorization: Bearer ${ACCESS_TOKEN}
	public void kakaologout(String accessToken) throws JsonMappingException, JsonProcessingException {
		
		// Http Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		// Http 요청작업
		HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
		
		// Http 요청하기
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(kakaologout, HttpMethod.POST, kakaoLogoutRequest, String.class);
		
		// 리턴된 정보 : Json 포맷의 문자열
		String responseBody = response.getBody();
		
		// 직렬화 : Object -> Json
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		
		Long id = jsonNode.get("id").asLong();
		
		log.info("id : " + id);
	}
	
	
	
	
	
	
	
	
}
