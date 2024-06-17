package com.yewmall.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan(basePackages = "com.yewmall.basic.**")
public class YewmallApplication {

	public static void main(String[] args) {
		SpringApplication.run(YewmallApplication.class, args);
	}

}
