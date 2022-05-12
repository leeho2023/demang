package org.pro.demang.controller;

import org.pro.demang.model.PostDTO;
import org.pro.demang.model.PostImgDTO;
import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	        @RequestParam(value="p_image", required = false)MultipartFile[] files) {

		try {
			int p_origin = 3; // 리뷰 작성 시 참조할 원게시글 번호값[아직 미정]
			PostDTO dto = new PostDTO(p_origin, p_type, p_writer, p_content); // 생성되기 전 게시글에 들어갈 값을 dto로 먼저 생성
			postService.postInsert( dto ); // 작동
			int p_id = dto.getP_id(); // 생성 된 게시글의 이미지 등록시 참조하기 위해 p_id값을 가져옴
			
			for(int i = 0; i < files.length; i++) {
				PostImgDTO imgDTO = new PostImgDTO(); // 이미지가 들어갈 DTO를 생성
				imgDTO.setI_image(files[i].getBytes()); // DTO안에 이미지를 byte변환하여 넘겨줌
				postService.postInsertImg(p_id, imgDTO.getI_image()); // 작동
				System.out.println(i + "번째 이미지 입력됨");
			}
			System.out.println(dto.getP_id()+"번 게시글로 작성된 거 확인하세요. ~ sjh controller ###########################");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "post/PostInsert";
		}
	
	@GetMapping("thumnail")
	public String thumnail() {
		return "post/thumnail";
	}
}