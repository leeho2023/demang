package org.pro.demang.controller;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.MemberDTO;
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

    @Autowired
    private MemberService memberService;
	@Autowired
	private MainMapper mapper;

    @Autowired
	PostService postService;
    

	//// 개인 페이지
	@GetMapping("/vip")
	public String profile( @RequestParam("p") String no, Model model ) {
		//// 식별코드로 회원정보 찾기
		MemberDTO dto = memberService.getMember_no(no);
		//// 찾는 회원이 없는 경우 회원 없다는 페이지로 이동
		if( dto == null ) return "post/profile_noMember";
		model.addAttribute(// 글 목록
				"PostList", 
				mapper.getPostList_writer( dto.getM_id() )
				);
		//// 회원정보와 함께 회원정보 페이지로
		model.addAttribute("dto", dto);
		return "post/profile";
	}
	
	//// 팔로잉 수 가져오기
	@GetMapping("/func/followingNum")
	@ResponseBody
	public String followingNum( int m_id ) {
		return ""+mapper.followingCount(m_id);
	}
	//// 팔로워 수 가져오기
	@GetMapping("/func/followerNum")
	@ResponseBody
	public String followerNum( int m_id ) {
		return ""+mapper.followerCount(m_id);
	}
	
	//// 팔로우 하기
	@GetMapping("/func/doFollow")
	@ResponseBody
	public String doFollow( String m1, String m2 ) {
		mapper.doFollow(m1,m2);
		return "";
	}
	
}
