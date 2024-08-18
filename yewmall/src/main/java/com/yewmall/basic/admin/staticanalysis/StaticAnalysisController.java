package com.yewmall.basic.admin.staticanalysis;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/staticanalysis/*")
public class StaticAnalysisController {
	
	// DI
	private final StaticAnalysisService staticAnalysisService;
	
	
	// 1차 카테고리 별 매출현황
	@GetMapping("cate_precode_orderStats")
	public void cate_precode_orderStats(Model model) throws Exception {
		
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		
		model.addAttribute("year", year);
		model.addAttribute("month", month);
	}
	
	// 월별 매출 데이터 조회
	@GetMapping("monthlySalesStatusByTopCategory")
	@ResponseBody
	public List<Map<String, Object>> monthlySalesStatusByTopCategory(int year, int month) throws Exception {
		String ord_date = String.format("%s/%s", year, (month < 10 ? "0" + String.valueOf(month) : month));
		log.info("선택일 : " + ord_date);
		
		List<Map<String, Object>> listObjMap = staticAnalysisService.monthlySalesStatusByTopCategory(ord_date);
		
		return listObjMap;
	}
	
	
	
	
	// 2차 카테고리 별 매출현황
	@GetMapping("cate_code_orderStats")
	public void cate_code_orderStats(Model model) throws Exception {
		
	}
	
	
	
	
	
	
	
	
	
}
