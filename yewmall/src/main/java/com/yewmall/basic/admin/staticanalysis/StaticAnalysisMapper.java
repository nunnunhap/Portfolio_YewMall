package com.yewmall.basic.admin.staticanalysis;

import java.util.List;
import java.util.Map;

public interface StaticAnalysisMapper {
	
	// 월별 매출 데이터 조회
	List<Map<String, Object>> monthlySalesStatusByTopCategory(String ord_date);
	
	
	
	
	
	
	
}
