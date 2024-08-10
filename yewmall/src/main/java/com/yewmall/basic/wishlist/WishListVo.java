package com.yewmall.basic.wishlist;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WishListVo {
	
	private Long wish_idx;
	private Integer pro_num;
    private String mbsp_id;
    private Date wish_regdate;
}
