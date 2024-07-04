package com.yewmall.basic.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {

	// DI
	private final AdminCategoryMapper adminCategoryMapper;
	
	
	// 1차 카테고리 목록
	public List<AdminCategoryVo> getFirstCategoryList() {
		return adminCategoryMapper.getFirstCategoryList();
	}
	
	// 2차 카테고리 목록
	public List<AdminCategoryVo> getSecondCategoryList(int cate_precode) {
		return adminCategoryMapper.getSecondCategoryList(cate_precode);
	}
	
	
}
