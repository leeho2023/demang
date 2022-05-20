package org.pro.demang.service;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired private MainMapper mapper;

	@Override
	public void newOrder( OrderDTO dto, String loginId ) {
		int mer_id = dto.getOrd_target();// 상품 번호
		//// 주문 시도 횟수 +1 ???
		mapper.merCountUp( mer_id );
		//// dto 완성
		dto.setOrd_id("ord-"+dto.getOrd_target()+"-"+mapper.getMerCount(mer_id));// 주문 번호
		dto.setOrd_target_name( mapper.getMerName(mer_id) );// 상품 이름
		dto.setOrd_price( mapper.getMerPrice(mer_id) * dto.getOrd_amount() );// 가격 = 해당 상품의 단가 * 구매자가 입력한 수량 ???
		dto.setOrd_buyer( loginId );// 구매자 회원번호
		// 주문 시각은 DB에서 자동 생성
		//// 주문을 디비에 저장 (실제 결제 시 결제 정보 위변조 확인용)
		mapper.orderInsert(dto);
	}
}
