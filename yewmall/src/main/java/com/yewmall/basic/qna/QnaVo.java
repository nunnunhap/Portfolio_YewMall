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
	private String admin_id;
	private int pro_num;
	private String qcontent;
	private String reply_content;
	private char anscheck;
	private Date qregdate;
	private Date answer_date;

	public QnaVo(Long qno) {
	}

	public QnaVo(Long qno, String mbsp_id, String admin_id, int pro_num, String qcontent, String reply_content,
			char anscheck, Date qregdate, Date answer_date) {
		super();
		this.qno = qno;
		this.mbsp_id = mbsp_id;
		this.admin_id = admin_id;
		this.pro_num = pro_num;
		this.qcontent = qcontent;
		this.reply_content = reply_content;
		this.anscheck = anscheck;
		this.qregdate = qregdate;
		this.answer_date = answer_date;
	}
	
	
	
}
