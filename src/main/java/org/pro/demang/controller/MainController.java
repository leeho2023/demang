package org.pro.demang.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.model.PostImgDTO;
import org.pro.demang.service.MemberService;
import org.pro.demang.service.OrderService;
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
    OrderService orderService;
	@Autowired
	private MainMapper mapper;

	// 로그인 페이지로 이동
	@GetMapping("/loginMove")
	public String loginMove( @Param("red") String red, Model model, HttpSession session ) {
		if( loginId(session) != 0 ) return "redirect:/";// 이미 로그인 돼있는 경우 홈으로
		model.addAttribute("red", red);
		return "member/login";
	}

	// 회원가입 페이지로 이동
	@GetMapping("/signUp")
	public String signup() {
		return "member/signUp";
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
	public String commentShow(@RequestParam("p_id") Integer p_id, Model model){
		List<CommentDTO> list = mapper.getCommentList(p_id);
		model.addAttribute("commentList",list);
		return "post/commentList";
		
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
	public String postInsertRoute( 
			@RequestParam(value="p_type", required = false) String p_type,//  게시물 종류
			@RequestParam(value="to", required = false) Integer to,// 리뷰·답글 대상
			HttpSession session, Model model ) {
		
		if( session.getAttribute("login") == null ) return "redirect:/loginMove?red=postInsert";// 비회원인 경우 로그인하러 가기
		
		if( p_type == null ) p_type = "N";// 게시물 종류 기본값 N(일반인데 html 파싱 시에는 N, S를 포괄하는 거로 취급)
		if( to == null ) to = 0;// 리뷰답글 대상 기본값 0 (DB에 넣을 때 null로)

		//// 리뷰글인데 리뷰 대상이 지정되지 않음: 오류
		if( p_type.equals("R") && to == 0 ) {
			// ??? 처리방법 아직 없음
		}
		
		model.addAttribute("p_type", p_type);
		model.addAttribute("to", to);
		
		return "post/PostInsert";
	}
	
	//게시글 상세보기 페이지 이동
	@GetMapping("/postView")
	public String postViewRoute( @RequestParam("p_id") int p_id, HttpSession session, Model model) {
		PostDTO dto = postService.getPost(p_id);
		// 게시글 정보 받아서 넣기
		model.addAttribute( "post", dto );
		// 게시글의 이미지 정보
		List<PostImgDTO> imageList = mapper.getImageList(p_id);
		if( imageList.size() > 0 ) {// 이미지가 있을 때만
			model.addAttribute(
					"imageList",
					imageList
					);
		}
		// 게시글의 댓글들
		model.addAttribute(
				"commentList",
				mapper.getCommentList(p_id)
				);
		// 판매글일 경우 상품 목록
		if( dto.getP_type().equals("S") ) {
			model.addAttribute(
					"merList",
					orderService.getMerchandiseList(p_id)
					);
		}
		
		return "post/PostView";
	}
	
	//세션으로 사용자 정보를 호출
	@PostMapping("/memberCheck")
	@ResponseBody
	public String memberCheck(@RequestParam("m_id") Integer m_id, Model model) {
		
		MemberDTO dto = memberService.getMember_no(m_id);
		System.out.println("회원 불러오기");
		System.out.println(dto.toString());
		
		model.addAttribute("member", dto);
		
		return "";
	}
	
	//// 게시글 한 개 가져오기(ajax용, stack식으로 표시할 용도)
	@PostMapping("/getPost_stack")
	public String postItem_forStack( @RequestParam("no") int no, Model model ) {
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
		return "post/postItem_stack";
	}
	//// 게시글 한 개 가져오기(ajax용, album식으로 표시할 용도)
	@PostMapping("/getPost_album")
	public String postItem_forAlbum( @RequestParam("no") int no, Model model ) {
		//// 번호로 게시글 찾아서 DTO 받아오기
		PostDTO dto = postService.getPost( no );
		model.addAttribute("post", dto);
		//// 게시글의 이미지 한 개만
		model.addAttribute(
				"image",
				mapper.getPostImage( no )
				);
		return "post/postItem_album";
	}
	//// 게시글 한 개 가져오기(ajax용, list식으로 표시할 용도)
	@PostMapping("/getPost_list")
	public String postItem_forList( @RequestParam("no") int no, Model model ) {
		//// 번호로 게시글 찾아서 DTO 받아오기
		PostDTO dto = postService.getPost( no );
		model.addAttribute("post", dto);
		//// 게시글의 이미지 한 개만
		model.addAttribute(
				"image",
				mapper.getPostImage( no )
				);
		return "post/postItem_list";
	}
	
    //// 개인 피드
	@GetMapping("/feed")
	public String feed( Model model, HttpSession session ) {
		if( session.getAttribute("login") == null ) return "redirect:/loginMove?red=feed";// 비회원인 경우 로그인하러 가기
		//// 피드에 나올 글 목록 번호를 model에 붙이고 feed 화면으로
		model.addAttribute(// 현재 로그인한 회원의 팔로들의 글 목록(번호만)
				"postList", 
				postService.getPostList_followee( loginId(session) )
				);
		model.addAttribute("postType", "stack");
		model = postList( // 현재 로그인한 회원의 팔로들의 글 목록을 stack 방식으로 보여주기
				model, 
				"stack", 
				postService.getPostList_followee( loginId(session) ), 
				null );
		return "post/postList";// 게시글 목록 페이지
	}
	
	//// 개인 페이지
	@GetMapping("/vip")
	public String profile( @RequestParam(value="p", required=false) Integer no, Model model, HttpSession session ) {
		//// 회원번호 지정되어있지 않으면 자신(현재로그인)의 번호로 지정, 로그인도 안 돼있으면 로그인 페이지로
		if( no == null ) {
			no = loginId(session);
			if( loginId(session) == 0 )// 로그인 안 돼있으면
				return "redirect:/loginMove?red=vip";// 로그인 페이지로
		} 
		//// 식별코드로 회원정보 찾기
		MemberDTO dto = memberService.getMember_no(no);
		//// 찾는 회원이 없는 경우 회원 없다는 페이지로 이동
		if( dto == null ) return "post/profile_noMember";
		model = postList( model, "stack", mapper.getPostList_writer( dto.getM_id() ), "profile" );// 해당 회원이 쓴 글 목록을 stack 방식으로 profile과 함께 보여주기
		model.addAttribute("dto", dto);// 해당 회원 정보
		return "post/postList";// 게시글 목록 페이지
	}
	
    //// 내가 좋아한 게시글들 보기 페이지
	@GetMapping("/mylike")
	public String mylike( Model model, HttpSession session ) {
		if( session.getAttribute("login") == null ) return "redirect:/loginMove?red=mylike";// 비회원인 경우 로그인하러 가기
		//// 피드에 나올 글 목록 번호를 model에 붙이고 feed 화면으로
		model.addAttribute(// 현재 로그인한 회원의 팔로들의 글 목록(번호만)
				"postList", 
				postService.getPostList_like( loginId(session) )
				);
		model.addAttribute("postType", "list");
		return "post/postList";
	}
	
	//// 댓글 등록 (ajax용)
	@PostMapping("func/newComment")
	public String newComment( CommentDTO dto, HttpSession session, Model model ) {
		//// 내용 없으면 아무것도 하지 않음
		if( dto.getC_content().trim().equals("") ) return "empty";
		//// 댓글 등록
		dto.setC_writer( loginId(session)  );
		postService.commentInsert(dto);// 디비에 댓글 넣기
		//// (단 댓글 표시용) 방금 단 댓글에 해당하는 html 요소를 만들어서 반환 
		model.addAttribute( "comment", dto );
		return "post/comment";
	}

	//// 팔로잉 수 가져오기
	@GetMapping("/func/followingNum")
	@ResponseBody
	public int followingNum( int m_id ) {
		return mapper.followingCount(m_id);
	}
	//// 팔로워 수 가져오기
	@GetMapping("/func/followerNum")
	@ResponseBody
	public int followerNum( int m_id ) {
		return mapper.followerCount(m_id);
	}
	
	//// 팔로우 하기
	@PostMapping("/func/doFollow")
	@ResponseBody
	public String doFollow( int m2, HttpSession session ) {
		if( m2 == loginId(session) ) return "";// 나 자신을 팔로우하려는 시도: 아무것도 안 함
		if( followCheck( m2, session ) ) {// 이미 팔로우 되어있는 경우
			mapper.unFollow(// 팔로우 해제
					loginId(session),
					m2);
		}else {// 아니면
			mapper.doFollow(// 팔로우 하기
					loginId(session),
					m2);
		}
		return "";
	}
	
	//// 팔로우 확인 // true: 팔로우중 / false: 팔로우 안하고있음
	@GetMapping("/func/followCheck")
	@ResponseBody
	public boolean followCheck( int m2, HttpSession session ) {
		if( mapper.followCheck(
				loginId(session), 
				m2
				) != 0 ) {
			return true;
		}
		return false;
	}

	//// 검색 페이지
	@GetMapping("/search")
	public String prePostSearch( 
			@RequestParam(value="type", required=false) String searchType, // hashtag, member, post, null이거나 엉뚱한 값인 경우 post
			@RequestParam("val") String searchVal, 
			Model model) {
		List<Integer> postList;
		//// searchType에 따라 검색 유형 나뉜다
		//// 유형 없는 경우: 게시글 검색으로 리다이렉트
		if( searchType == null ) 
			return "redirect:/search?type=post&val="+searchVal;
		//// 회원 검색
		if( searchType.equals("member") ) {
			if( searchVal.equals("") ) {// 검색어 없는 경우 결과 없음
				model.addAttribute("memList", new ArrayList<MemberDTO>() );
				model.addAttribute("searchVal", "");
				return "other/searchUser";
			}
			List<MemberDTO> memList = memberService.memberSearch(searchVal);
			model.addAttribute("memList", memList);
			model.addAttribute("searchVal", searchVal);
			return "other/searchUser";// 유저 검색 결과 페이지
		}
		//// 해시태그 검색
		if( searchType.equals("hashtag") ) {
			if( searchVal.equals("") ) {// 검색어 없는 경우 결과 없음
				postList = new ArrayList<Integer>();
			}else {
				postList = mapper.searchTag(searchVal);// 검색결과 게시글 목록
			}
			model = postList( model, "album", postList, "searchInfo" );
			model.addAttribute("searchVal", searchVal);
			return "post/postList";// 게시글 목록 페이지
		}
		//// 게시글 검색
		if( searchType.equals("post") ) {
			if( searchVal.equals("") ) {// 검색어 없는 경우 결과 없음
				postList = new ArrayList<Integer>();
			}else {
				postList = mapper.searchPost(searchVal);// 검색결과 게시글 목록
			}
			model = postList( model, "album", postList, "searchInfo" );
			model.addAttribute("searchVal", searchVal);
			return "post/postList";// 게시글 목록 페이지
		}
		//// 다른 엉뚱한 유형: 게시글 검색으로 리다이렉트
		return "redirect:/search?type=post&val="+searchVal;
	}
	
	//// postList return 공통사항
	private Model postList( Model model, String postType, List<Integer> postList, String additional ) {
		model.addAttribute("postList", postList);
		model.addAttribute("postType", postType);// 이 페이지에서 게시글 목록을 보여줄 방식
		model.addAttribute("additional", additional);// 이 페이지에서 [additional].html, css을 추가로 표시
		return model;
	}
	
	
	//// 현재 로그인한 회원 번호(정수) 가져오기
	private static int loginId( HttpSession session ) {
		if( session.getAttribute("login") == null ) return 0;
		return Integer.parseInt( session.getAttribute("login")+"" );
	}
	
}
