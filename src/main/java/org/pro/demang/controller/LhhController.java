package org.pro.demang.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import com.github.pagehelper.PageInfo;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.AnswerDTO;
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
		
		return "redirect:/feed";
	}
    
    @GetMapping("/admin")
	public String admin(Model model, HttpSession session) {
		
		String email = session.getAttribute("email")+"";
		if(email.equals("admin")){
			model.addAttribute("userCount", mapper.userCount());
		model.addAttribute("postCount", mapper.postCount());
		model.addAttribute("postTOP", mapper.postTOP());

		return "admin/index";
		}

		return "other/error";
	}

    @GetMapping("/noPost")
	public String noPost() {
		return "other/noPost";
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

// 문의 사진과 같이 등록하기
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

// 관리자 user 페이징
	@GetMapping("/userListPage")
    public String userListPage(
		@RequestParam(required = false, defaultValue = "1") int pageNum, Model model, HttpSession session) throws Exception {
		
		String email = session.getAttribute("email")+"";
		if(email.equals("admin")){
			PageInfo<MemberDTO> p = new PageInfo<>(pagingServiceImpl.getUserList(pageNum), 10);
    		model.addAttribute("userListPaging", p);
    
    	   	return "admin/userPage";
		}
       	return "other/error";
    }
	
// 관리자 post 페이징
	@GetMapping("/postListPage")
    public String postListPage(
		@RequestParam(required = false, defaultValue = "1") int pageNum,
		Model model, HttpSession session) throws Exception {
			String email = session.getAttribute("email")+"";
			if(email.equals("admin")){
				PageInfo<PostDTO> p = new PageInfo<>(pagingServiceImpl.getPostList(pageNum), 10);
				model.addAttribute("postListPaging", p);
		
				return "admin/postPage";
			}
		return "other/error";
    }
	
	
	
//	admin messages 페이징 
	@GetMapping("/contactUsList")
	public String page(
            @RequestParam(required = false, defaultValue = "1") int pageNum,
			Model model, HttpSession session) throws Exception {
				String email = session.getAttribute("email")+"";
				if(email.equals("admin")){
					PageInfo<ContactUsDTO> p = new PageInfo<>(pagingServiceImpl.getContactList(pageNum), 10);
					model.addAttribute("contactUsList", p);
					return "admin/messages";
				}
			return "other/error";
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
	@PostMapping("/answerInsert")
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
	
//	유저 경고 주기 ajax
	@PostMapping("/warnCountUp")
	@ResponseBody
	public int warnCountUp(@RequestParam("m_id") String m_id, Model model) {
		memberService.warnCountUp(m_id);
		int result = memberService.getWarnCount(m_id);
		return result;
	}
	
//	유저 경고 빼기 ajax
	@PostMapping("/warnCountDown")
	@ResponseBody
	public int warnCountDown(@RequestParam("m_id") String m_id, Model model) {
		memberService.warnCountDown(m_id);
		int result = memberService.getWarnCount(m_id);
		return result;
	}



	// #########################################################################################
	
}
