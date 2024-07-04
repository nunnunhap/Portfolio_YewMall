package com.yewmall.basic.admin.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/category/*")
@RequiredArgsConstructor
public class AdminCategoryController {
	
	// DI
	private final AdminCategoryService adminCategoryService;
	
	
	// 2차 카테고리 목록
	@GetMapping("/secondcategory/{cate_precode}")
	public ResponseEntity<List<AdminCategoryVo>> getSecondCategoryList(@PathVariable("cate_precode") int cate_precode) throws Exception {
		log.info("1차 카테고리코드 : " + cate_precode);
		
		ResponseEntity<List<AdminCategoryVo>> entity = null;
		entity = new ResponseEntity<List<AdminCategoryVo>>(adminCategoryService.getSecondCategoryList(cate_precode), HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
}
