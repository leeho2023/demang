package org.pro.demang.controller;

import java.util.ArrayList;
import java.util.List;

import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.service.MemberService;
import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@GetMapping("/fList")
	public String fList() {
		
		return "other/fList";
	}

	@GetMapping("/userSearch")
	public String userSearch(@RequestParam("searchVal")String searchVal, Model model) {
		System.out.println(searchVal);

		List<MemberDTO> memList = memberService.memberSearch(searchVal);

		for(MemberDTO dto : memList){
			System.out.println(dto.toString());
		}

		model.addAttribute("list", memList);


		return "other/searchUser";
	}

	@GetMapping("/boardSearch")
	public String boardSearch(@RequestParam("searchVal")String searchVal, Model model) {
		System.out.println(searchVal);

		List<PostDTO> postList = postService.postSearch(searchVal);
		
		List<MemberDTO> memList = new ArrayList<MemberDTO>();
		
		for(PostDTO dto : postList){
			MemberDTO memdto = new MemberDTO();
			memdto.setM_code(dto.getMemberDTO().getM_code());
			memdto.setM_email(dto.getMemberDTO().getM_email());
			memdto.setM_nickname(dto.getMemberDTO().getM_nickname());
			memList.add(memdto);
		}
		model.addAttribute("postList", postList);
		model.addAttribute("memList", memList);

		return "other/searchPost";
	}
}
