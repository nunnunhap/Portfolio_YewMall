package com.yewmall.basic.admin.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yewmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
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
	
	// 상품수정
	public ProductVo pro_edit(Integer pro_num) {
		return adminProductMapper.pro_edit(pro_num);
	}
	
	// 상품수정 저장
	void pro_edit_ok(ProductVo vo) {
		adminProductMapper.pro_edit_ok(vo);
	}
	
	// 개별 상품 삭제
	void pro_delete(Integer pro_num) {
		adminProductMapper.pro_delete(pro_num);
	}
	
	// 일괄 상품 삭제
	void pro_delete_all(List<Integer> pro_num_arr) {
		List<ProductDTO> pro_delete_list = new ArrayList<>();
		
		for(int i = 0; i < pro_num_arr.size(); i++) {
			ProductDTO productDto = new ProductDTO(pro_num_arr.get(i));
			pro_delete_list.add(productDto);
		}
		
		adminProductMapper.pro_delete_all(pro_delete_list);
	}
	
}
