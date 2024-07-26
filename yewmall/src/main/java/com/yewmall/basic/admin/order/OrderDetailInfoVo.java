package com.yewmall.basic.admin.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailInfoVo {
	
	private Long ord_code;
	private Integer pro_num;
	private int dt_amount;
	private int dt_price;
	private String pro_name;
	private String pro_up_folder;
	private String pro_img;
}
