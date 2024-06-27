package com.yewmall.basic.kakaologin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class KakaoUserInfo {
	
	private Long id;
	private String nickname;
	private String email;
	
}
