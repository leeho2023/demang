package org.pro.demang.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.AnswerDTO;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.ContactUsImgDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.OrderDTO;
import org.pro.demang.model.PostDTO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

// ?????? ????????? ?????? ????????????
	@PostMapping("/contactUsInsert")
	public String contactUsInsert(
			ContactUsDTO dto,
	        @RequestParam("files") MultipartFile[] files, 
	        RedirectAttributes rttr
	        ) {
		try {
			memberService.contactUsInsert( dto ); // ??????
			int c_id = dto.getC_id(); // ?????? ??? ???????????? ????????? ????????? ???????????? ?????? p_id?????? ?????????
			
			// for(int i = 0; i < files.length; i++){
			// }

			for(int i = 0; i < files.length; i++) {
				ContactUsImgDTO imgDTO = new ContactUsImgDTO(); // ???????????? ????????? DTO??? ??????
				imgDTO.setI_image(files[i].getBytes()); // DTO?????? ???????????? byte???????????? ?????????
				memberService.contactUsImgInsert(c_id, imgDTO.getI_image()); // ??????
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		rttr.addFlashAttribute("alert", "????????? ?????????????????????.");
		return "redirect:/serviceCenter";
	}

// ????????? user ?????????
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
	
// ????????? post ?????????
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
	
	
	
//	admin messages ????????? 
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

	// orderList ?????????
	@GetMapping("/testOrderList")
	public String testOrderList(
		@RequestParam(required = false, defaultValue = "1") int pageNum,
		Model model, HttpSession session) throws Exception {
			if(loginId(session) == 0) return "redirect:/loginMove?red=orderlist";

			PageInfo<OrderDTO> p = new PageInfo<>(pagingServiceImpl.getOrderList(pageNum, loginId(session)), 10);
			model.addAttribute("orderList", p);

			return "order/testOrderList";
		}

//	messageOneSelect ????????? ?????? ??????
	@PostMapping("/messageOneSelect")
	public String messageOneSelect(@RequestParam("c_id") String c_id, Model model) {
		ContactUsDTO cDto = memberService.messageOneSelect(c_id);
		AnswerDTO aDto = memberService.answerSelect(c_id);
		
		memberService.updateC_checked(c_id);
		model.addAttribute("cDto", cDto);
		model.addAttribute("aDto", aDto);
		return "admin/appendMessage";
	}
	
//	?????? ?????? ??? ????????????
	@PostMapping("/sendMailForm")
	public String sendMailForm(
			@RequestParam("m_email") String m_email,
			@RequestParam("c_id") String c_id, Model model) {
		model.addAttribute("m_email", m_email);
		model.addAttribute("c_id", c_id);
		return "admin/sendMailForm";
	}
	
//	?????? ????????????
	@PostMapping("/answerInsert")
	public String answerInsert(AnswerDTO dto,
			@RequestParam("m_email") String m_email) {
		
		System.out.println("m_email ??? : " + m_email);
		
		try {
			memberService.answerInsert(m_email, dto);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return "redirect:/contactUsList";
	}
	
//	?????? ?????? ?????? ajax
	@PostMapping("/warnCountUp")
	@ResponseBody
	public int warnCountUp(@RequestParam("m_id") String m_id, Model model) {
		memberService.warnCountUp(m_id);
		int result = memberService.getWarnCount(m_id);
		return result;
	}
	
//	?????? ?????? ?????? ajax
	@PostMapping("/warnCountDown")
	@ResponseBody
	public int warnCountDown(@RequestParam("m_id") String m_id, Model model) {
		memberService.warnCountDown(m_id);
		int result = memberService.getWarnCount(m_id);
		return result;
	}


	//// ?????? ???????????? ?????? ??????(??????) ???????????? // ???????????? ????????? ?????? 0??????
	private static int loginId( HttpSession session ) {
		if( session.getAttribute("login") == null ) return 0;
		return Integer.parseInt( session.getAttribute("login")+"" );
	}


	// #########################################################################################
	
}
