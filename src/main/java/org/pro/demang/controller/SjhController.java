package org.pro.demang.controller;

import javax.servlet.http.HttpSession;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.CommentDTO;
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

@Controller
public class SjhController {
	
    @Autowired
	PostService postService;
	@Autowired
	private MainMapper mapper;
	
	// 게시글 작성하는 PostMapping
	@PostMapping("/postInsert")
	public String postInsert(
	        @RequestParam("p_content")String p_content,
	        @RequestParam("p_type")String p_type,
	        @RequestParam("p_writer")int p_writer,
	        @RequestParam(value="p_image", required = false)MultipartFile[] files,
	        @RequestParam("mer_name")String mer_name, 
	        @RequestParam("mer_price")int mer_price,
	        @RequestParam("mer_amount")int mer_amount){
		
		//일반글 입력
		if(p_type.equals("N")) {

		try {
//			mer_name = "";
//			mer_price = 1;
//			mer_amount = 1;
			PostDTO dto = new PostDTO(p_type, p_writer, p_content); // 생성되기 전 게시글에 들어갈 값을 dto로 먼저 생성
			postService.postInsertN( dto ); // 작동
			int p_id = dto.getP_id(); // 생성 된 게시글의 이미지 등록시 참조하기 위해 p_id값을 가져옴
			
			for(int i = 0; i < files.length; i++) {
				PostImgDTO imgDTO = new PostImgDTO(); // 이미지가 들어갈 DTO를 생성
				imgDTO.setI_image(files[i].getBytes()); // DTO안에 이미지를 byte변환하여 넘겨줌
				postService.postInsertImg(p_id, imgDTO.getI_image()); // 작동
				System.out.println(i + "번째 이미지 입력됨");
			}
			System.out.println(dto.getP_id()+"번 게시글로 작성된 거 확인하세요. ~ sjh controller ###########################");
		} catch (Exception e) {
			e.printStackTrace();
			}
		}
		// 판매글 입력
		if(p_type.equals("S")) {
			try {
				PostDTO dto = new PostDTO(p_type, p_writer, p_content); // 생성되기 전 게시글에 들어갈 값을 dto로 먼저 생성
				postService.postInsertN( dto ); // 작동
				int p_id = dto.getP_id(); // 생성 된 게시글의 이미지 등록시 참조하기 위해 p_id값을 가져옴
				
				// 판매 게시글 번호, 상품 이름, 가격, 수량을 상품 정보 테이블에 등록
				MerchandiseDTO merDTO = new MerchandiseDTO(p_id, mer_name, mer_price, mer_amount);
				postService.orderPostInsert(merDTO);
				
				for(int i = 0; i < files.length; i++) {
					PostImgDTO imgDTO = new PostImgDTO(); // 이미지가 들어갈 DTO를 생성
					imgDTO.setI_image(files[i].getBytes()); // DTO안에 이미지를 byte변환하여 넘겨줌
					postService.postInsertImg(p_id, imgDTO.getI_image()); // 작동
					System.out.println(i + "번째 이미지 입력됨");
				}
				System.out.println(dto.getP_id()+"번 게시글로 작성된 거 확인하세요. ~ sjh controller ###########################");
			} catch (Exception e) {
				e.printStackTrace();
				}
		}
		return "post/PostInsert";
		}
	
	@GetMapping("postInsert2")
	public String Review(@RequestParam("p_id") String p_id, Model model) {
		
		model.addAttribute(
				"post",
				mapper.getPost(p_id)
				);
		return "post/PostInsert2";
	}
	
	// 리뷰글 작성전용 insert
	@PostMapping("/postInsert2")
	public String postInsert2(
	        @RequestParam("p_content")String p_content,
	        @RequestParam("p_type")String p_type,
	        @RequestParam("p_writer")int p_writer,
	        @RequestParam("p_origin")int p_origin,
	        @RequestParam(value="p_image", required = false)MultipartFile[] files) {

		try {
			PostDTO dto = new PostDTO(p_origin, p_type, p_writer, p_content); // 생성되기 전 게시글에 들어갈 값을 dto로 먼저 생성
			postService.postInsert( dto ); // 작동
			int p_id = dto.getP_id(); // 생성 된 게시글의 이미지 등록시 참조하기 위해 p_id값을 가져옴
			
			for(int i = 0; i < files.length; i++) {
				PostImgDTO imgDTO = new PostImgDTO(); // 이미지가 들어갈 DTO를 생성
				imgDTO.setI_image(files[i].getBytes()); // DTO안에 이미지를 byte변환하여 넘겨줌
				postService.postInsertImg(p_id, imgDTO.getI_image()); // 작동
				System.out.println(i + "번째 이미지 입력됨");
			}
			System.out.println(dto.getP_id()+"번 게시글로 작성된 거 확인하세요. ~ sjh controller ###########################");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "post/feed";
		}
	
	// 좋아요 버튼 누르면 타는 Mapping
	@PostMapping("likeToPost")
	@ResponseBody
	public String likeToPost(@RequestParam("p_id")String l_postNo, @RequestParam("m_id")String l_id) {
		
		postService.addLike(l_id, l_postNo);
		System.out.println("좋아요 누르기 완료");
		
		return "";
	}
	
	// DB에서 로그인 한 사람과 게시물을 검색해서 이미 좋아요를 눌렀다면 다른 버튼을 띄우기
	@PostMapping("likeCheck")
	@ResponseBody
	public String likeCheck(@RequestParam("p_id")String l_postNo, HttpSession session) {
		return postService.likeCheck(session.getAttribute("login")+"", l_postNo); 
	}
	
	// 좋아요 갯수 불러오기
	@PostMapping("likeCount")
	@ResponseBody
	public String likeCount(@RequestParam("p_id")String l_postNo) {
		return postService.likeCount(l_postNo); 
	}
	
	// 댓글 작성
	@PostMapping("/CommentInsert2")
	@ResponseBody
	public String commentInsert(@RequestParam("m_id")int c_writer, @RequestParam("p_id")int c_postNo, @RequestParam("commentWrite")String c_content) {
		CommentDTO dto = new CommentDTO();
		dto.setC_writer(c_writer);
		dto.setC_content(c_content);
		dto.setC_postNo(c_postNo);
		postService.commentInsert(dto);
		return "OK";
	}
	
	// 판매 상태를 변경하기
	@PostMapping("changeSell")
	@ResponseBody
	public String changeSell(@RequestParam("p_id")String p_id, @RequestParam("p_type")String p_type) {
		postService.postSellUpdate(p_id, p_type);
		return "";
	}
	
	// 리뷰 작성 전 구매자 확인
	@PostMapping("reViewCheck")
	@ResponseBody
	public String reViewCheck(@RequestParam("p_id")String p_id, HttpSession session) {
		String ord_buyer = session.getAttribute("login")+"";
		
		if(postService.reViewCheck(p_id, ord_buyer) == true) {
			return "found";
		}else {
			return "not found";
		}
	}
	
}