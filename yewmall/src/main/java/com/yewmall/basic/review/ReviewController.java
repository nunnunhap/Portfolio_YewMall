package com.yewmall.basic.review;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/review/*")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

	// DI
	private final ReviewService reviewService;
	
	
	
	
	
	
}
