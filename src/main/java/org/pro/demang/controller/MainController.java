package org.pro.demang.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.service.MemberService;
import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

    @Autowired
    private MemberService memberService;
    @Autowired
	PostService postService;
	@Autowired
	private MainMapper mapper;


    //로그인 페이지
    // @GetMapping("/")
	// public String home() {
		
	// 	return "member/login";
	// }

	// 로그인 페이지로 이동
	@GetMapping("/loginMove")
	public String loginMove() {
		
		return "member/login";
	}

	// 회원가입 페이지로 이동
	@GetMapping("/signUp")
	public String signup() {
		
		return "member/signUp";
	}

    // 관리자 페이지 이동
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

    // 댓글 보기
	@PostMapping("/CommentShow")
	@ResponseBody
	public List<CommentDTO> commentShow(@RequestParam("p_id")String p_id){
		
		System.out.println("댓글 보기 Controller 작동시작");
		List<CommentDTO> list = postService.commentShow(p_id);
		System.out.println("댓글 보기 Controller 작동 끝");
		return list;
		
	}
	
	// 댓글 작성
	@PostMapping("/CommentInsert")
	@ResponseBody
	public String commentInsert(CommentDTO dto) {
		System.out.println("댓글 작성 Controller 시작");
		postService.commentInsert(dto);
		System.out.println("댓글 작성 Controller 끝");
		return "OK";
	}

    // 회원가입
    @PostMapping("/signUp")
	public String signUp(MemberDTO dto) {
		
		System.out.println("dto 값 확인 : " + dto);
		
		memberService.memberInsert(dto);
		return "member/login";
	}

    // 게시글 입력페이지 이동
	@GetMapping("/postInsert")
	public String postInsertRoute() {
		return "post/PostInsert";
	}
	
	//게시글 상세보기 페이지 이동
	@GetMapping("/postView")
	public String postViewRoute() {
		return "post/PostView";
	}
	
	//게시글 등록
	@PostMapping("/postInsert")
	public String postInsert(
            @RequestParam(value="p_image", required=false) MultipartFile file,
	        @RequestParam("p_content")String p_content) {
	    try {
	        postService.postInsert(p_content, file.getBytes());
	        
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return "post/PostInsert";
	}

    //// 개인 피드
	@GetMapping("/feed")
	public String feed( Model model, HttpSession session ) {
		String testLogin = "6";// ???test 1번 회원으로 로그인했다 치고 시작하기
		session.setAttribute("login", testLogin);
		model.addAttribute(// 현재 로그인한 회원의 팔로들의 글 목록(번호만)
				"PostList", 
				postService.getPostList_followee( testLogin )
				);
		model.addAttribute("testdto", postService.getPost( "5" ));
		return "post/feed";
	}
	
	//// 게시글 한 개
	@PostMapping("/postItem")
	public String feedItem( @RequestParam("no") String no, Model model ) {
		//// 번호로 게시글 찾아서 DTO 받아오기
		PostDTO dto = postService.getPost( no );
		model.addAttribute("dto", dto);
		//// 게시글 작성자 정보
		model.addAttribute(
				"writer",
				memberService.getMember_no( dto.getP_writer() )
				);
		//// 게시글의 댓글 정보
		model.addAttribute(
				"commentList",
				mapper.getCommentList_recent( no )
				);
		return "post/post";
	}
	

}
