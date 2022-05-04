package org.pro.demang.controller;

import javax.servlet.http.HttpSession;

import org.pro.demang.model.MemberDTO_ext;
import org.pro.demang.model.PostDTO;
import org.pro.demang.service.YoungsunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class YoungsunController {
	@Autowired private YoungsunService service;
	
	//// 개인 피드
	@GetMapping("/feed")
	public String feed( Model model, HttpSession session ) {
		String testLogin = "1";// ???test 1번 회원으로 로그인했다 치고 시작하기
		session.setAttribute("login", testLogin);
		model.addAttribute(// 현재 로그인한 회원의 팔로들의 글 목록(번호만)
				"PostList", 
				service.getPostList_followee( testLogin )
				);
		model.addAttribute("testdto", service.getPost( "5" ));
		return "feed/feed";
	}
	
	//// 게시글 한 개
	@PostMapping("/postItem")
	public String feedItem( @RequestParam("no") String no, Model model ) {
		//// 번호로 게시글 찾아서 DTO 받아오기
		PostDTO dto = service.getPost( no );
		model.addAttribute("dto", dto);
		//// 게시글 작성자 정보
		model.addAttribute(
				"writer",
				service.getMember_no( dto.getP_writer() )
				);
		//// 게시글의 댓글 정보
		model.addAttribute(
				"commentList",
				service.getCommentList( no )
				);
		return "post/post";
	}
	
	//// 개인 페이지
	@GetMapping("/vip")
	public String profile( @RequestParam("p") String code, Model model ) {
		//// 식별코드로 회원정보 찾기
		MemberDTO_ext dto = service.getMember_ext(code);
		//// 찾는 회원이 없는 경우 회원 없다는 페이지로 이동
		if( dto == null ) return "profile_noMember";
		model.addAttribute(// 현재 로그인한 회원의 팔로들의 글 목록(번호만)
				"PostList", 
				service.getPostList_writer( dto.getMember().getM_id() )
				);
		//// 회원정보와 함께 회원정보 페이지로
		model.addAttribute("dto", dto);
		return "feed/profile";
	}

}
