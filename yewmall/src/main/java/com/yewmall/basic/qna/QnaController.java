package com.yewmall.basic.qna;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.review.ReviewVo;
import com.yewmall.basic.user.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/qna/*")
@Slf4j
@RequiredArgsConstructor
public class QnaController {
	
	// DI
	private final QnaService qnaService;
	
	
	// Rest API qna목록과 페이징
	@GetMapping("/qnalist/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> qnalist(@PathVariable("pro_num") int pro_num, @PathVariable("page") int page) throws Exception {
		log.info("상품번호 : " + pro_num);
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<>();
		
		// 1) 후기목록
		Criteria cri = new Criteria();
		cri.setAmount(5);
		cri.setPageNum(page);
		
		List<QnaVo> qna_list = qnaService.qna_list(pro_num, cri);
		
		// 2) 페이징 정보
		int qnacount = qnaService.getCountQnaByPro_num(pro_num);
		PageDTO qna_pageMaker = new PageDTO(cri, qnacount);
		
		map.put("qna_list", qna_list);
		map.put("qna_pageMaker", qna_pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>> (map, HttpStatus.OK);
		
		return entity;
	}
	
	// Q&A 작성 후 저장
	@PostMapping(value = "qna_save", consumes = {"application/json"}, produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> qna_save(@RequestBody QnaVo vo, HttpSession session) throws Exception {
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		log.info("이름" + mbsp_id);
		
		vo.setMbsp_id(mbsp_id);
		
		log.info("상품문의데이터 : " + vo);
		
		qnaService.qna_save(vo);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// Q&A 삭제
	@DeleteMapping("qna_delete/{qno}")
	public ResponseEntity<String> qna_delete(@PathVariable("qno") Long qno) throws Exception {
		log.info("문의코드 : " + qno);
		qnaService.qna_delete(qno);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// Q&A 수정 폼
	@GetMapping("qna_modify/{qno}")
	public ResponseEntity<QnaVo> review_modify(@PathVariable("qno") Long qno) throws Exception {

		ResponseEntity<QnaVo> entity = null;
		entity = new ResponseEntity<QnaVo> (qnaService.qna_modify(qno), HttpStatus.OK);
		
		return entity;
	}
	
	// Q&A 수정 저장
	@PutMapping("qna_modify")
	public ResponseEntity<String> review_modify(@RequestBody QnaVo vo) throws Exception {
		log.info("업데이트 : " + vo);
		qnaService.qna_update(vo);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
}
