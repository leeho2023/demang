package org.pro.demang.controller;

import javax.servlet.http.HttpSession;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.service.ChatService;
import org.pro.demang.service.MemberService;
import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KysController {
	@Autowired private MemberService memberService;
	@Autowired private PostService postService;
	@Autowired private ChatService chatService;
	@Autowired private MainMapper mapper;
	
    @GetMapping("/chat")
    String chat( HttpSession session, @RequestParam("to") int listener ){
		if( session.getAttribute("login") == null ) return "redirect:/loginMove";// 비회원인 경우 로그인하러 가기
    	return "chat";
    }
    
    @PostMapping("/chat/send")
    @ResponseBody
    String chat_send( HttpSession ss, @RequestParam("to") String listener, @RequestParam("h_content") String h_content ) {
    	System.out.println( "kys controller ~ "+listener+"에게: "+h_content );
    	chatService.chatSend( loginId(ss), listener, h_content );
    	return "kys controller ~ !!!!!!!!!!!!!!!!!!!!!!!";
    }
    
    //// 현재 로그인한 회원 번호(문자열로) 가져오기
    private String loginId( HttpSession session ) {
    	return session.getAttribute("login")+"";
    }
}
