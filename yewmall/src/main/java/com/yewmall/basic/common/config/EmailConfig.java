package com.yewmall.basic.common.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@PropertySource("classpath:/mail/email.properties")
public class EmailConfig {
	
	public EmailConfig() throws Exception {
		// 생성자가 호출되었단건 객체가 생성되었단 뜻. 이 클래스에 대한 빈을 알아서 생성해줌.
		log.info("EmailConfig.java constructor called ...");
	}
	
	// email.properties 파일의 설정정보를 참조
	
	// 사용 안함 (시작)
	@Value("${spring.mail.transport.protocol}")
	private String protocol; // smtp
	@Value("${spring.mail.debug}")
	private boolean debug;
	// 사용 안함 (끝)
	
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean auth;
	
	@Value("${spring.mail.properties.mail.starttls.enable}")
	private boolean starttls;
	
	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.port}")
	private int port;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Value("${spring.mail.default-encoding}")
	private String encoding;
	
	@Bean
	public JavaMailSender javaMailSender() {
		// JavaMailSenderImpl 클래스가 어떤 메일 서버를 이용하여 메일 발송할지 서버에 대한 정보를 구성하는 작업
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		// email.properties의 설정을 Emailconfig class에 저장하고 그걸 properties라는 형태로 관리
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.starttls.enable", starttls);
		
		/* 운영체제에 따라 아래의 코드로 대체해야 할 수 있음.
		 * properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
		 * properties.setProperty("mail.smtp.ssl.enable", "true"); // ssl 사용
		 */
		
		mailSender.setHost(host);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		mailSender.setPort(port);
		mailSender.setJavaMailProperties(properties);
		mailSender.setDefaultEncoding(encoding);
		
		log.info("메일서버 : " + host);
		
		return mailSender;
	}
	
	
}
