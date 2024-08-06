package com.yewmall.basic.admin.user;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailMngVo {
	
	private Integer m_idx;
	private String m_title;
	private String m_explan;
	private String m_content;
	private String m_gubun;
	private Date reg_date;
	private String admin_id;
	private int m_sendcount;
}
