package com.yewmall.basic.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.yewmall.basic.common.constants.Constants;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	// DI
	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine; // 타임리프 템플릿을 메일 템플릿으로 사용하기 위한 필드 선언
	
	
	// 회원 가입 등 인증용 메일 발송
	public void sendMail(String type, EmailDTO dto, String authcode) {
		
		type = Constants.MAILFOLDERNAME + "/" + type;
		
		// 메일 구성정보 담당(받는 사람, 보내는 사람, 받는 사람 메일 주소, 본문 내용)
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			// 메일 템플릿으로 타임리프 사용목적으로 아래 코드 구성
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo(dto.getReceiverMail()); // 메일 수신자
			mimeMessageHelper.setFrom(new InternetAddress(dto.getSenderMail(), dto.getSenderName()));
			mimeMessageHelper.setSubject(dto.getSubject()); // 메일 제목
			mimeMessageHelper.setText(setContext(authcode, type), true);
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 회원 대상 안내/홍보 메일 발송
	public void sendMail(EmailDTO dto, String[] mbsp_email_arr) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            
			mimeMessageHelper.setTo(mbsp_email_arr); // 메일 수신자
            mimeMessageHelper.setFrom(new InternetAddress(dto.getSenderMail(), dto.getSenderName()));
            mimeMessageHelper.setSubject(dto.getSubject()); // 메일 제목
            mimeMessageHelper.setText(dto.getMessage(), true); // 메일 본문 내용, HTML 여부
			
			// 메일발송기능
			mailSender.send(mimeMessage);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// String code = authcode, String type = email(.html 확장자는 빠진 상태로 들어옴)
	public String setContext(String authcode, String type) {
		Context context = new Context();
		context.setVariable("authcode", authcode);
		
		return templateEngine.process(type, context);
	}
	
}
