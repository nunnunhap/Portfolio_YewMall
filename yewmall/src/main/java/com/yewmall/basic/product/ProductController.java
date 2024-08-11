package com.yewmall.basic.product;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.yewmall.basic.admin.product.ProductVo;
import com.yewmall.basic.common.constants.Constants;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.common.util.FileManagerUtils;
import com.yewmall.basic.order.OrderVo;
import com.yewmall.basic.review.ReviewService;
import com.yewmall.basic.user.UserVo;
import com.yewmall.basic.wishlist.WishListService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {
	
	// DI
	private final ProductService productService;
	private final ReviewService reviewService;
	private final WishListService wishListService;
	
	
	// 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	@Value("${file.ckdir}")
	private String upladCKPath;
	
	
	// 상품목록 : 1) 전체상품 2) 카테고리별 상품목록
	@GetMapping("pro_list") // @ModelAttribute : 파라미터값이 필수가 아님. @RequestParam은 기본설정이 필수로, 원치 않을 시 (required = false) 코드 추가
	public void pro_list(@RequestParam(required = false) Integer cate_code, @RequestParam(required = false) String cate_name, Model model, Criteria cri) throws Exception {
		log.info("2차 카테고리 코드 : " + cate_code);
		log.info("2차 카테고리 이름 : " + cate_name);
		
		cri.setAmount(Constants.CUSTOMER_PROLIST_AMOUNT);
		
		List<ProductVo> pro_list = productService.pro_list(cate_code, cri);
		// Model작업 전, \를 /로 변환
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		int totalCount = productService.getCountProductByCategory(cate_code);
		log.info("전체 데이터 수 : " + totalCount);
		
		model.addAttribute("pro_list", pro_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		model.addAttribute("cate_code", cate_code);
		model.addAttribute("cate_name", cate_name);

	}
	
	// 상품목록에서 사용할 이미지
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// 상품 팝업 및 상세설명 (모달상자)
	@GetMapping("pro_info")
	public void pro_info(int pro_num, Model model) throws Exception {
		log.info("상품코드 : " + pro_num);
		
		// DB 연동
		ProductVo vo = productService.pro_info(pro_num);
		vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		
		model.addAttribute("product", vo);
	}
	
	// 상품 상세설명
	@GetMapping("pro_detail")
	public void pro_detail(int pro_num, @ModelAttribute("cate_name") String cate_name, HttpSession session, Model model) throws Exception {
		// log.info("상품코드 : " + pro_num);
		// log.info("카테고리 번호 : " + cate_name);
		
		// DB연동
		ProductVo vo = productService.pro_info(pro_num);
		vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		
		int revcount = reviewService.getCountReviewByPro_num(pro_num);
		
		// 상품 상세정보 model
		model.addAttribute("product", vo);
		model.addAttribute("revcount", revcount);
		
		// log.info("세션 정보 : " + session.getAttribute("login_status"));
		
		// 위시리스트 정보 가져오기
		if(session.getAttribute("login_status") != null) {
			String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
			Long wish_idx = wishListService.getWish(mbsp_id, pro_num);
			model.addAttribute("wish_idx", wish_idx);
		}
	}
	
	
}
