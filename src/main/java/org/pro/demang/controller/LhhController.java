package org.pro.demang.controller;

import java.util.List;

import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.model.TagDTO;
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
		
		return "other/searchResult";
	}

    @GetMapping("/searchResult")
	public String searchResult() {
		
		return "other/searchResult";
	}
    
	@GetMapping("/fList")
	public String fList() {
		
		return "other/fList";
	}

	//유저 검색
	@GetMapping("/userSearch")
	public String userSearch(@RequestParam("reSearchVal")String reSearchVal, Model model) {
		System.out.println(reSearchVal);

		List<MemberDTO> memList = memberService.memberSearch(reSearchVal);

		for(MemberDTO dto : memList){
			System.out.println(dto.toString());
		}

		model.addAttribute("list", memList);


		return "other/searchUser";
	}

	//게시글 검색
	@GetMapping("/postSearch")
	public String postSearch(@RequestParam("searchVal")String searchVal, Model model) {
		System.out.println(searchVal);

		List<PostDTO> postList = postService.postSearch(searchVal);
		
		model.addAttribute("postList", postList);

		return "other/searchPost";
	}

	//태그 검색
	@GetMapping("/tagSearch")
	public String tagSearch(@RequestParam("reSearchVal")String reSearchVal, Model model) {
		System.out.println(reSearchVal);

		List<TagDTO> postList = postService.tagSearch(reSearchVal);
		
		for(TagDTO dto : postList){
			System.out.println(dto.toString());
		}

		// model.addAttribute("postList", postList);

		return "other/searchTag";
	}

}
