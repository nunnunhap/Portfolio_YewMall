package com.yewmall.basic.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/admin/*")
@Slf4j
@Controller
public class AdminController {
	
	// DI
	private final AdminService adminService;
	private final PasswordEncoder passwordEncoder;
	
	
	// 관리자 로그인 폼
	@GetMapping("/")
	public String loginForm() {
		
		return "/admin/adminLogin";
	}
	
	// 관리자 로그인 버튼 클릭 시
	@PostMapping("admin_ok")
	public String admin_ok(AdminVo vo, HttpSession session) throws Exception {
		log.info("관리자 정보 : " + vo);
		
		AdminVo d_vo = adminService.loginOk(vo.getAdmin_id());
		String url = "";
		
		if(d_vo != null) {
			if(passwordEncoder.matches(vo.getAdmin_pw(), d_vo.getAdmin_pw())) {
				log.info("비밀번호 일치");
				
				d_vo.setAdmin_pw("");
				session.setAttribute("admin_state", d_vo);
				url = "admin/admin_menu";
			}
		}
		return "redirect:/" + url;
	}
	
	// 메인 페이지
	@GetMapping("admin_menu")
	public String admin_menu() {
		return "redirect:/admin/product/pro_list";
	}
	
	// 로그아웃
	@GetMapping("admin_logout")
	public String admin_logout(HttpSession session) {
		session.removeAttribute("admin_state");
		
		return "redirect:/admin/";
	}
	
	
}