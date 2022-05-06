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
public class LwkController {
	@Autowired
	private MemberService MemberService;
	
	@PostMapping("/login")
	public String login(MemberDTO dto, HttpServletRequest request,RedirectAttributes rttr) {
		
		String result = MemberService.login(dto);
		if(result.equals("Success")) {
		HttpSession session = request.getSession();
		session.setAttribute("login",dto.getM_id());
		return "post/feed";
	}else {
		rttr.addFlashAttribute("msg", false);
		return "redirect:/";
		
	}
}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/";
	}
}

