package com.yewmall.basic.admin.product;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.yewmall.basic.admin.category.AdminCategoryService;
import com.yewmall.basic.admin.category.AdminCategoryVo;
import com.yewmall.basic.common.dto.Criteria;
import com.yewmall.basic.common.dto.PageDTO;
import com.yewmall.basic.common.util.FileManagerUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/product/*")
public class AdminProductController {
	
	// DI
	private final AdminProductService adminProductService;
	private final AdminCategoryService adminCategoryService;
	
	
	// 상품 이미지 업로드 경로
	@Value("${file.product.image.dir}")
	private String uploadPath;
	
	// CKEditor 파일 업로드 경로
	@Value("${file.ckdir}")
	private String uploadCKPath;
	
	
	// 상품등록폼
	@GetMapping("pro_insert")
	public void pro_insertForm(Model model) {
		List<AdminCategoryVo> cate_list = adminCategoryService.getFirstCategoryList();
		model.addAttribute("cate_list", cate_list);
	}
	
	// 상품저장
	@PostMapping("pro_insert")
	public String pro_insertOk(ProductVo vo, MultipartFile uploadFile) throws Exception {
		
		// 1) 상품이미지 업로드
		String dateFolder = FileManagerUtils.getDateFolder();
		String saveFileName = FileManagerUtils.uploadFile(uploadPath, dateFolder, uploadFile);
		
		vo.setPro_img(saveFileName);
		vo.setPro_up_folder(dateFolder);
		
		log.info("상품정보 : " + vo);
		
		// 2) 상품정보 DB 저장
		adminProductService.pro_insert(vo);
		
		return "redirect:/admin/product/pro_list";
	}
	
	// CKEditor 상품설명 이미지 업로드
	// HttpServletRequest request : 클라이언트의 요청정보를 가지고 있는 객체.
	// HttpServletResponse response : 서버에서 클라이언트에게 보낼 정보를 응답하는 객체
	// MultipartFile upload : CKeditor의 업로드탭에서 나온 파일첨부버튼(파일선택 : <input type="file" name="upload">)
	@PostMapping("imageupload")
	public void imageupload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
		OutputStream out = null;
		PrintWriter printWriter = null; // 업로드한 이미지정보를 브라우저에게 보내는 용도
		
		try {
			// 1) CKEditor를 이용한 파일 업로드 처리
			String fileName = upload.getOriginalFilename(); // 업로드할 클라이언트 파일 이름
			byte[] bytes = upload.getBytes(); // 업로드할 파일의 바이트 배열
			
			String ckUploadPath = uploadCKPath + fileName; // "C:\\Coding\\Study\\SpringStudy\\upload\\ckeditor\\" + "abc.jpg"
			
			out = new FileOutputStream(ckUploadPath); // "C:\\Coding\\Study\\SpringStudy\\upload\\ckeditor\\abc.jpg" 파일생성 0byte
			
			out.write(bytes); // 스트림 공간에 업로드할 파일의 바이트 배열을 채운 상태
			out.flush(); // 스트림의 버퍼에 남아 있는 데이터를 실제 출력 대상으로 밀어내는(flush) 역할 -> 크기가 채워진 정상파일로 인식
			
			// 2) 업로드한 이미지 파일에 대한 정보를 클라이언트에게 보내는 작업
			printWriter = response.getWriter();
			
			String fileUrl = "/admin/product/display/" + fileName; // 매핑주소/이미지파일명
			
			// CKEditor 4.12에서는 파일정보를 다음과 같이 구성하여 보내야 함.
			// {"filename" : "abc.jpg", "uploaded" : 1, "url" : "/ckupload/abc.jpg"}
			// {"filename" : 변수, "uploaded" : 1, "url" : 변수}
			printWriter.println("{\"filename\" :\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}"); // 스트림에 데이터를 채움.
			printWriter.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			if(printWriter != null) {
				printWriter.close();
			}
		}
	}
	
	// CKEditor에서 업로드 이미지 보여주는 기능
	@GetMapping("display/{fileName}")
	public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) {
		log.info("파일 이미지 : " + fileName);
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = FileManagerUtils.getFile(uploadCKPath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return entity;
	}
	
	// 상품리스트
	@GetMapping("pro_list")
	public void pro_list(Criteria cri, Model model) throws Exception {
		List<ProductVo> pro_list = adminProductService.pro_list(cri);
		
		// 1) 기본코드
//		for(ProductVo vo : pro_list) {
//			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
//		}
		
		// 2) 람다식
		pro_list.forEach(vo -> { // model작업 전 \를 /로 변환함. \\는 이스케이프 문자로 사용되어 두 번 작성
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		int totalCount = adminProductService.getTotalCount(cri);
		
		model.addAttribute("pro_list", pro_list);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 상품리스트에서 사용할 이미지 보여주기.
	@GetMapping("image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		return FileManagerUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
