package com.yewmall.basic.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.yewmall.basic.admin.AdminVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// AdminMvcConfig에서 어떤 매핑주소를 인터셉트할 것인지 설정
@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("preHandle 핸들");
		
		boolean result = false;
		
		HttpSession session = request.getSession();
		AdminVo vo = (AdminVo) session.getAttribute("admin_state");
		
		if(vo == null) { // 요청이 인증되지 않은 상태
			result = false; // result 값이 false일 시 Controller는 진행되지 않음.
			
			if(isAjaxRequest(request)) { // ajax요청이라는 의미
				response.sendError(400); // 400 : Client쪽의 문제란 것
			} else { // 일반요청
				// 원래 요청한 주소를 세션으로 저장하는 기능
				getTargetUrl(request);
				response.sendRedirect("/admin/");
			}
		} else {
			result = true;
		}
		
		return result;
	}
	
	// 원래 요청 주소
	private void getTargetUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		String targetUrl = uri + query;
		
		if(request.getMethod().equals("GET")) {
			// 요청한 주소에 대한 정보를 세션으로 저장
			request.getSession().setAttribute("targetUrl", targetUrl);
		}
	}
	
	// 사용자 요청이 ajax요청인지 구분
	private boolean isAjaxRequest(HttpServletRequest request) {
		boolean isAjax = false;
		
		String header = request.getHeader("AJAX");
		
		if(header != null && header.equals("true")) {
			isAjax = true;
		}
		return isAjax;
	}
	
	
	
}
