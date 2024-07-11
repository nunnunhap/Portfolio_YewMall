package com.yewmall.basic.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/order/*")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
	
	// DI
	private final OrderService orderService;
	
	
	
	
	
	
	
}
