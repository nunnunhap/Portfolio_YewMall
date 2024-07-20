package com.yewmall.basic.qna;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QnaVo {
	
	private Long qno;
	private String mbsp_id;
	private int pro_num;
	private String qcontent;
    private Date qregdate;	
	private String admin_id;
	private String reply_content;
	
}
