package com.yewmall.basic.payinfo;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PayInfoVo {
	private Integer p_id;
	private Long ord_code;
	private String mbsp_id;
	private String paymethod;
	private String payinfo; // 은행/ 계좌번호/ 예금주
	private int p_price;
	private String p_status; // 완납/ 미납
	private Date p_date;
}
