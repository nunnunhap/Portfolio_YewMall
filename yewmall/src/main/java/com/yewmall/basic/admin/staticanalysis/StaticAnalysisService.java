package com.yewmall.basic.admin.staticanalysis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaticAnalysisService {
	
	// DI
	private final StaticAnalysisMapper staticAnalysisMapper;
	
	
	// 월별 매출 데이터 조회
	List<Map<String, Object>> monthlySalesStatusByTopCategory(String ord_date) {
		return staticAnalysisMapper.monthlySalesStatusByTopCategory(ord_date);
	}
	
	
	
	
	
	
}
