package com.yewmall.basic.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자 생성
public class EmailDTO {
	
	private String senderMail; // 발신자 메일 주소
	private String senderName; // 발신자 이름
	private String receiverMail; // 수신자 메일 주소
	private String subject; // 메일 제목
	private String message; // 메일 내용
	
	// receiverMail은 사용자가 입력한 데이터를 사용하기 위하여 4개만 셋팅을 해줌.
	public EmailDTO() {
		this.senderMail = "YewMall";
		this.senderName = "YewMallManager";
		this.subject = "YewMall 회원가입인증코드";
		this.message = "메일 인증코드 확인하시고 회원 가입 시 인증코드 입력란에 입력바랍니다.";
	}
	
}
