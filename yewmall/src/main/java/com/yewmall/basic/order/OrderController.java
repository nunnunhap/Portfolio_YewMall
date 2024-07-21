package com.yewmall.basic.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yewmall.basic.cart.CartProductVo;
import com.yewmall.basic.cart.CartService;
import com.yewmall.basic.cart.CartVo;
import com.yewmall.basic.common.util.FileManagerUtils;
import com.yewmall.basic.user.UserService;
import com.yewmall.basic.user.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/order/*")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
	
	// DI
	private final OrderService orderService;
	private final CartService cartService;
	private final UserService userService;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
		
	
	@GetMapping("orderinfo")
	public String orderinfo(@ModelAttribute CartProductVo vo, HttpSession session, Model model) throws Exception {
		log.info("구매상품정보 : " + vo);
		
		if(vo.getPro_num() != 0 && vo.getCart_amount() != 0) { // 즉시구매 버튼 클릭 시
			CartProductVo cp_vo = new CartProductVo();
			// cp_vo.setCart_code(null);
			cp_vo.setPro_num(vo.getPro_num());
			cp_vo.setPro_name(vo.getPro_name());
			cp_vo.setPro_up_folder(vo.getPro_up_folder());
			cp_vo.setPro_img(vo.getPro_img());
			cp_vo.setPro_price(vo.getPro_price());
			cp_vo.setCart_amount(vo.getCart_amount());

            List<CartProductVo> cart_list = new ArrayList<>();
            cart_list.add(cp_vo);
            model.addAttribute("cart_list", cart_list);
            
			int total_price = 0;
			for(int i = 0; i < cart_list.size(); i++) {
				total_price += cart_list.get(i).getPro_price() * cart_list.get(i).getCart_amount();
			}
			model.addAttribute("total_price", total_price);
			
		} else { // cart_list에서 '구매하기'버튼 클릭 시
			String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();

			List<CartProductVo> cart_list = cartService.cart_list(mbsp_id);
			cart_list.forEach(d_vo -> d_vo.setPro_up_folder(d_vo.getPro_up_folder().replace("\\", "/")));
			model.addAttribute("cart_list", cart_list);
			
			int total_price = 0;
			for(int i = 0; i < cart_list.size(); i++) {
				total_price += cart_list.get(i).getPro_price() * cart_list.get(i).getCart_amount();
			}
			model.addAttribute("total_price", total_price);
		}
		
		return "/order/orderinfo";
		
	}
	
	// 상품리스트에서 사용할 이미지 보여주기.
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// 주문자와 동일 체크박스
	@GetMapping("ordersame")
	public ResponseEntity<UserVo> ordersame(HttpSession session) throws Exception {
		ResponseEntity<UserVo> entity = null;
		
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		entity = new ResponseEntity<UserVo> (userService.login(mbsp_id), HttpStatus.OK);
		
		return entity;
	}
	
	// 무통장 입금
	@PostMapping("nobank_ordersave")
	public String nobank_ordersave(OrderVo vo, String pay_nobank, String pay_nobank_user, HttpSession session) throws Exception {
		log.info("주문정보: " + vo);
		log.info("입금은행: " + pay_nobank);
		log.info(("예금주:" + pay_nobank_user));
		
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		String payinfo = pay_nobank + "/" + pay_nobank_user;
		
		orderService.order_process(vo, mbsp_id, "무통장", "미납", payinfo);
		
		return "redirect:/order/nobank_ordercomplete";
	}
	
	// 무통장 주문완료
	@GetMapping("nobank_ordercomplete")
	public void nobank_ordercomplete() throws Exception {
		
	}
	
	
}
