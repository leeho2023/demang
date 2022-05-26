package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderDTO {
	private String ord_id;
	private int ord_buyer;
	private int ord_target;
	private String ord_target_name;
	private int ord_amount;
	private String ord_buyer_name;
	private String ord_buyer_email;
	private String ord_buyer_tel;
	private String ord_buyer_address;
	private String ord_buyer_postcode;
	private int ord_price;
	private Timestamp ord_date;
	private char ord_state;

	/*public OrderDTO(int ord_target, String mer_name, int mer_price, int mer_amount) {
		this.ord_target = ord_target;
		this.ord_target_name = mer_name;
		this.ord_price = mer_price;
		this.ord_amount = mer_amount;
	}*/
}
