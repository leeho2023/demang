package org.pro.demang.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.EmailCheckDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.service.MailService;
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
public class LhhController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PostService postService;

	@Autowired
	private MainMapper mapper;
	
	@Autowired
	private MailService mailService;
	

    @GetMapping("/")
	public String home() {
		
		return "member/signUp";
	}
    
	@GetMapping("/fList")
	public String fList() {
		
		return "other/fList";
	}

	//유저 검색
	@GetMapping("/userSearch")
	public String userSearch(@RequestParam("reSearchVal")String reSearchVal, Model model) {
		System.out.println("유저 검색 발동!! : " + reSearchVal);

		List<MemberDTO> memList = memberService.memberSearch(reSearchVal);

		for(MemberDTO dto : memList){
			System.out.println(dto.toString());
		}

		model.addAttribute("list", memList);


		return "other/searchUser";
	}

	//입력된 정보로 게시글 번호 리스트로 받기
	@GetMapping("/postSearch")
	public String postSearch(@RequestParam("searchVal")String searchVal, Model model) {
		System.out.println("게시물 검색 발동!! : " + searchVal);
		

		List<Integer> postNoList = postService.getPostNO(searchVal);
		
		// for(PostDTO dto : postList){
		// 	System.out.println(dto.toString());
		// }
		// List<String> pImgList = new ArrayList<>();
		// for(int i = 0;  i < postList.size(); i++){
		// 	pImgList.add("data:image/;base64," + Base64.getEncoder().encodeToString(postList.get(i).getPostImgDTO().getI_image()));
		// }
		// model.addAttribute("pImgList", pImgList);
		model.addAttribute("postNoList", postNoList);

		return "other/searchPost";
	}

	// 게시글 가져오기
	@PostMapping("/getPostForSearch")
	public String getPost( @RequestParam("no") int no, Model model ) {
		//// 번호로 게시글 찾아서 DTO 받아오기
		PostDTO dto = postService.getPost( no );
		model.addAttribute("post", dto);
		//// 게시글의 이미지 정보
		model.addAttribute(
				"image",
				mapper.getImage( no )
				);
		return "other/searchResult_post";
	}

	// 입력된 태그로 게시글 번호 리스트 받아오기!
	@GetMapping("/tagSearch")
	public String tagSearch(@RequestParam("reSearchVal")String reSearchVal, Model model) {
		System.out.println("태그 검색 발동!! : " + reSearchVal);

		List<Integer> tagForPostNoList = postService.tagForGetPostNO(reSearchVal);
		
		// for(TagDTO dto : postList){
		// 	System.out.println(dto.toString());
		// }

		model.addAttribute("tagForPostNoList", tagForPostNoList);

		return "other/searchTag";
	}

	// 회원 코드 생성 테스트
	@GetMapping("/createCode")
	public String createCode(){



		// String codeCheck = "";
		// boolean check = true;
		// while(check){
		// 	codeCheck = memberService.codeCheck();
		// 	if(codeCheck.equals("fail")){
		// 		System.out.println("다시 회원 코드를 생성합니다.");
		// 	}else{
		// 		System.out.println("회원 코드 생성중...");
		// 		break;
		// 	}
		// }
		// System.out.println("생성된 회원 코드는 : " + codeCheck);

		return "redirect:/";
	}
	
	//메일 체크하기
	@PostMapping("/emailReduplicationCheck")
	@ResponseBody
	public int emailReduplicationCheck(@RequestParam("m_email")String m_email){
		int emailCheckResult = mailService.emailCheck(m_email);
		System.out.println(emailCheckResult);

		return emailCheckResult;
	}

	// 메일 보내기
	@PostMapping("/sendMail")
	public String sendMail(@RequestParam("m_email")String m_email) throws MessagingException {

		mailService.sendMail(m_email);
		
	    return "redirect:/";
	}
	
	@PostMapping("/reEmailCheck")
	@ResponseBody
	public int reEmailCheck(EmailCheckDTO dto){
		System.out.println(dto.toString());
		int result = mailService.reEmailCheck(dto);
		
		return result;
	}
	
}
