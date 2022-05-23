package org.pro.demang.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import com.github.pagehelper.PageInfo;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.ContactUsImgDTO;
import org.pro.demang.model.EmailCheckDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.service.MailService;
import org.pro.demang.service.MemberService;
import org.pro.demang.service.PagingServiceImpl;
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
public class LhhController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PostService postService;

	@Autowired
	private MainMapper mapper;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private PagingServiceImpl pagingServiceImpl;

    @GetMapping("/")
	public String home() {
		
		return "post/feed";
	}
    
    @GetMapping("/admin")
	public String admin(Model model) {
		
		model.addAttribute("userCount", mapper.userCount());
		model.addAttribute("postCount", mapper.postCount());
		model.addAttribute("postTOP", mapper.postTOP());

		return "admin/index";
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

	// 이메일 체크
	@PostMapping("/reEmailCheck")
	@ResponseBody
	public int reEmailCheck(EmailCheckDTO dto){
		System.out.println(dto.toString());
		int result = mailService.reEmailCheck(dto);
		
		return result;
	}


	@PostMapping("/contactUsInsert")
	public String contactUsInsert(ContactUsDTO dto,
	        @RequestParam("files") MultipartFile[] files) {
		try {

			System.out.println(dto.toString());
			memberService.contactUsInsert( dto ); // 작동
			int c_id = dto.getC_id(); // 생성 된 게시글의 이미지 등록시 참조하기 위해 p_id값을 가져옴
			
			// System.out.println(files.length); // 이미지 들어오는 files의 length 확인
			// for(int i = 0; i < files.length; i++){
			// 	System.out.println(files[i].getBytes()); // 들어오는 이미지 files의 바이트배열로 바꿔서 확인
			// }

			for(int i = 0; i < files.length; i++) {
				ContactUsImgDTO imgDTO = new ContactUsImgDTO(); // 이미지가 들어갈 DTO를 생성
				imgDTO.setI_image(files[i].getBytes()); // DTO안에 이미지를 byte변환하여 넘겨줌
				memberService.contactUsImgInsert(c_id, imgDTO.getI_image()); // 작동
				System.out.println(i + "번째 이미지 입력됨");
			}
			System.out.println(dto.getC_id()+"번 게시글로 작성된 거 확인하세요. ~ LHH controller ###########################");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	// ##########################################################################################
	//  페이징 관련 테스트
	//admin 페이지에서 messages 탭으로 들어갈 때 ajax로 최신 10개 글만 불러 오기
	@PostMapping("/messageList")
	public String messageList(@RequestParam("c_id")int c_id, Model model) {
		System.out.println(c_id);
		List<ContactUsDTO> dtoList = memberService.messageList(c_id);
		ArrayList<Integer> arrlist = memberService.contactAllNumCount();
		model.addAttribute("arrlist", arrlist);
		model.addAttribute("dtoList", dtoList);

		return "admin/appendMessage";
	}

	@PostMapping("/contactAllNumCount")
	public String contactAllNumCount(Model model){

		ArrayList<Integer> arrlist = memberService.contactAllNumCount();
		model.addAttribute("arrlist", arrlist);

		return "admin/pageNumList";
	}
	
	@GetMapping("/contactUsList")
	public String page(
            @RequestParam(required = false, defaultValue = "1") int pageNum, Model model) throws Exception {
	PageInfo<ContactUsDTO> p = new PageInfo<>(pagingServiceImpl.getUserList(pageNum), 10);
	model.addAttribute("contactUsList", p);
	return "admin/messages";
	}
	



	// #########################################################################################
	
}
