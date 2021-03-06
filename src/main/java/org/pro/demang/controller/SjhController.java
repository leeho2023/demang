package org.pro.demang.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.MerchandiseDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.model.PostImgDTO;
import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SjhController {
	
    @Autowired
	PostService postService;
	@Autowired
	private MainMapper mapper;
	
	//// 게시글 작성
	@PostMapping("/postInsert")
	public String postInsert( 
			PostDTO pdto,
			String[] mer_name, int[] mer_price, int[] mer_amount, // MerDTO를 배열로 못 받아서 그만….
			@RequestParam(value="p_image", required = false)MultipartFile[] files, HttpSession session 
			) {
				
		//// 게시글 등록
		pdto.setP_writer( loginId(session) );// 글쓴이 = 현재 로그인한 회원
		postService.postInsert( pdto ); // DB에 게시글 넣기
		int p_id = pdto.getP_id(); // 생성 된 게시글의 이미지, 판매정보 등 등록시 참조하기 위해 p_id값을 가져옴
		
		//// 이미지 등록
		if(files != null) {
			for(int i = 0; i < Math.min( files.length, 8); i++) {// 이미지 개수만큼 반복 (최대 여덟 개)
				try {
					PostImgDTO imgDTO = new PostImgDTO(); // 이미지가 들어갈 DTO를 생성
					imgDTO.setI_image(files[i].getBytes());// 이미지를 byte변환하여 이미지DTO 안에 넣기
					postService.postInsertImg(p_id, imgDTO.getI_image()); // DB에 이미지 삽입
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//// 판매글일 경우; 상품 정보 등록
		if( pdto.getP_type().equals("S")) {
			for( int i=0; i < Math.min(mer_name.length, 5); i++ ) {// 상품 개수 만큼 (최대 다섯 개)(PostInsert.js에서도 같은 수만큼 제한)
				mapper.merchandiseInsert( // 디비에 상품 등록
						new MerchandiseDTO( p_id, mer_name[i], mer_price[i], mer_amount[i])// 인자로 상품 DTO 만들기
						);
			}
		}
		
		return "redirect:/postView?p_id="+p_id;// 방금 작성한 그 게시글 보기 페이지로 리다이렉트
	}
	
	//// 게시글 삭제하기
	@PostMapping("deletePost")
	@ResponseBody
	public String deletePost(
			@RequestParam("p_id")Integer p_id,
			HttpSession session, RedirectAttributes rttr ) {
		
		if( loginId(session) != mapper.getP_writer(p_id) ){// 삭제 시도하는 회원이 글쓴이와 불일치하는 경우 거부 
			rttr.addFlashAttribute( "alert", "접근 시도가 거부되었습니다." );
			return "redirect:/postView?p_id="+p_id;
		}
		
		postService.postDelete(p_id);
		return "";
	}
	
	//// 게시글 수정하기
	@GetMapping("postUpdate")
	public String postUpdateRoute( 
			@RequestParam("p_id")int p_id,
			HttpSession session, Model model, RedirectAttributes rttr ) {
		
		if( loginId(session) == 0 ) return "redirect:/loginMove?red=postInsert";// 비회원인 경우 로그인하러 가기
		if( loginId(session) != mapper.getP_writer(p_id) ){// 수정 시도하는 회원이 글쓴이와 불일치하는 경우 거부 
			rttr.addFlashAttribute( "alert", "접근 시도가 거부되었습니다." );
			return "redirect:/postView?p_id="+p_id;
		}
		
		PostDTO post = postService.getPost_raw(p_id);
		model.addAttribute("post", post);
		
		List<PostImgDTO> imageList = mapper.getImageList(p_id);
		if( imageList.size() > 0 ) {// 이미지가 있을 때만
			model.addAttribute(
					"imageList",
					imageList
					);
		}
		
		return "post/PostUpdate";
	}
	
	@PostMapping("postUpdate")
	public String postUpdate(
			@RequestParam("p_id") Integer p_id, 
			@RequestParam("p_content")String p_content, 
			HttpSession session, RedirectAttributes rttr ) {
		if( loginId(session) != mapper.getP_writer(p_id) ){// 수정 시도하는 회원이 글쓴이와 불일치하는 경우 거부 
			rttr.addFlashAttribute( "alert", "접근 시도가 거부되었습니다." );
			return "redirect:/postView?p_id="+p_id;
		}
		PostDTO dto = new PostDTO();
		dto.setP_id(p_id);
		dto.setP_content(p_content);
		postService.postUpdate( dto );// 업데이트 후
		return "redirect:/postView?p_id="+p_id;// 해당 게시글 보기 페이지로 리다이렉트
	}
	
	//// 게시글에 좋아요 (좋아요 버튼 누르면 ajax로)
	@PostMapping("likeToPost")
	@ResponseBody
	public String likeToPost(@RequestParam("p_id") Integer l_postNo, HttpSession session ) {
		postService.addLike( loginId(session), l_postNo);
		return "";
	}
	
	//// 게시글에 좋아요 취소 (좋아요 취소 버튼 누르면 ajax로)
	@PostMapping("unlikeToPost")
	@ResponseBody
	public String unlikeToPost(@RequestParam("p_id") Integer l_postNo, HttpSession session ) {
		postService.removeLike( loginId(session), l_postNo);
		return "";
	}
	
	// DB에서 로그인 한 사람과 게시물을 검색해서 이미 좋아요를 눌렀다면 다른 버튼을 띄우기
	// 좋아요 여부 확인
	@PostMapping("likeCheck")
	@ResponseBody
	public boolean likeCheck(@RequestParam("p_id") Integer l_postNo, HttpSession session) {
		return postService.likeCheck( loginId(session), l_postNo ); 
	}
	
	// 좋아요 갯수 불러오기
	@PostMapping("likeCount")
	@ResponseBody
	public String likeCount(@RequestParam("p_id") Integer l_postNo) {
		return postService.likeCount(l_postNo); 
	}
	
	
	
	// 리뷰 작성 전 구매자 확인
//	@PostMapping("reViewCheck")
//	@ResponseBody
//	public String reViewCheck(@RequestParam("p_id")String p_id, HttpSession session) {
//		String ord_buyer = session.getAttribute("login")+"";
//		
//		int ord_target = postService.stuffNum(p_id);
//		
//		if(postService.reViewCheck(ord_target, ord_buyer) == true) {
//			return "found";
//		}else {
//			return "not found";
//		}
//	}

	//// 현재 로그인한 회원 번호(정수) 가져오기 // 비로그인 상태일 경우 0으로
	private static int loginId( HttpSession session ) {
		if( session.getAttribute("login") == null ) return 0;
		return Integer.parseInt( session.getAttribute("login")+"" );
	}
	
	//// 댓글 삭제하기
	@PostMapping("deleteComment")
	@ResponseBody
	public String deleteComment(@RequestParam("c_id")String c_id) {
		postService.commentDelete(c_id);
		return "";
	}
	
}