package org.pro.demang.service;

import java.util.List;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.MerchandiseDTO;
import org.pro.demang.model.OrderDTO;
import org.pro.demang.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired private MainMapper mapper;
	
	//// 상품 정보 가져오기 (수량을 주문가능수량으로 대체)
	@Override
	public MerchandiseDTO getMerchandise( int mer_id ) {
		MerchandiseDTO dto = mapper.getMerchandise(mer_id);
		replace_merAmount_with_orderableAmount(dto);
		return dto;
	}
	//// 게시글의 상품 목록 가져오기 (수량을 주문가능수량으로 대체)
	@Override
	public List<MerchandiseDTO> getMerchandiseList( int p_id ) {
		List<MerchandiseDTO> list = mapper.getMerchandiseList(p_id);
		for( MerchandiseDTO dto: list ) {
			replace_merAmount_with_orderableAmount(dto);
		}
		return list;
	}
	private void replace_merAmount_with_orderableAmount( MerchandiseDTO dto ) {
		dto.setMer_amount( mapper.getMerAmount( dto.getMer_id() ) );
	}
	
	//// 새 주문 생성
	@Override
	public void newOrder( OrderDTO dto, String loginId ) {
		int mer_id = dto.getOrd_target();// 상품 번호
		MerchandiseDTO merdto = mapper.getMerchandise( mer_id );// 주문할 상품
		//// 주문 시도 횟수 +1
		mapper.merCountUp( mer_id );
		//// 주문 dto 완성
		dto.setOrd_id("ord-"+dto.getOrd_target()+"-"+mapper.getMerCount(mer_id));// 주문 번호 만들기
		dto.setOrd_target_name( merdto.getMer_name() );// 주문 정보에 상품 이름 넣기
		dto.setOrd_price( merdto.getMer_price() * dto.getOrd_amount() );// 가격 = 해당 상품의 단가 * 구매자가 입력한 수량 ???
		dto.setOrd_buyer( loginId );// 구매자 회원번호
		// 주문 시각은 DB에서 자동 생성
		//// 주문을 디비에 저장 (실제 결제 시 결제 정보 위변조 확인용)
		mapper.orderInsert(dto);
	}
	

	//// 결제 검증
	@Override
	public boolean paymentVerify(String imp_uid, String ord_id) {
		///// 디비에 저장된 주문 금액이랑 실제 결제 금액을 비교 ??? 어려워서 생략 … 지금은 무조건 true 반환
		return true;
	}
	
	//// 결제 완료: 디비에 표시
	@Override
	public void paymentValidate(String ord_id) {
		mapper.ordComplete(ord_id);// 주문 상태: O(결제완료)
		mapper.merSubtract(ord_id);// 주문한 수만큼 상품 수량 차감
	}




	
	
	
	//// 아임포트 서버에서 실제 결제된 금액 조회 ??? 미완성
	private int getPayedPrice(String imp_uid) {
		
		
		/* 토큰 발급도 못 했어 아직*/
		
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "https://api.iamport.kr/users/getToken";
		//// 헤더
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("Content-Type", "application/json");
		//// 바디
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("imp_key", "7383674159115542");
		body.add("imp_secret", "783254872209d50bff0f5947edb867337ed7a395bf2e5462e1db82b28d24015db65e3b5f1e780d72");
		//// 요청
		HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);
		System.out.println("@@@@@@@@@@@@@@@@@@ 1");
		HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

		System.out.println("@@@@@@@@@@@@@@@@@@ 2");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		
		System.out.println("getPayedPrice() ~ 토큰 발급: "+response.getBody());
		
//		try {
//			objectMapper.readValue(response.getBody(), PostDTO.class );// ??? 무슨 DTO
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}

		return 0;
	}
}
