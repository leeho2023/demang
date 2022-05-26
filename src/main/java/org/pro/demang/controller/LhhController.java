package org.pro.demang.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.ContactUsImgDTO;
import org.pro.demang.model.EmailCheckDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.model.AnswerDTO;
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

import com.github.pagehelper.PageInfo;

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
		
		return "redirect:/feed";
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


	@GetMapping("/serviceCenter")
	public String serviceCenter() {
		
		return "other/serviceCenter";
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

	
	
	
//	admin messages 페이징 
	@GetMapping("/contactUsList")
	public String page(
            @RequestParam(required = false, defaultValue = "1") int pageNum, Model model) throws Exception {
	PageInfo<ContactUsDTO> p = new PageInfo<>(pagingServiceImpl.getUserList(pageNum), 10);
	model.addAttribute("contactUsList", p);
	return "admin/messages";
	}

	
//	messageOneSelect 하나만 상세 보기
	@PostMapping("/messageOneSelect")
	public String messageOneSelect(@RequestParam("c_id") String c_id, Model model) {
		System.out.println("LHHController### 선택한 c_id 값 = " + c_id);
		ContactUsDTO cDto = memberService.messageOneSelect(c_id);
		AnswerDTO aDto = memberService.answerSelect(c_id);
		
		System.out.println(aDto);
		memberService.updateC_checked(c_id);
		model.addAttribute("cDto", cDto);
		model.addAttribute("aDto", aDto);
		return "admin/appendMessage";
	}
	
//	문의 메일 폼 나타내기
	@PostMapping("/sendMailForm")
	public String sendMailForm(
			@RequestParam("m_email") String m_email,
			@RequestParam("c_id") String c_id, Model model) {
		System.out.println("LHHController### 선택한 m_email 값 = " + m_email);
		System.out.println("LHHController### 선택한 c_id 값 = " + c_id);
		model.addAttribute("m_email", m_email);
		model.addAttribute("c_id", c_id);
		return "admin/sendMailForm";
	}
	
//	문의 답변하기
	@PostMapping("answerInsert")
	public String answerInsert(AnswerDTO dto,
			@RequestParam("m_email") String m_email) {
		
		System.out.println("m_email 값 : " + m_email);
		System.out.println("===================");
		
		try {
			mailService.answerInsert(m_email, dto);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return "redirect:/contactUsList";
	}

//	admin 페이지에서 검색 하는거
	@GetMapping("/adminSearch")
	public String adminSearch(@RequestParam("search")String search, Model model){
		
		System.out.println("LHHController### admin 페이지 검색 = " + search);
		int memCount = memberService.memberSearchCount(search); // 검색어로 검색된 유저수 
		int postCount = postService.postSearchCount(search); // 검색어로 검색된 게시글 수
		int contactCount = memberService.contactSearchCount(search); // 검색어로 검색된 문의 수
		List<MemberDTO> memList = memberService.memberSearchAdmin(search); // 검색어로 검색된 유저
		List<PostDTO> postList = postService.postSearchAdmin(search); // 검색어로 검색된 게시글
		List<ContactUsDTO> contactList = memberService.contactSearchAdmin(search); // 검색어로 검색된 문의글
		
		System.out.println(memCount);
		System.out.println(postCount);
		System.out.println(contactCount);
		System.out.println(memList);
		System.out.println(postList);
		System.out.println(contactList);
		
		model.addAttribute("search", search);
		model.addAttribute("memCount", memCount);
		model.addAttribute("postCount", postCount);
		model.addAttribute("contactCount", contactCount);
		model.addAttribute("memList", memList);
		model.addAttribute("postList", postList);
		model.addAttribute("contactList", contactList);
		
		
		return "admin/adminSearchTotal";
	}
	



	// #########################################################################################
	
}
