package com.yewmall.basic.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yewmall.basic.admin.product.ProductVo;
import com.yewmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	// DI
	private final ProductMapper productMapper;
	
	
	// 상품 리스트
	List<ProductVo> pro_list(Integer cate_code, Criteria cri) {
		return productMapper.pro_list(cate_code, cri);
	}
	
	// 총 데이터 갯수(페이징)
	int getCountProductByCategory(Integer cate_code) {
		return productMapper.getCountProductByCategory(cate_code);
	}
	
	// 상품 팝업 및 상세설명 (모달상자)
	ProductVo pro_info(int pro_num) {
		return productMapper.pro_info(pro_num);
	}
	
	// 메인 페이지 : 최신 상품 순서
	public List<ProductVo> main_latestproduct(int amount) {
		return productMapper.main_latestproduct(amount);
	}
	
	// 메인 페이지 : 리뷰 많은 순서
	public List<ProductVo> main_reviewproduct(int amount) {
		return productMapper.main_reviewproduct(amount);
	}

	
	
	
}
