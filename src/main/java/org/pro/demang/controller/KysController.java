package org.pro.demang.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.github.pagehelper.PageInfo;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.ChatDTO;
import org.pro.demang.model.OrderDTO;
import org.pro.demang.service.ChatService;
import org.pro.demang.service.MemberService;
import org.pro.demang.service.OrderService;
import org.pro.demang.service.PagingServiceImpl;
import org.pro.demang.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KysController {
	@Autowired private MemberService memberService;
	@Autowired private PostService postService;
	@Autowired private ChatService chatService;
	@Autowired private OrderService orderService;
	@Autowired private MainMapper mapper;
	@Autowired private PagingServiceImpl pagingServiceImpl;
	
	//// 주문 페이지
	@GetMapping("/order")
	String orderPage( @RequestParam("mer_id")int mer_id, @RequestParam("from")int p_id, Model model ) {
		//// ??? 로그인 안 돼있으면
		model.addAttribute(// 주문할 상품 정보
				"mer",
				orderService.getMerchandise(mer_id)
				);
		model.addAttribute( "backto", p_id );// 결제 완료 후 돌아갈 게시글 상세보기 페이지의 게시글 번호
		return "order/order";
	}
	//// 결제 페이지 (주문페이지에서 들어와서 새 주문 만들기)
	@PostMapping("/payment")
	String paymentPage_newOrder( OrderDTO dto, @RequestParam("backto")int p_id, Model model, RedirectAttributes rttr, HttpSession session ) {
		//// ??? 로그인 안 돼있으면
		//// 주문 정보 저장
		if( dto.getOrd_amount() > mapper.getMerAmount(dto.getOrd_target()) ) { // 주문할 수량이 남은 수량보다 크면
			System.out.println("ksy con ~ 주문 요청하신 수량이 현재 상품의 남은 수량보다 많습니다.");
			rttr.addFlashAttribute("alert", "주문 요청하신 수량이 현재 상품의 남은 수량보다 많습니다.");// 수량 초과 메시지와 함께
			return "redirect:/postView?p_id="+p_id;//이전 게시글로 돌아가기
		}
		orderService.newOrder( dto, loginId(session) );// 서비스에서 dto 완성
		//// 주문 정보 model에 넣기
		model.addAttribute("order", dto);// model에 주문 정보
		model.addAttribute( "backto", p_id );// 결제 완료 후 돌아갈 게시글 상세보기 페이지의 게시글 번호
		return "order/payment";
	}
	//// 결제 페이지 (주문 내역 페이지에서 결제 대기중인 주문 다시 결제하려고 들어옴)
	@GetMapping("/payment")
	String paymentPage_oldOrder( @RequestParam("order") String ord_id, @RequestParam("backto")int p_id, Model model, RedirectAttributes rttr, HttpSession session ) {
		model.addAttribute(// model에 주문 정보
				"order", 
				mapper.getOrder(ord_id)
				);
		return "order/payment";
	}
	
	/// 결제 시도 ???
	@PostMapping("pay")
	@ResponseBody
	String pay( @RequestParam("imp_uid") String imp_uid, @RequestParam("merchant_uid") String ord_id  ) {
		if( orderService.paymentVerify( imp_uid, ord_id ) ) {// 결제 검증 통과하면
			orderService.paymentValidate( ord_id );// 디비에 결제 완료 표시
			return "O";
		}else {
			return "X";
		}
	}
	
	//// 주문 내역 확인 페이지
	@GetMapping("/orderlist")
	String orderlist(@RequestParam(required = false, defaultValue = "1") int pageNum,
		Model model, HttpSession session ) {
		if( loginId(session) == 0 ) return "redirect:/loginMove?red=orderlist";// 비회원인 경우 로그인하러 가기
		PageInfo<OrderDTO> p = new PageInfo<>(pagingServiceImpl.getOrderList(pageNum, loginId(session)), 10);
		model.addAttribute("orderList", p);
		return "order/orderList";
	}
	
	
	
	//// 채팅 페이지
	@GetMapping("/chat")
	String chattingPage( @RequestParam("to") int listener, Model model, HttpSession session ){
		if( loginId(session) == 0 ) return "redirect:/loginMove?red=chat?to="+listener;// 비회원인 경우 로그인하러 가기
		model.addAttribute(// 상대방 정보
				"listener", 
				mapper.getMember_no( listener ) 
				);
		model.addAttribute(
				"lastH_id",
				0 );
		return "chat/chattingPage";
	}
	
	//// 채팅 (ajax로 모달 만들기용)
	@GetMapping("/chatModal")
	String chatting( @RequestParam("to") int listener, Model model, HttpSession session ){
		model.addAttribute(// 상대방 정보
				"listener", 
				mapper.getMember_no( listener ) 
				);
		model.addAttribute(
				"lastH_id",
				0 );
		return "chat/chatting";
	}
	
	//// 채팅 보내기 (ajax용)
	@PostMapping("/chat/send")
	@ResponseBody
	String chat_send( @RequestParam("to") int listener, @RequestParam("h_content") String h_content, HttpSession ss ) {
		chatService.chatSend( 
				new ChatDTO( loginId(ss), listener, h_content ) );
		return "";
	}
	
	//// (채팅 내역 새로고침 용) 특정 번호 다음의 채팅만 불러오기
	@PostMapping("/chat/refresh")
	String chat_refresh( @RequestParam("to") int listener, @RequestParam("since") int since, HttpSession session, Model model ) {
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
	String chat_history( @RequestParam("to") int listener, Model model, HttpSession session ){
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
	

	//// 현재 로그인한 회원 번호(정수) 가져오기 // 비로그인 상태일 경우 0으로
	private static int loginId( HttpSession session ) {
		if( session.getAttribute("login") == null ) return 0;
		return Integer.parseInt( session.getAttribute("login")+"" );
	}
}
