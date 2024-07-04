package com.yewmall.basic.admin.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yewmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductService {
	
	// DI
	private final AdminProductMapper adminProductMapper;
	
	
	// 판매상품등록
	public void pro_insert(ProductVo vo) {
		adminProductMapper.pro_insert(vo);
	}
	
	// 상품목록
	public List<ProductVo> pro_list(Criteria cri) {
		return adminProductMapper.pro_list(cri);
	}
	
	// 전체 데이터 개수(페이징)
	public int getTotalCount(Criteria cri) {
		return adminProductMapper.getTotalCount(cri);
	}
	
	
}
