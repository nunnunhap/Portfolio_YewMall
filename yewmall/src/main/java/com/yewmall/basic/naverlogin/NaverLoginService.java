package com.yewmall.basic.naverlogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NaverLoginService {
	
	@Value("${naver.client.id}")
	private String clientId;
	
	@Value("${naver.redirect.uri}")
	private String redirectUri;
	
	@Value("${naver.client.secret}")
	private String clientSecret;
	
	
	// 네이버 로그인 연동 URL 생성하기
	public String getNaverAuthorizationUrl() throws UnsupportedEncodingException {
		UriComponents uriComponents = UriComponentsBuilder
				.fromUriString("https://nid.naver.com/oauth2.0/authorize")
				.queryParam("response_type", "code")
				.queryParam("client_id", clientId)
				.queryParam("state", URLEncoder.encode("1234", "UTF-8")) // 임의의 값 1234
				.queryParam("redirect_uri", URLEncoder.encode(redirectUri, "UTF-8"))
				.build();
		
		log.info("생성된 URL : " + uriComponents.toString());
		return uriComponents.toString();
	}
	
	// 접근토큰 발급요청
	public String getNaverTokenUrl(NaverCallback callback) throws IOException {
		try {
			UriComponents uriComponents = UriComponentsBuilder
					.fromUriString("https://nid.naver.com/oauth2.0/token")
					.queryParam("grant_type", "authorization_code")
					.queryParam("client_id", clientId)
					.queryParam("client_secret", clientSecret)
					.queryParam("code", callback.getCode())
					.queryParam("state", URLEncoder.encode(callback.getState(), "UTF-8"))
					.build();
			
			URL url = new URL(uriComponents.toString());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			int responseCode = conn.getResponseCode();
			BufferedReader br;
			
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			
			br.close();
			
			log.info("응답데이터 : " + response.toString());
			
			return response.toString();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// 접근 토큰을 이용하여 프로필 API 호출(사용자 정보 받아오기)
	// https://openapi.naver.com/v1/nid/me
	public String getNaverUserByToken(NaverToken naverToken) {
		String accessToken = naverToken.getAccess_token();
		String tokenType = naverToken.getToken_type();
		
		try {
			URL url = new URL("https://openapi.naver.com/v1/nid/me");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", tokenType + " " + accessToken);
			
			int responseCode = conn.getResponseCode();
			BufferedReader br;
			
			// 입력스트림 작업
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			}else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			String inputLine;
			StringBuffer response = new StringBuffer();
			
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			
			br.close();
			
			log.info("사용자 정보 응답데이터 : " + response.toString());
			
			return response.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void getNaverTokenDelete(String access_token) {
		
		try {
			UriComponents uriComponents = UriComponentsBuilder
					.fromUriString("https://nid.naver.com/oauth2.0/token")
					.queryParam("grant_type", "delete")
					.queryParam("client_id", clientId)
					.queryParam("client_secret", clientSecret)
					.queryParam("access_token", URLEncoder.encode(access_token, "UTF-8"))
					.build();
			
			URL url = new URL(uriComponents.toString());
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			
			int responseCode = conn.getResponseCode();
			log.info("상태코드 : " + responseCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
}
