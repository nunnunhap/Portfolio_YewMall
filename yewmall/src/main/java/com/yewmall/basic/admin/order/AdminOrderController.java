package com.yewmall.basic.admin.order;

import java.util.HashMap;
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

import com.yewmall.basic.common.constants.Constants;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.common.util.FileManagerUtils;
import com.yewmall.basic.order.OrderVo;
import com.yewmall.basic.payinfo.PayInfoService;
import com.yewmall.basic.payinfo.PayInfoVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/order/*")
@Slf4j
@RequiredArgsConstructor
public class AdminOrderController {
	
	// DI
	private final AdminOrderService adminOrderService;
	private final PayInfoService payInfoService;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	
	// 주문목록
	@GetMapping("order_list")
	public void order_list(Criteria cri, @ModelAttribute("start_date") String start_date, @ModelAttribute("end_date") String end_date, Model model) throws Exception {
		cri.setAmount(Constants.ADMIN_ORDERLIST_AMOUNT);
		
		List<OrderVo> order_list = adminOrderService.order_list(cri, start_date, end_date);
		int totalCount = adminOrderService.getTotalCount(cri, start_date, end_date);
		
		model.addAttribute("order_list", order_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 주문목록 내 주문삭제
	@PostMapping("ord_delete")
	public ResponseEntity<String> ord_delete(Long ord_code) throws Exception {
		ResponseEntity<String> entity = null;
		
		adminOrderService.ord_delete(ord_code);
		
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 주문상세보기(modal)
	@GetMapping("order_detail_info")
	public ResponseEntity<Map<String, Object>> order_detail_info(Long ord_code) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<>();
		
		// 1) 주문자(수령인) 정보
		OrderVo vo = adminOrderService.order_info(ord_code);
		map.put("ord_basic", vo);
		
		// 2) 주문상품정보
		List<OrderDetailInfoVo> ord_product_list = adminOrderService.order_detail_info(ord_code);
		ord_product_list.forEach(ord_pro -> {
			ord_pro.setPro_up_folder(ord_pro.getPro_up_folder().replace("\\", "/"));
		});
		map.put("ord_pro_list", ord_product_list);
		
		// 3) 결제정보
		PayInfoVo p_vo = payInfoService.ord_pay_info(ord_code);
		map.put("payinfo", p_vo);
		
		entity = new ResponseEntity<Map<String,Object>> (map, HttpStatus.OK);
		return entity;
	}
	
	// 주문상세정보에서 주문상품 이미지 출력
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// modal 내 상품 개별삭제
	@GetMapping("order_product_delete")
	public ResponseEntity<String> order_product_delete(Long ord_code, Integer pro_num) throws Exception {
		ResponseEntity<String> entity = null;
		
		adminOrderService.order_product_delete(ord_code, pro_num);
		
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		return entity;
	} 
	
	// modal 내 정보 수정
	@PostMapping("order_basic_modify")
	public ResponseEntity<String> order_basic_modify(OrderVo vo) throws Exception {
		ResponseEntity<String> entity = null;
		
		adminOrderService.order_basic_modify(vo);
		
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		return entity;
	}
	
	
	
}
