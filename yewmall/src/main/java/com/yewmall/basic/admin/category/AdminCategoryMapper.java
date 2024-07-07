package com.yewmall.basic.admin.category;

import java.util.List;

public interface AdminCategoryMapper {

	// 1차 카테고리 목록
	List<AdminCategoryVo> getFirstCategoryList();
	
	// 2차 카테고리 목록
	List<AdminCategoryVo> getSecondCategoryList(int cate_precode);
	
	// 2차 카테고리 정보를 이용한 1차 카테고리 정보
	AdminCategoryVo getFirstCategoryBySecondCategory(Integer cate_code);
	
}
