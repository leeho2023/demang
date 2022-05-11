package org.pro.demang.controller;

import javax.servlet.http.HttpSession;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.CommentDTO;
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

    //// 개인 피드
	@GetMapping("/feed")
	public String feed( Model model, HttpSession session ) {
		if( session.getAttribute("login") == null ) return "redirect:/loginMove";// 비회원인 경우 로그인하러 가기
		System.out.println(session.getAttribute("login")+"번 회원으로 로그인 ~ kysController.feed");
		//// 피드에 나올 글 목록 번호를 model에 붙이고 feed 화면으로
		model.addAttribute(// 현재 로그인한 회원의 팔로들의 글 목록(번호만)
				"PostList", 
				postService.getPostList_followee( 
						session.getAttribute("login")+""// 정수를 문자열로 바꾸려고 +""
						)
				);
		return "post/feed";
	}

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
	
	//// 댓글 등록 (ajax용)
	@PostMapping("func/newComment")
	@ResponseBody
	public String newComment( CommentDTO dto ) {
		return memberService.commentInsert(dto);
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
	@PostMapping("/func/doFollow")
	@ResponseBody
	public String doFollow( String m2, HttpSession session ) {
		mapper.doFollow(
				session.getAttribute("login")+"", // +""의 뜻: 문자열로 바꿈
				m2);
		return "";
	}
	
	//// 팔로우 확인
	@GetMapping("/func/followCheck")
	@ResponseBody
	public String followCheck( String m2, HttpSession session ) {
		if( mapper.followCheck(
				session.getAttribute("login")+"", // +""의 뜻: 문자열로 바꿈
				m2
				) != 0 ) {
			return "O";
		}
		return "X";
	}
	
}
