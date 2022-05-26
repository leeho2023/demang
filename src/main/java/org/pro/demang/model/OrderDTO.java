package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderDTO {
	private String ord_id;// 주문 번호: ord-상품번호-주문시도횟수
	private int ord_buyer;// 구매자 (회원 번호)
	private int ord_target;// 주문 대상 상품 번호
	private MerchandiseDTO targetDTO;// 주문 대상 상품 정보
	private int ord_amount;// 수량
	private String ord_buyer_name;// 구매자 이름
	private String ord_buyer_email;// 구매자 이메일
	private String ord_buyer_tel;// 구매자 연락처
	private String ord_buyer_address;// 배송지 주소
	private String ord_buyer_postcode;// 배송지 우편번호
	private int ord_price;// 결제 가격 (단가 아님)
	private Timestamp ord_date;// 주문일자
	private char ord_state;// O: 상태 결제완료 / A: 결제대기 / X: 결제실패
}
