package org.pro.demang.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pro.demang.model.MemberDTO;
import org.pro.demang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LwkController {
	@Autowired
	private MemberService MemberService;
	
	//// 로그인
	//// 로그인 성공시 세션 login 속성에 회원번호가 들어간다. 
	@PostMapping("/login")
	public String login(MemberDTO dto, HttpServletRequest request,RedirectAttributes rttr) {
		MemberDTO member = MemberService.login(dto);// 입력된 정보(dto)로 디비에서 회원정보 찾아오기
		if( member != null ) {// 일치하는 회원 있음: 로그인 성공
			HttpSession session = request.getSession();
			session.setAttribute("login", member.getM_id());
			return "redirect:/feed";
		}else {// 로그인 실패
			rttr.addFlashAttribute("msg", false);
			return "redirect:/loginMove";
		}
	}
	
	//// 로그아웃
	//// 세션을 삭제한다.
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 회원 정보보기창
	@GetMapping("/memberRead")
	public String memberRead(Model model, HttpSession session) {
		MemberDTO dto = MemberService.getMember_no(session.getAttribute("login")+"");
		System.out.println("lwkController.memberRead "+dto);
		model.addAttribute("dto",dto);
		return "member/memberRead";
	}
	
	// 회원 업데이트창
	@GetMapping("/memberUpdate")
	public String memberUpdate(Model model, HttpSession session) {
		MemberDTO dto = MemberService.getMember_no(session.getAttribute("login")+"");
		model.addAttribute("dto",dto);
		return "member/memberUpdate";
	}
	// 회원정보 업데이트 하기
	@PostMapping("/memberUpdate")
	public String memberUpdate2(MemberDTO dto, HttpSession session) {
		dto.setM_id( (int)session.getAttribute("login") );
		MemberService.memberUpdate(dto);
		return "redirect:/memberRead";
	}
	
	   
	   @PostMapping("/emailCheck")
	   @ResponseBody
	   public String emailCheck(@RequestParam("m_email") String m_email, RedirectAttributes rttr) {
	      String result = MemberService.emailCheck(m_email);
	      if (result.equals("useUser_email")) {
	         System.out.println("user_emailbaaaaaaaaaaaad");
	         rttr.addFlashAttribute("bad", false);
	         return "bad";
	      } else {
	         System.out.println("user_emailgooooooooooooood");
	         rttr.addFlashAttribute("good", true);
	         return "good";
	      }
	   }
}


