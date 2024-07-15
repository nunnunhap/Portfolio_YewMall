package com.yewmall.basic.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yewmall.basic.common.util.FileManagerUtils;
import com.yewmall.basic.user.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/cart/*")
@Slf4j
@RequiredArgsConstructor
public class CartController {
	
	// DI
	private final CartService cartService;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	
	// 장바구니 추가
	@GetMapping("cart_add")
	public ResponseEntity<String> cart_add(CartVo vo, HttpSession session) throws Exception {
		ResponseEntity<String> entity = null;
		log.info("장바구니 : " + vo.getPro_num());
		log.info("세션정보 " + ((UserVo) session.getAttribute("login_status")));
		
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		// db 연동작업
		cartService.cart_add(vo);
		
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 장바구니 목록
	@GetMapping("cart_list")
	public void cart_list(HttpSession session, Model model) throws Exception {
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		
		List<CartProductVo> cart_list = cartService.cart_list(mbsp_id);
		cart_list.forEach(vo -> vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/")));
		
		model.addAttribute("cart_list", cart_list);
	}
	
	// 상품리스트에서 사용할 이미지 보여주기.
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// 장바구니 상품 삭제
	@GetMapping("cart_del")
	public String cart_del(Long cart_code) throws Exception {
		cartService.cart_del(cart_code);
		
		return "redirect:/cart/cart_list";
	}
	
	// 장바구니 상품 수량 변경
	@GetMapping("cart_change")
	public String cart_change(Long cart_code, int cart_amount) throws Exception {
		log.info("장바구니 코드 : " + cart_code);
		log.info("장바구니 수량 : " + cart_amount);
		
		cartService.cart_change(cart_code, cart_amount);
		
		return "redirect:/cart/cart_list";
	}
	
	// 장바구니 비우기
	@GetMapping("cart_empty")
	public String cart_empty(HttpSession session) throws Exception {
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		
		cartService.cart_empty(mbsp_id);
		
		return "redirect:/cart/cart_list";
	}
	
	
}
