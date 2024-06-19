package com.yewmall.basic.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user/*")
@Slf4j
@RequiredArgsConstructor
public class UserController {
	
	// DI
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	
	
	// 회원가입폼
	@GetMapping("join")
	public void joinForm() {
		log.info("join");
	}
	
	
	// 회원가입폼 저장
	@PostMapping("join")
	public String join(UserVo vo) throws Exception {
		vo.setMbsp_password(passwordEncoder.encode(vo.getMbsp_password())); // 암호화과정
		
		log.info("회원정보 : " + vo);
		
		userService.join(vo);
		
		return "redirect:/";
	}
	
	
	// ID 중복체크
	@GetMapping("idCheck")
	public ResponseEntity<String> idCheck(String mbsp_id) throws Exception {
		log.info("아이디 : " + mbsp_id);
		ResponseEntity<String> entity = null;
		
		// 아이디 사용가능여부
		String idUse = "";
		if(userService.idCheck(mbsp_id) != null) {
			idUse = "no"; // 사용불가
		} else {
			idUse = "yes"; // 사용가능
		}
		
		entity = new ResponseEntity<String> (idUse, HttpStatus.OK);
		
		return entity;
	}
	
	
	// 로그인 폼
	@GetMapping("login")
	public void loginForm() {
		
	}
	
	@PostMapping("login")
	public String loginOk(LoginDTO dto, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		UserVo vo = userService.login(dto.getMbsp_id());
		
		String msg = "";
		String url = "/"; // "/" 메인주소
		
		if(vo != null) { // 아이디가 존재하는 경우
			if(passwordEncoder.matches(dto.getMbsp_password(), vo.getMbsp_password())) { // 비밀번호 일치
				vo.setMbsp_password("");
				session.setAttribute("login_status", vo);
			}else { // 비밀번호 불일치
				msg = "failPW";
				url = "/user/login";
			}
		}else { // 아이디가 존재하지 않을 경우
			msg = "failID";
			url = "/user/login";
		}
		
		rttr.addFlashAttribute("msg", msg);
		
		return "redirect:" + url;
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/";
	}
	
	
	
	
}
