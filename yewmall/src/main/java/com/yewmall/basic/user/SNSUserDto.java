package com.yewmall.basic.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SNSUserDto {
	
	private String sns_type;
	private String id;
	private String name;
	private String email;	
	
}
