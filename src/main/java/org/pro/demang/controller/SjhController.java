package org.pro.demang.controller;

import java.io.IOException;

import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SjhController {
	
    @Autowired
	PostService postService;
	
	@PostMapping("/postInsert")
	public String postInsert(
	        @RequestParam("p_content")String p_content,
	        @RequestParam("p_type")String p_type,
	        @RequestParam("p_writer")String p_writer,
	        @RequestParam(value="p_image", required = false)MultipartFile file) {
		try {
			int p_origin = 0;
			if(p_type.equals("R")) {
				p_origin = 2; // 리뷰 작성 시 참조할 원게시글 번호값[아직 미정]
			}
			postService.postInsert(p_origin, p_type, p_writer, p_content);
			postService.postImgInsert(file.getBytes());// 등록 될 글 내용
			} catch (Exception e) {
				e.printStackTrace();
				}
		return "post/PostInsert";
		}
}