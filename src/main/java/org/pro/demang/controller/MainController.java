package org.pro.demang.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Param;
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
	public String loginMove( @Param("red") String red, Model model ) {
		model.addAttribute("red", red);
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
		model.addAttribute(
				"fList",
				memberService.fList(follower)
				);
		return "other/fList";
	}

    // 댓글 보기
	@PostMapping("/CommentShow")
	@ResponseBody
	public List<CommentDTO> commentShow(@RequestParam("p_id")String p_id){
		List<CommentDTO> list = postService.commentShow(p_id);
		return list;
		
	}
	
	// 댓글 작성
	@PostMapping("/CommentInsert")
	@ResponseBody
	public String commentInsert(CommentDTO dto) {
		postService.commentInsert(dto);
		return "OK";
	}

    // 회원가입
    @PostMapping("/signUp")
    public String signUp(MemberDTO dto, Model model) {
    		
       int result = memberService.memberInsert(dto);
       
       if(result == 1) {
    	   return "member/login";
       }else {
    	   model.addAttribute("result", result);
    	   return "member/signUp";
       }
      
    }
    

    // 게시글 입력페이지 이동
	@GetMapping("/postInsert")
	public String postInsertRoute(HttpSession session) {
		if( session.getAttribute("login") == null ) return "redirect:/loginMove?red=postInsert";// 비회원인 경우 로그인하러 가기
		return "post/PostInsert";
	}
	
	//게시글 상세보기 페이지 이동
	@GetMapping("/postView")
	public String postViewRoute( @RequestParam("p_id") String p_id, HttpSession session, Model model) {		
		
		MemberDTO dto = memberService.getMember_no(session.getAttribute("login"));
		
		// 게시글 정보 받아오기
		model.addAttribute(
				"post",
				mapper.getPost(p_id)
				);
		// 게시글의 이미지 정보
		model.addAttribute(
				"imageList",
				mapper.getImageList(p_id)
				);
		// 게시글 댓글 받아오기
		model.addAttribute(
				"commentList",
				mapper.getCommentList(p_id)
				);
		// 유저 정보 받아오기
		model.addAttribute("member", dto);
		
		return "post/PostView";
	}
	
	//세션으로 사용자 정보를 호출
	@PostMapping("/memberCheck")
	@ResponseBody
	public String memberCheck(@RequestParam("m_id")String m_id, Model model) {
		
		MemberDTO dto = memberService.getMember_no(m_id);
		System.out.println("회원 불러오기");
		System.out.println(dto.toString());
		
		model.addAttribute("member", dto);
		
		return "";
	}
	
	//게시글 등록
//	@PostMapping("/postInsert")
//	public String postInsert(
//            @RequestParam(value="p_image", required=false) MultipartFile file,
//	        @RequestParam("p_content")String p_content) {
//	    try {
//	        postService.postInsert(p_content, file.getBytes());
//	        
//	      } catch (Exception e) {
//	         e.printStackTrace();
//	      }
//	      return "post/PostInsert";
//	}
	
	//// 게시글 한 개 가져오기(ajax용)
	@PostMapping("/getPostForFeed")
	public String feedItem( @RequestParam("no") String no, Model model ) {
		//// 번호로 게시글 찾아서 DTO 받아오기
		PostDTO dto = postService.getPost( no );
		model.addAttribute("post", dto);
		//// 게시글의 이미지 정보
		model.addAttribute(
				"imageList",
				mapper.getImageList( no )
				);
		//// 게시글의 댓글 정보
		model.addAttribute(
				"commentList",
				mapper.getCommentList_recent( no )
				);
		return "post/post";
	}

    //// 개인 피드
	@GetMapping("/feed")
	public String feed( Model model, HttpSession session ) {
		if( session.getAttribute("login") == null ) return "redirect:/loginMove?red=feed";// 비회원인 경우 로그인하러 가기
		//// 피드에 나올 글 목록 번호를 model에 붙이고 feed 화면으로
		model.addAttribute(// 현재 로그인한 회원의 팔로들의 글 목록(번호만)
				"PostList", 
				postService.getPostList_followee( loginId(session) )
				);
		return "post/feed";
	}

	//// 개인 페이지
	@GetMapping("/vip")
	public String profile( @RequestParam(value="p", required=false) String no, Model model, HttpSession session ) {
		//// 회원번호 지정되어있지 않으면 자신(현재로그인)의 번호로 지정
		if( no == null ) no = loginId(session);
		//// 식별코드로 회원정보 찾기
		MemberDTO dto = memberService.getMember_no(no);
		//// 찾는 회원이 없는 경우 회원 없다는 페이지로 이동
		if( dto == null ) return "post/profile_noMember";
		model.addAttribute(// 글 목록 (이 페이지 주인이 쓴 글 목록)
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
				loginId(session),
				m2);
		return "";
	}
	
	//// 팔로우 확인
	@GetMapping("/func/followCheck")
	@ResponseBody
	public String followCheck( String m2, HttpSession session ) {
		if( mapper.followCheck(
				loginId(session), 
				m2
				) != 0 ) {
			return "O";
		}
		return "X";
	}
	
	

	//// 현재 로그인한 회원 번호(문자열로) 가져오기
	private static String loginId( HttpSession session ) {
		return session.getAttribute("login")+"";
	}
}
