package org.pro.demang.controller;

import java.util.List;

import org.pro.demang.model.CommentDTO;
import org.pro.demang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
	
	@Autowired CommentService commentService;
	
	@PostMapping("/CommentShow")
	@ResponseBody
	public List<CommentDTO> commentShow(@RequestParam("p_id")String p_id){
		
		System.out.println("댓글 보기 Controller 작동시작");
		List<CommentDTO> list = commentService.commentShow(p_id);
		System.out.println("댓글 보기 Controller 작동 끝");
		return list;
		
	}
	
	@PostMapping("/CommentInsert")
	@ResponseBody
	public String commentInsert(CommentDTO dto) {
		System.out.println("댓글 작성 Controller 시작");
		commentService.commentInsert(dto);
		System.out.println("댓글 작성 Controller 끝");
		return "OK";
	}
	
}
