package org.pro.demang.controller;

import java.util.List;

import org.pro.demang.model.MemberDTO;
import org.pro.demang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/")
	public String home() {
		
		return "other/footer";
	}
	
	@GetMapping("/fList")
	public String homeNav() {
		
		return "other/fList";
	}

	@GetMapping("/homeNav")
	public String homeNav2(){
		return "other/homeNav";
	}

	@GetMapping("/admindex")
	public String admindex(){
		return "admin/index";
	}
	
	// 친구 목록 불러오기(해당 유저의 회원코드 사용) 
	@PostMapping("/fList")
	public String fList(@RequestParam("follower")int follower, Model model) {
		
		List<MemberDTO> list = memberService.fList(follower);
		

		model.addAttribute("list",list);
		
		
		return "other/fListList";
	}
	
	
}
