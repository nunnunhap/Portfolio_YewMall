package com.yewmall.basic.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDetailVo {
	
	private Long ord_code;
	private int pro_num;
	private int dt_amount;
	private int dt_price;
	
}
