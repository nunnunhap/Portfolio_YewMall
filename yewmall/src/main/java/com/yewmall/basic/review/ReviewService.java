package com.yewmall.basic.review;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	// DI
	private final ReviewMapper reviewMapper;
	
	
}
