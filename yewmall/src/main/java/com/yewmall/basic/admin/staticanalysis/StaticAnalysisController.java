package com.yewmall.basic.admin.staticanalysis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/staticanalysis/*")
public class StaticAnalysisController {
	
	// DI
	private final StaticAnalysisService staticAnalysisService;
	
	
	
	
	
}
