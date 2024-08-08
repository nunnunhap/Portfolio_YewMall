package com.yewmall.basic.admin.review;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yewmall.basic.common.constants.Constants;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.common.util.FileManagerUtils;
import com.yewmall.basic.review.ReviewVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/review/*")
@RequiredArgsConstructor
public class AdminReviewController {
	
	// DI
	private final AdminReviewService adminReviewService;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	
	// 상품구매후기(리뷰) 목록 및 페이징
	@GetMapping("review_list") // null값을 받기 위하여 wrapper class 사용
	public void review_list(Criteria cri, @RequestParam(required = false) Integer rev_rate, Model model) throws Exception {
		
		log.info("rev_rate : " + rev_rate);
		
		cri.setAmount(Constants.ADMIN_REVIEWLIST_AMOUNT);
		
		List<Map<String, Object>> rev_list = adminReviewService.rev_list(rev_rate, cri);
		int totalCount = adminReviewService.getTotalCount(rev_rate, cri);
		
		rev_list.forEach(vo -> {
			String folderPath = (String) vo.get("PRO_UP_FOLDER");
			if(folderPath != null) {
				vo.put("PRO_UP_FOLDER", folderPath.replace("\\", "/"));
			}
		});

		model.addAttribute("rev_list", rev_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		model.addAttribute("rev_rate", rev_rate);
		
	}
	
	// 리뷰 상품 이미지 출력
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// 리뷰 수정 저장
	@PostMapping("rev_modify")
	public ResponseEntity<String> rev_modify(ReviewVo vo) throws Exception {
		
		log.info("rev_code : " + vo.getRev_code());
		log.info("rev_title : " + vo.getRev_title());
		log.info("rev_content : " + vo.getRev_content());
		log.info("rev_rate : " + vo.getRev_rate());
		
		adminReviewService.revModify(vo);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 리뷰 삭제 및 일괄삭제
	@PostMapping("rev_delete")
	public ResponseEntity<String> rev_delete(@RequestParam("rev_code_arr") List<Long> rev_code_arr) throws Exception {
		
		adminReviewService.rev_delete(rev_code_arr);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	
}
