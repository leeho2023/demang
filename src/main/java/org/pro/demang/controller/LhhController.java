package org.pro.demang.controller;

import java.util.List;

import org.pro.demang.model.MemberDTO;
import org.pro.demang.service.MemberService;
import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LhhController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PostService postService;

    @GetMapping("/")
	public String home() {
		
		return "other/homeNav";
	}

	@GetMapping("/userSearch")
	public String userSearch(@RequestParam("searchVal")String searchVal) {
		System.out.println(searchVal);

		List<MemberDTO> memList = memberService.memberSearch(searchVal);

		return "redirect:/";
	}

	@GetMapping("/boardSearch")
	public String boardSearch(@RequestParam("searchVal")String searchVal) {
		System.out.println(searchVal);
		return "redirect:/";
	}
}
