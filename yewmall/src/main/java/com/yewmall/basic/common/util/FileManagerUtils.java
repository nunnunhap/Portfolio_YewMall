package com.yewmall.basic.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;


//@Component // 스프링에서 클래스를 자동관리.
public class FileManagerUtils {

	// 기능? 현재폴더를 운영체제에 맞게 문자열로 반환.
	public static String getDateFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜포맷형식
		Date date = new Date(); // 오늘 날짜정보
		
		String str = sdf.format(date); // "2024-07-03" 폴더명 문자열
		
		/* File.separator : 이 코드를 실행하는 운영체제별로 파일의 경로구분자를 리턴.
		 - 유닉스,맥, 리눅스 구분자 : /   예>"2024-07-03"   ->  "2024/07/03"
		 - 윈도우즈 구분자: \           예>"2024-07-03"   ->  "2024\07\03"
		 */
		
		return str.replace("-", File.separator);
	}
	
	
	// 기능? 파일 업로드
	/*
	  String uploadFolder : 업로드 폴더명.  C:\\Coding\\Study\\SpringStudy\\upload\\pds
	  String dateFolder : 날짜폴더명.      "2024\\07\\03"
	  MultipartFile uploadFile : 클라이언에서 전송한 파일
	  
	  중복되지 않도록 파일 업로드하고 그 이름까지 받아옴. 
	 */
	public static String uploadFile(String uploadFolder, String dateFolder, MultipartFile uploadFile) {
		
		String realUploadFileName = ""; // 실제 업로드한 파일명.
		
		// File클래스 : JDK제공. 파일과폴더관련 기능을 제공.
		/*
		  File file = new File(파일 또는 폴더정보구성);  file.명령어(속성과메서드)
		  - 파일또는 폴더 존재여부확인
		  - 존재하지 않으면 파일또는 폴더 생성
		  - 존재하면 파일또는 폴더 속성확인
		 */
		
		// 업로드 할 폴더 file객체.
		// File클래스 : 파일/폴더 정보 참조하여 관련 기능 사용
		File file = new File(uploadFolder, dateFolder);
		
		// "2024/07/03" 폴더가 존재하지 않으면, 폴더생성
		// 새로운 날짜에 첫번째 파일업로드가 진행이되면, 폴더생성되고, 두번째 파일업로드부터는 폴더가 생성되지않게 된다.
		if(file.exists() == false) {
			file.mkdirs(); // .mkdir()은 폴더 한 개 생성, .mkdirs()는 폴더 전부라서 보통 .mkdirs() 사용
		}
		
		// 클라이언트에서 보낸 파일명
		String clientFileName = uploadFile.getOriginalFilename(); // abc.png
		UUID uuid = UUID.randomUUID(); // 2f48f241-9d64-4d16-bf56-70b9d4e0e79a
		
		// 2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
		realUploadFileName = uuid.toString() + "_" + clientFileName;
		
		//예외처리작업
		try {
			File saveFile = new File(file, realUploadFileName); // 아직 실제 파일 생성 전
			uploadFile.transferTo(saveFile); // 파일복사(원본), transferTo() 덕분에 saveFile 실존
			
			// Thumbnail 작업(원본파일에서 해상도크기를 줄여 Thumbnail 생성)
			if(checkImageType(saveFile)) {
				// Thumbnail 파일명 : s_2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png 생성
				// thumbnailFile객체 : "C:/Coding/Study/SpringStudy/upload/pds/2024/05/16" "s_2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png"
				File thumbnailFile = new File(file, "s_" + realUploadFileName);
				
				// saveFile객체 : 업로드 된 파일정보
				// bo_img : saveFile 정보를 가지고 있으나 실존하지 않음.
				BufferedImage bo_img = ImageIO.read(saveFile);
				double ratio = 3;
				int width = (int) (bo_img.getWidth() / ratio);
				int height = (int) (bo_img.getHeight() / ratio);
				
				// saveFile(실존파일)을 size()를 활용하여 thumbnailFile로
				Thumbnails.of(saveFile)
						  .size(width, height)
						  .toFile(thumbnailFile);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return realUploadFileName;  // 실제 업로드되는 파일명 2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
	}

	// 기능? 업로드파일의 MIME타입 확인. 즉 이미지파일 또는 일반파일 여부를 체크
	public static boolean checkImageType(File saveFile) {
		
		boolean isImageType = false;
		
		try {
			// MIME : text/html, text/plain, image/jpeg, image/jpg, image/png ...... 언제나 image가 먼저 작성됨.
			// 클라이언트에서 전송한 파일의 MIME정보 추출
			// toPath() : 파일의 MIME 타입을 확인하는 데 사용
			String contentType = Files.probeContentType(saveFile.toPath());
			
			// contentType변수의 내용이 "image"라는 문자열로 시작여부 boolean값 반환
			isImageType = contentType.startsWith("image");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return isImageType;
	}
	
	
	// 기능? 이미지 파일을 웹브라우저 화면에 보이는 작업.
	// <img src="abc.gif"> : 정적 웹페이지 꾸미는 용도
	// <img src="매핑주소"> : 매핑주소를 통한 서버측에서 받아오는 바이트배열을 이용하여, 브라우저가 이미지를 표시한다.
	/* 파일업로드되는 폴더가 프로젝트 외부에 존재하며, 보안적인 이슈가 있으므로, 업로드 파일들을 바이트배열로 읽어서 클라이언트로 전송
	 * String uploadPath : 서버 업로드폴더 예> "C:/Coding/Study/SpringStudy/upload/pds"
	 * String fileName : 이미지파일명(날짜폴더명 포함)
	 */
	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception {
		ResponseEntity<byte[]> entity = null;
		
		File file = new File(uploadPath, fileName);
		
		if(!file.exists()) {
			return entity;
		}
		
		HttpHeaders headers = new HttpHeaders();
		// Files.probeContentType(file.toPath()) : MIME TYPE 정보  예> image/jpeg
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		
		entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		
		return entity;
	}
	
	// 기능? 이미지파일 삭제
	/*
	 * String uploadPath : 서버업로드 폴더
	 * String dateFolderName : 날짜폴더명
	 * String fileName : 파일명 (날짜폴더명 포함)
	 */
	public static void delete(String uploadPath, String dateFolderName, String fileName, String type) {
		
		// 1) fileName.substring(2) s_를 빼고 원본파일명 2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
		File file1 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName.substring(2)).replace('\\', File.separatorChar));
		if(file1.exists()) {
			file1.delete();
		}

		if(type.equals("image")) {
			// 2) 리눅스는 /로 바꿔주고 윈도우면 \로 바꿔줌.
			File file2 = new File((uploadPath + "\\" + dateFolderName + "\\" + fileName).replace('\\', File.separatorChar));
			if(file2.exists()) {
				file2.delete();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
