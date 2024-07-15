package com.yewmall.basic.cart;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartVo {
	
	private Long cart_code;
    private int pro_num;
    private String mbsp_id;
    private int cart_amount;
    private Date cart_date; // Date, LocalDate, Calendar
	
}
