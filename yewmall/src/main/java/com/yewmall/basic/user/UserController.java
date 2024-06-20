package com.yewmall.basic.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yewmall.basic.mail.EmailDTO;
import com.yewmall.basic.mail.EmailService;

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
	private final EmailService emailService;
	
	
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
	
	// 로그인 버튼 클릭 시
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
	
	// 로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 아이디 찾기
	@GetMapping("idfind")
	public void idfindForm() {
		
	}
	
	// 아이디 찾기 버튼 클릭 시
	@PostMapping("idfind")
	public String idfindOk(String mbsp_name, String mbsp_email, String authcode, HttpSession session, RedirectAttributes rttr) throws Exception {
		String url = "";
		String msg = "";
		
		// 인증코드 확인
		if(authcode.equals(session.getAttribute("authcode"))) {
			// 아이디를 찾아 메일 발송
			String u_id = userService.idfind(mbsp_name, mbsp_email);
			if(u_id != null) { // 아이디가 존재하면
				String subject = "YewMall 아이디 찾기";
				EmailDTO dto = new EmailDTO("YewMall", "YewMallManager", mbsp_email, subject, u_id);
				
				emailService.sendMail("emailIdResult", dto, u_id);
				session.removeAttribute("authcode");
				
				msg = "success";
				url = "/user/login";
				rttr.addFlashAttribute("msg", msg);
			}else { // 아이디가 존재하지 않으면
				msg = "nameFail";
				url = "/user/idfind";
			}
		}else { // authcode 불일치
			msg = "failAuthCode";
			url = "/user/idfind";
		}
		rttr.addFlashAttribute("msg", msg);
		
		return "redirect:" + url;
	}
	
	// 비밀번호 찾기
	@GetMapping("pwfind")
	public void pwfindForm() {
		
	}
	
	// 비밀번호 찾기 버튼 클릭 시
	@PostMapping("pwfind")
	public String pwfind() throws Exception {
		
		return "";
	}
	
	// 마이페이지
	@GetMapping("mypage")
	public void mypage(HttpSession session, Model model) throws Exception {
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		UserVo vo = userService.login(mbsp_id);
		
		model.addAttribute("user", vo);
	}
	
	// 회원정보 변경
	@GetMapping("modify")
	public void modifyForm(HttpSession session, Model model) throws Exception {
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		UserVo vo = userService.login(mbsp_id);
		
		model.addAttribute("user", vo);
	}
	
	// 회원정보 변경 버튼 클릭
	@PostMapping("modify")
	public String modifyOk(UserVo vo, HttpSession session, RedirectAttributes rttr) throws Exception {
		log.info("회원정보수정 내역 :" + vo);
		
		// 인터셉터 구현 전까지 사용 예정
		if(session.getAttribute("login_status") == null) {
			return "redirect:/user/login";
		}
		
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		userService.modify(vo);
		
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/user/modify";
	}
	
	
	
	
	
	
	
	
	
}