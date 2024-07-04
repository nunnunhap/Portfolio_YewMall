package com.yewmall.basic.admin.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminCategoryVo {
	
	private Integer cate_code;
	private int cate_precode; // 상위코드
    private String cate_name;
}
