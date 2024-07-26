package com.yewmall.basic.admin.product;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductVo {
	private Integer pro_num;
	private Integer cate_code;// 2차 카테고리 코드
	private String pro_name;
	private int pro_price;
	private int pro_discount;
	private String pro_publisher;
	private String pro_content;
	private String pro_up_folder;
	private String pro_img;
	private int pro_amount;
	private String pro_buy;
	private Date pro_date;
	private Date pro_updatedate;
	private int revcount;
}

