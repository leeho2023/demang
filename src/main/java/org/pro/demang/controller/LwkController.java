package org.pro.demang.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.service.MemberService;
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
public class LwkController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MainMapper mapper;
	
	//// 로그인
	//// 로그인 성공시 세션 login 속성에 회원번호가 들어간다. 
	@PostMapping("/login")
	public String login( MemberDTO dto, @Param("red") String red, HttpServletRequest request, RedirectAttributes rttr) {
		// 들어온 dto의 이메일이 admin일 때 
		if(dto.getM_email().equals("admin")) {
			MemberDTO member = memberService.login(dto);
			HttpSession session = request.getSession();
			session.setAttribute("login", member.getM_id());
			session.setAttribute("email", member.getM_email());
			return "redirect:/feed";
		}else { // admin이 아닐 때 로그인하기
			MemberDTO member = memberService.login(dto);// 입력된 정보(dto)로 디비에서 회원정보 찾아오기
			if( member != null ) {// 일치하는 회원 있음: 로그인 성공
				HttpSession session = request.getSession();
				session.setAttribute("login", member.getM_id());
				session.setAttribute("email", member.getM_email());
				if( red == "" ){// 로그인 후 따로 이동할 페이지가 없으면 피드로 이동
					return "redirect:/feed";
				}
				else{// 있으면 그 페이지로 이동
					return "redirect:/"+red;
				}
			}else { // 로그인 실패
				rttr.addFlashAttribute("alert", "아이디와 비밀번호를 다시 확인해주세요.");
				return "redirect:/loginMove";
			}
		}
	}
	
	// 회원 정보보기창
	@GetMapping("/memberRead")
	public String memberRead(Model model, HttpSession session) {
		if( session.getAttribute("login") == null ) return "redirect:/loginMove?red=memberRead";// 비회원인 경우 로그인하러 가기
		MemberDTO dto = memberService.getMember_no( loginId(session) );
		// 프사 인코딩
		System.out.println("lwkController.memberRead ~ "+dto);
		model.addAttribute("dto",dto);
		return "member/memberRead";
	}
	
	// 회원 업데이트창
	@GetMapping("/memberUpdate")
	public String memberUpdate( Model model, HttpSession session ) {
		if( session.getAttribute("login") == null ) return "redirect:/loginMove?red=memberUpdate";// 비회원인 경우 로그인하러 가기
		MemberDTO dto = memberService.getMember_no( loginId(session) );
		model.addAttribute("dto",dto);
		return "member/memberUpdate";
	}
	
	// 회원정보 업데이트 하기
	//// 각 항목별로, 해당 항목이 빈칸이 아닐 경우에만 수정
	@PostMapping("/memberUpdate")
	public String memberUpdateProcess(
			@RequestParam(value="m_nickname", required = false) String m_nickname,
			@RequestParam(value="m_password", required = false) String m_password,
			@RequestParam(value="m_gender", required = false) String m_gender,
			@RequestParam(value="m_introduce", required = false) String m_introduce,
			@RequestParam(value="m_profilePic", required=false) MultipartFile m_profilePic,
			@RequestParam(value="isTherePropic", required=false) Boolean isTherePropic,// 파일을 선택하지 않아도 m_profilePic는 null이 아니기 때문에 파일 선택 여부를 따로 받음
			@RequestParam(value="eraseProfilepic", required=false) Boolean eraseProfilepic,
			HttpSession session) {
		//// null로 들어온 값을 기본값으로 설정
		if( eraseProfilepic == null ) eraseProfilepic = false;// 체크박스에 체크 안 하면 null로 들어온다.
		if( isTherePropic == null ) isTherePropic = false;
		//// 닉네임
		if( !m_nickname.equals("") ) {
			memberService.memberUpdate_nickname( loginId(session), m_nickname );
		}
		//// 비밀번호
		if( !m_password.equals("") ) {
			memberService.memberUpdate_password( loginId(session), m_password );
		}
		//// 성별
		if( m_gender.equals("M") || m_gender.equals("F") || m_gender.equals("X") ) {
			memberService.memberUpdate_gender( loginId(session), m_gender );
		}
		//// 자기소개
		if( !m_introduce.equals("") ) {
			memberService.memberUpdate_introduce( loginId(session), m_introduce );
		}
		//// 프사
		if( eraseProfilepic ) {// 프사를 지우기
			mapper.memberUpdate_eraseProfilePic( loginId(session) );
		}else if( isTherePropic ){// 선택한 파일로 프사 바꾸기// 파일을 업로드 한 경우에만
			try {
				mapper.memberUpdate_profilePic( loginId(session), m_profilePic.getBytes() );
			} catch (IOException e) {
				System.out.println("프사 바꾸기 실패");
				e.printStackTrace();
			}
		}
		return "redirect:/memberRead";// 업데이트 완료 후 회원정보 페이지로
	}
	
	@PostMapping("/emailCheck")
	@ResponseBody
	public String emailCheck(@RequestParam("m_email") String m_email, RedirectAttributes rttr) {
		String result = memberService.emailCheck(m_email);
		if (result.equals("useUser_email")) {
			System.out.println("lwkController.memberRead ~ user_emailbaaaaaaaaaaaad");
			rttr.addFlashAttribute("bad", false);
			return "bad";
		} else {
			System.out.println("lwkController.memberRead ~ user_emailgooooooooooooood");
			rttr.addFlashAttribute("good", true);
			return "good";
		}
	}
	

	//// 현재 로그인한 회원 번호(정수) 가져오기 // 비로그인 상태일 경우 0으로
	private static int loginId( HttpSession session ) {
		if( session.getAttribute("login") == null ) return 0;
		return Integer.parseInt( session.getAttribute("login")+"" );
	}
}


