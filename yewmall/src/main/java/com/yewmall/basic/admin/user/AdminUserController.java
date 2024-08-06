package com.yewmall.basic.admin.user;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yewmall.basic.admin.AdminVo;
import com.yewmall.basic.common.constants.Constants;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.common.util.FileManagerUtils;
import com.yewmall.basic.kakaologin.KakaoUserInfo;
import com.yewmall.basic.mail.EmailDTO;
import com.yewmall.basic.mail.EmailService;
import com.yewmall.basic.user.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/user/*")
public class AdminUserController {
	
	// DI
	private final AdminUserService adminUserService;
	private final EmailService emailService;
	
	
	// CKEditor 파일 업로드 경로
	@Value("${file.ckdir}")
	private String uploadCKPath;
	
	
	// 회원목록
	@GetMapping("user_list")
	public void user_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(Constants.ADMIN_USERLIST_AMOUNT);
		
		List<UserVo> user_list = adminUserService.user_list(cri);
		int totalCount = adminUserService.getTotalCount(cri);
		
		model.addAttribute("user_list", user_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 메일발송 목록
	@GetMapping("mailinglist")
	public void mailmnglist(Criteria cri, Model model) throws Exception {
		log.info("keyword : " + cri.getKeyword());
		
		cri.setAmount(Constants.ADMIN_MAILLIST_AMOUNT);
		
		List<MailMngVo> mailList = adminUserService.getMailInfoList(cri);
		int totalCount = adminUserService.getMailListCount(cri);
		
		model.addAttribute("maillist", mailList);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 메일발송 폼
	@GetMapping("mailingform")
	public void mailingform(@ModelAttribute("vo") MailMngVo vo, Model model) throws Exception {
		log.info("form의 vo : " + vo);
		log.info("m_idx : " + vo.getM_idx());
		
		if(vo.getM_idx() != null && !vo.getM_idx().equals("")) {
			MailMngVo voByIdx = adminUserService.mailingFormByIdx(vo.getM_idx());
			model.addAttribute("vo", voByIdx);
		}
	}
	
	// 메일발송 폼 저장
	@PostMapping("mailingsave")
	public String mailingsave(@ModelAttribute("vo") MailMngVo vo, HttpSession session, RedirectAttributes rttr) throws Exception {
		String admin_id = ((AdminVo) session.getAttribute("admin_state")).getAdmin_id();
		vo.setAdmin_id(admin_id);
		
		log.info("save의 vo : " + vo);
		adminUserService.mailingsave(vo);
		log.info("m_idx값 : " + vo.getM_idx()); // select key 사용
		
		// Model -> addAttribute() : 같은 요청 내에서 데이터 유지. 단순 데이터 전달 사용
		// RedirectAttributes -> addFlashAttribute() : 리다이렉트 이후에도 데이터 유지
		
		rttr.addFlashAttribute("msg", "save");
		rttr.addFlashAttribute("vo", vo);
		
		return "redirect:/admin/user/mailingform"; // redirect 사용하지 않을 시, 타임리프 파일명으로 해석됨.
	}

	// 메일발송 폼 수정
	@PostMapping("mailingmodify")
	public String mailingmodify(MailMngVo vo, RedirectAttributes rttr) throws Exception {
		log.info("modifydml vo : " + vo);
		adminUserService.mailingmodify(vo);
		
		rttr.addFlashAttribute("msg", "modify");
		rttr.addFlashAttribute("vo", vo);
		
		return "redirect:/admin/user/mailingform";
	}
	
	// 메일발송 폼 삭제
	@PostMapping("mailingdelete")
	public ResponseEntity<String> mailingdelete(Integer m_idx) throws Exception {
		adminUserService.mailingdelete(m_idx);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("delete", HttpStatus.OK);
		
		return entity;
	}
	
	// '발송' 버튼 클릭 후 메일발송 대상 회원 추출 form(메일발송 대상 회원 필터링)
	// @RequestParam(required = false) : 해당 요청 파라미터가 필수가 아님을 명시
	@GetMapping("mailsendingform")
	public void mailingsendingform(
			Integer m_idx, @RequestParam(required = false) String mbsp_receive, @RequestParam(required = false) String sns_login_type, Model model
			) throws Exception {
		// log.info("m_idx 값" + m_idx);
		// log.info("mbsp_receive 값" + mbsp_receive);
		// log.info("sns_login_type 값" + sns_login_type);
		
		MailMngVo vo = adminUserService.mailingFormByIdx(m_idx);
		// log.info("vo 값" + vo);
		model.addAttribute("vo", vo);
		
		List<UserVo> user_list = adminUserService.SendingMailUserList(mbsp_receive, sns_login_type);
		// log.info("user_list" + user_list);
		model.addAttribute("user_list", user_list);
		model.addAttribute("mbsp_receive", mbsp_receive);
		model.addAttribute("sns_login_type", sns_login_type);
	}
	
	// 선택한 회원 대상 메일 발송
	@PostMapping("mailingsend")
	public ResponseEntity<String> mailingsend(Integer m_idx, String m_title, String m_content, String[] mbsp_email_arr) throws Exception {
		log.info("mbsp_email_arr : " + mbsp_email_arr);
		
		EmailDTO dto = new EmailDTO("YewMall", "YewMallManager", "", m_title, m_content);
		emailService.sendMail(dto, mbsp_email_arr);
		
		adminUserService.mailSendCountUpdate(m_idx);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
		
	// CKEditor 이메일 발송 폼 내 이미지 업로드
	// HttpServletRequest request : 클라이언트의 요청정보를 가지고 있는 객체.
	// HttpServletResponse response : 서버에서 클라이언트에게 보낼 정보를 응답하는 객체
	// MultipartFile upload : CKeditor의 업로드탭에서 나온 파일첨부버튼(파일선택 : <input type="file" name="upload">)
	@PostMapping("imageupload")
	public void imageupload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
		OutputStream out = null;
		PrintWriter printWriter = null; // 업로드한 이미지정보를 브라우저에게 보내는 용도
		
		try {
			// 1) CKEditor를 이용한 파일 업로드 처리
			String fileName = upload.getOriginalFilename(); // 업로드할 클라이언트 파일 이름
			byte[] bytes = upload.getBytes(); // 업로드할 파일의 바이트 배열
			
			String ckUploadPath = uploadCKPath + fileName; // "C:\\Coding\\Portfolio\\upload\\ckeditor\\" + "abc.jpg"
			
			out = new FileOutputStream(ckUploadPath); // "C:\\Coding\\Portfolio\\upload\\ckeditor\\abc.jpg" 파일생성 0byte
			
			out.write(bytes); // 스트림 공간에 업로드할 파일의 바이트 배열을 채운 상태
			out.flush(); // 스트림의 버퍼에 남아 있는 데이터를 실제 출력 대상으로 밀어내는(flush) 역할 -> 크기가 채워진 정상파일로 인식
			
			// 2) 업로드한 이미지 파일에 대한 정보를 클라이언트에게 보내는 작업
			printWriter = response.getWriter();
			
			// 한글파일 인코딩 문제 발생으로 Constants 설정
			String fileUrl = Constants.ROOT_URL + "/admin/user/display/" + fileName; // 매핑주소/이미지파일명
			
			// CKEditor 4.12에서는 파일정보를 다음과 같이 구성하여 보내야 함.
			// {"filename" : "abc.jpg", "uploaded" : 1, "url" : "/ckupload/abc.jpg"}
			// {"filename" : 변수, "uploaded" : 1, "url" : 변수}
			printWriter.println("{\"filename\" :\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}"); // 스트림에 데이터를 채움.
			printWriter.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(printWriter != null) {
				printWriter.close();
			}
		}
	}
	
	// CKEditor에서 업로드 이미지 보여주는 기능
	@GetMapping("display/{fileName}")
	public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) {
		log.info("파일 이미지 : " + fileName);
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = FileManagerUtils.getFile(uploadCKPath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	
	
}
