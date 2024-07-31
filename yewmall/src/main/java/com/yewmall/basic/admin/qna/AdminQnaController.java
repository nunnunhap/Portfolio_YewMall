package com.yewmall.basic.admin.qna;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yewmall.basic.common.constants.Constants;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.common.util.FileManagerUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/qna/*")
public class AdminQnaController {
	
	// DI
	private final AdminQnaService adminQnaService;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	
	// qna목록과 페이징
	@GetMapping("qna_list")
	public void qna_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(Constants.ADMIN_QNALIST_AMOUNT);
		
		List<Map<String, Object>> qna_list = adminQnaService.qna_list(cri);
		int totalCount = adminQnaService.getTotalCount(cri);
		
		qna_list.forEach(vo -> {
			String folderPath = (String) vo.get("PRO_UP_FOLDER");
			if(folderPath != null) {
				vo.put("PRO_UP_FOLDER", folderPath.replace("\\", "/"));
			}
		});

		model.addAttribute("qna_list", qna_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// qna 상품 이미지 출력
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// qna 답변 저장
	@PostMapping("qna_insert")
	public ResponseEntity<String> qna_insert(Long qna_idx, String answer) throws Exception {
		log.info("qna번호 : " + qna_idx);
		log.info("qna답변 : " + answer);
		
		adminQnaService.answerInsert(qna_idx, answer);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// qna 수정 저장
	@PostMapping("qna_modify")
	public ResponseEntity<String> qna_modify(Long qna_idx, String answer) throws Exception {
		log.info("qna번호 : " + qna_idx);
		log.info("qna답변 : " + answer);
		
		adminQnaService.answerModify(qna_idx, answer);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// qna 답변 삭제하기
	@PostMapping("qna_delete")
	public ResponseEntity<String> qna_delete(Long qna_idx) throws Exception {
		log.info("qna번호 : " + qna_idx);
		
		adminQnaService.answerDelete(qna_idx);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// qna 삭제하기
	@PostMapping("qna_delete_all")
	public ResponseEntity<String> qna_delete_all(
			@RequestParam("qna_idx_arr") List<Long> qna_idx_arr) throws Exception {
		log.info("상품코드 : " + qna_idx_arr);
		
		adminQnaService.qna_delete_all(qna_idx_arr);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	
}
