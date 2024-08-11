package com.yewmall.basic.wishlist;

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
import com.yewmall.basic.user.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/wish/*")
public class WishListController {
	
	// DI
	private final WishListService wishListService;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	
	// 위시리스트 목록/페이징
	@GetMapping("wish_list_mypage")
	public void wish_list_mypage(Criteria cri, HttpSession session, Model model) throws Exception {
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		cri.setAmount(Constants.CUSTOMER_WISHLIST_AMOUNT);
		
		List<Map<String, Object>> wish_list = wishListService.wish_list(cri, mbsp_id);
		int totalCount = wishListService.getTotalCount(mbsp_id);
		
		wish_list.forEach(vo -> {
			String folderPath = (String) vo.get("PRO_UP_FOLDER");
			if(folderPath != null) {
				vo.put("PRO_UP_FOLDER", folderPath.replace("\\", "/"));
			}
		});
		
		model.addAttribute("wish_list", wish_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 상품 이미지 출력
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// 위시리스트 추가/ 삭제
	@PostMapping("togglewish")
	public ResponseEntity<String> togglewish(Long wish_idx, Integer pro_num, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		
		log.info("wish_idx : " + wish_idx);
		
		if(session.getAttribute("login_status") != null) { // 로그인된 상태일 때
			if(wish_idx == null) { // 위시리스트 추가
				String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
				wishListService.insertWish(mbsp_id, pro_num);
				entity = new ResponseEntity<String> ("insert", HttpStatus.OK);
			} else { // 위시리스트 삭제
				wishListService.deleteWish(wish_idx);
				entity = new ResponseEntity<String> ("delete", HttpStatus.OK);
			}
		} else {
			entity = new ResponseEntity<String> ("login_required", HttpStatus.OK);
		}
		
		return entity;
	}
	
	
	
	
}
