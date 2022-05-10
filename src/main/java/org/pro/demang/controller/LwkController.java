package org.pro.demang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pro.demang.model.MemberDTO;
import org.pro.demang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LwkController {
	@Autowired
	private MemberService MemberService;
	
	@PostMapping("/login")
	public String login(MemberDTO dto, HttpServletRequest request,RedirectAttributes rttr) {
		
		String result = MemberService.login(dto);
		if(result.equals("Success")) {
		HttpSession session = request.getSession();
		session.setAttribute("login",dto.getM_id());
		System.out.println(session.getAttribute("login"));
		return "redirect:/";
	}else {
		rttr.addFlashAttribute("msg", false);
		return "redirect:/loginMove";
		
	}
}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/";
	}
	@GetMapping("/upDate")
	public String upDate() {
		
		return "member/upDate";
	}
//	@GetMapping("/memberUpdate")
//	public String memberUpdate() {
//		
//		return "member/memberUpdate";
//	}
	@GetMapping("/view")
	public String view() {
		
		return "member/view";
	}
	
	@GetMapping("/memberList")
	public String memberList() {
		
		return "member/memberList";
	}
	@RequestMapping("/memberUpdate")
		public String hello(Model model) {
		System.out.println("안녕하세요");
		model.addAttribute("message", "hello.html입니다.!");
		return "member/memberUpdate";
	}
	
	@GetMapping("/memberRead")
	public String memberRead(Model model, HttpSession session) {
//		System.out.println(session.getAttribute("login"));
		MemberDTO dto=MemberService.memberRead("1");
		System.out.println(dto);
		model.addAttribute("dto",dto);
		return "member/memberRead";
	}
//	@GetMapping("/memberRead")
//	public String memberRead(@RequestParam("m_id")String m_id, Model model) {
//	 MemberDTO dto=	MemberService.memberRead(m_id);
//	 model.addAttribute("dto",dto);
//		return "member/memberRead";
//	}
	}



