package com.yewmall.basic.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yewmall.basic.common.constants.Constants;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.common.util.FileManagerUtils;
import com.yewmall.basic.user.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/review/*")
@Slf4j
@RequiredArgsConstructor
public class ReviewController { // pro_detail.html 내 리뷰(상품구매후기) 처리

	// DI
	private final ReviewService reviewService;
	
	
	// 전역변수
	String mbsp_id;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	
	// Rest API 리뷰목록과 페이징
	@GetMapping("/revlist/{pro_num}/{page}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> revlist(@PathVariable("pro_num") int pro_num, @PathVariable("page") int page) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<>();
		
		// 1) 후기목록
		Criteria cri = new Criteria();
		cri.setAmount(5);
		cri.setPageNum(page);
		
		List<ReviewVo> revlist = reviewService.rev_list(pro_num, cri);
		
		// 2) 페이징 정보
		int revcount = reviewService.getCountReviewByPro_num(pro_num);
		PageDTO pageMaker = new PageDTO(cri, revcount);
		
		map.put("revlist", revlist);
		map.put("pageMaker", pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>> (map, HttpStatus.OK);
		
		return entity;
	}
	
	// 상품 후기 저장
	/* JacksonDatabind 라이브러리 : json 관련 내용은 이 라이브러리와 관련되어 있음.
	 * consumes : 클라이언트에서 보내오는 값의 포맷(MIME)
	 * produces : 스프링에서 클라이언트로 보내는 값.
	 * @RequestBody : 클라이언트에서 json포맷으로 넘어오는 경우 ReviewVo vo로 들어올 수 없어 이 어노테이션이 필요함.
	 * 	: JSON 형태의 데이터를 Java 객체에 매핑할 때 사용하는 어노테이션.
	 *  : Http 통신 메세지 구성의 status line, headers, body 세 부분으로 중 body 부분의 데이터를 받아올 때 사용.
	*/
	@PostMapping(value = "review_save", consumes = {"application/json"}, produces = {MediaType.TEXT_PLAIN_VALUE})
	@ResponseBody
	public ResponseEntity<String> review_save(@RequestBody ReviewVo vo, HttpSession session) throws Exception {
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		log.info("이름" + mbsp_id);
		
		vo.setMbsp_id(mbsp_id);
		
		log.info("상품후기데이터 : " + vo);
		
		reviewService.review_save(vo);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 상품 후기 삭제
	@DeleteMapping("review_delete/{rev_code}")
	@ResponseBody
	public ResponseEntity<String> review_delete(@PathVariable("rev_code") Long rev_code) throws Exception {
		log.info("상품코드 : " + rev_code);
		reviewService.review_delete(rev_code);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 리뷰 수정 폼
	@GetMapping("review_modify/{rev_code}")
	@ResponseBody
	public ResponseEntity<ReviewVo> review_modify(@PathVariable("rev_code") Long rev_code) throws Exception {

		ResponseEntity<ReviewVo> entity = null;
		entity = new ResponseEntity<ReviewVo> (reviewService.review_modify(rev_code), HttpStatus.OK);
		
		return entity;
	}
	
	// 리뷰 수정 저장
	@PutMapping("review_modify")
	@ResponseBody
	public ResponseEntity<String> review_modify(@RequestBody ReviewVo vo) throws Exception {
		reviewService.review_update(vo);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}

	
	// 마이페이지 내 상품구매후기(리뷰) 목록 및 페이징
	@GetMapping("review_list_mypage")
	public void review_list_mypage(Criteria cri, HttpSession session, Model model) throws Exception {
		mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		cri.setAmount(Constants.CUSTOMER_REVIEWLIST_AMOUNT);
		
		List<Map<String, Object>> rev_list = reviewService.rev_list_user(mbsp_id, cri);
		int totalCount = reviewService.getRevTotalCount(mbsp_id);
		
		rev_list.forEach(vo -> {
			String folderPath = (String) vo.get("PRO_UP_FOLDER");
			if(folderPath != null) {
				vo.put("PRO_UP_FOLDER", folderPath.replace("\\", "/"));
			}
		});

		model.addAttribute("rev_list", rev_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 상품 이미지 출력
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	
	
	
}
