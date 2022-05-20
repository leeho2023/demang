package org.pro.demang.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.ChatDTO;
import org.pro.demang.model.OrderDTO;
import org.pro.demang.service.ChatService;
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
public class KysController {
	@Autowired private MemberService memberService;
	@Autowired private PostService postService;
	@Autowired private ChatService chatService;
	@Autowired private OrderService orderService;
	@Autowired private MainMapper mapper;
	
	//// 주문 페이지
	@GetMapping("/order")
	String orderPage( @RequestParam("p_id")int p_id, Model model ) {
		//// ??? 로그인 안 돼있으면
		model.addAttribute(// 결제할 게시물
				"post", 
				mapper.getPost(p_id)
				);
		return "order/order";
	}
	//// 결제 페이지
	@PostMapping("/payment")
	String paymentPage( OrderDTO dto, Model model, HttpSession session ) {
		//// ??? 로그인 안 돼있으면
		//// 주문 정보 저장
		//// 주문 생성 전, 마지막으로 남은 상품 수량 확인 ???
		if( dto.getOrd_amount() > mapper.getMerAmount(dto.getOrd_target()) ) return "redirect:/memberRead";// 주문할 수량이 남은 수량보다 크다. ??? 또 일단 회원정보페이지로 ㅣ동
		orderService.newOrder( dto, loginId(session) );// 서비스에서 dto 완성
		//// 주문 정보 model에 넣기
		model.addAttribute("order", dto);
		return "order/payment";
	}
	/// 결제 시도 ???
	@PostMapping("pay")
	@ResponseBody
	String pay( @RequestParam("imp_uid") String imp_uid, @RequestParam("merchant_uid") String ord_id  ) {
		System.out.println("kys control ~ imp_uid: "+imp_uid);
		System.out.println("kys control ~ ord_id:  "+ord_id);
		
		if( orderService.paymentVerify( imp_uid, ord_id ) ) {// 결제 검증
			orderService.paymentValidate( ord_id );// 디비에 결제 완료 표시
			return "O";
		}else {
			return "X";
		}
	}
	
	
	
	//// 채팅 페이지
	@GetMapping("/chat")
	String chattingPage( @RequestParam("to") String listener, Model model, HttpSession session ){
		if( session.getAttribute("login") == null ) return "redirect:/loginMove?red=chat?to="+listener;// 비회원인 경우 로그인하러 가기
		if( listener == null ) return "post/feed";
		model.addAttribute(// 상대방 정보
				"listener", 
				mapper.getMember_no(listener) 
				);
		model.addAttribute(
				"lastH_id",
				0 );
		return "chat/chattingPage";
	}
	
	//// 채팅 (ajax로 모달 만들기용)
	@GetMapping("/chatModal")
	String chatting( @RequestParam("to") String listener, Model model, HttpSession session ){
		model.addAttribute(// 상대방 정보
				"listener", 
				mapper.getMember_no(listener) 
				);
		model.addAttribute(
				"lastH_id",
				0 );
		return "chat/chatting";
	}
	
	//// 채팅 보내기 (ajax용)
	@PostMapping("/chat/send")
	@ResponseBody
	String chat_send( @RequestParam("to") String listener, @RequestParam("h_content") String h_content, HttpSession ss ) {
		chatService.chatSend( 
				new ChatDTO( loginId(ss), listener, h_content ) );
		return "";
	}
	
	//// (채팅 내역 새로고침 용) 특정 번호 다음의 채팅만 불러오기
	@PostMapping("/chat/refresh")
	String chat_refresh( @RequestParam("to") String listener, @RequestParam("since") int since, HttpSession session, Model model ) {
		List<ChatDTO> list = chatService.chatHistory( loginId(session), listener, since );
		if( list.size() <= 0 ) {return "empty";}
		model.addAttribute(// 채팅 내역
				"chatList", 
				list
				);
		model.addAttribute(
				"lastH_id",
				list.get( list.size()-1 ).getH_id() );// 가장 최근 메시지의 h_id
		return "chat/chat";
	}
	
	//// 채팅 전체 내역 확인
	@GetMapping("/chat/history")
	String chat_history( @RequestParam("to") String listener, Model model, HttpSession session ){
		if( session.getAttribute("login") == null ) return "redirect:/loginMove?red=chat/history?to="+listener;// 비회원인 경우 로그인하러 가기
		model.addAttribute(// 상대방 정보
				"listener", 
				mapper.getMember_no(listener) 
				);
		model.addAttribute(// 전체 채팅 내역
				"chatList", 
				chatService.chatHistory( loginId(session), listener, 0 ) 
				);
		return "chat/history";
	}
	
	
	//// 현재 로그인한 회원 번호(문자열로) 가져오기
	private static String loginId( HttpSession session ) {
		return session.getAttribute("login")+"";
	}
}
