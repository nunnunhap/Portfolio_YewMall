package com.yewmall.basic.admin.wishlist;

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

import com.yewmall.basic.common.constants.Constants;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.common.util.FileManagerUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/wish/*")
public class AdminWishListController {
	
	// DI
	private final AdminWishListService adminWishListService;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	
	// 위시리스트 목록/페이징
	@GetMapping("wish_list")
	public void wish_list(Criteria cri, Model model) throws Exception {
		cri.setAmount(Constants.ADMIN_WISHLIST_AMOUNT);
		
		List<Map<String, Object>> wish_list = adminWishListService.wish_list(cri);
		int totalCount = adminWishListService.getTotalCount();
		
		wish_list.forEach(vo -> {
			String folderPath = (String) vo.get("PRO_UP_FOLDER");
			if(folderPath != null) {
				vo.put("PRO_UP_FOLDER", folderPath.replace("\\", "/"));
			}
		});
		
		model.addAttribute("wish_list", wish_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 위시리스트 삭제
	@PostMapping("deletewish")
	public ResponseEntity<String> deletewish(Long wish_idx) throws Exception {
		ResponseEntity<String> entity = null;
		
		log.info("wish_idx : " + wish_idx);
		
		adminWishListService.deleteWish(wish_idx);
		entity = new ResponseEntity<String> ("delete", HttpStatus.OK);
		
		return entity;
	}
	
	// 상품 이미지 출력
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	
	
	
	
}
