package com.yewmall.basic.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/order/*")
@Slf4j
@RequiredArgsConstructor
public class CartController {
	
	// DI
	private final CartService cartService;
	
	
	
	
	
	
	
}
