package org.pro.demang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pro.demang.model.MemberDTO;
import org.pro.demang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/")
	public String home() {
		return "member/login";
	}
	
	@PostMapping("/signUp")
	public String signUp(MemberDTO dto) {
		
		System.out.println("dto 값 확인 : " + dto);
		
		memberService.memberInsert(dto);
		return "redirect:/";
	}
	
	@GetMapping("/loginMove")
	public String loginMove() {
		return "member/login";
	}
	@GetMapping("/signUp")
	public String goSignUp() {
		return "member/signUp";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/";
	}
	
}
	
