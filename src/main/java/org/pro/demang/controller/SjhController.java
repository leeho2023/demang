package org.pro.demang.controller;

import java.io.IOException;

import org.pro.demang.model.PostDTO;
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
	        @RequestParam("p_writer")int p_writer,
	        @RequestParam(value="p_image", required = false)MultipartFile file) {
		try {
			int p_origin = 1;// 리뷰 작성 시 참조할 원게시글 번호값[아직 미정]
			PostDTO dto = new PostDTO(p_origin, p_type, p_writer, p_content);
			postService.postInsert( dto );
			System.out.println(dto.getP_id()+"번 게시글로 작성된 거 확인하세요. ~ sjh controller ###########################");
//			postService.postImgInsert(file.getBytes());// 등록 될 글 내용
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "post/PostInsert";
		}
}