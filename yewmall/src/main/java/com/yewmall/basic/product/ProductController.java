package com.yewmall.basic.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yewmall.basic.admin.product.ProductVo;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.common.util.FileManagerUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product/*")
public class ProductController {
	
	// DI
	private final ProductService productService;
	
	
	// 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	@Value("${file.ckdir}")
	private String upladCKPath;
	
	
	// 상품목록
	@GetMapping("pro_list")
	public void pro_list(@ModelAttribute("cate_code")int cate_code, @ModelAttribute("cate_name") String cate_name, Model model, Criteria cri) throws Exception {
		log.info("2차 카테고리 코드 : " + cate_code);
		log.info("2차 카테고리 이름 : " + cate_name);
		
		cri.setAmount(9);
		
		List<ProductVo> pro_list = productService.pro_list(cate_code, cri);
		// Model작업 전, \를 /로 변환
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		int totalCount = productService.getCountProductByCategory(cate_code);
		log.info("전체 데이터 수 : " + totalCount);
		
		model.addAttribute("pro_list", pro_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 상품목록에서 사용할 이미지
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	
	
	
}
