package org.pro.demang.controller;

import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WriteController {
	
	@Autowired
	PostService postService;
		
	@GetMapping("/")
	public String index() {
		return "/test2";
	}
	
	@GetMapping("/postInsert")
	public String postInsertRoute() {
		System.out.println("게시글 입력페이지로 전송");
		return "/PostInsert";
	}
	
	@GetMapping("/postView")
	public String postViewRoute() {
		System.out.println("게시글 상세보기 페이지로 전송");
		return "/PostView";
	}
	
	@PostMapping("/postInsert")
	   public String postInsert(
			   @RequestParam(value="p_image", required=false) MultipartFile file,
	         @RequestParam("p_content")String p_content) {
	      try {
	    	  System.out.println("postInsert() 컨트롤러 시작");
	    	  System.out.println("입력값 확인 : 내용 = " + p_content + " 파일 = " + file);
	         postService.postInsert(p_content, file.getBytes());
	        System.out.println("postInsert() 컨트롤러 입력완료");
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return "/PostInsert";
	   }
	
}
