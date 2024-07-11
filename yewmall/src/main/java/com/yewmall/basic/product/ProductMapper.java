package com.yewmall.basic.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yewmall.basic.admin.product.ProductVo;
import com.yewmall.basic.common.dto.Criteria;

public interface ProductMapper {

	// 상품 리스트
	List<ProductVo> pro_list(@Param("cate_code") int cate_code, @Param("cri") Criteria cri);
	
	// 총 데이터 갯수(페이징)
	int getCountProductByCategory(int cate_code);
	
	
}
