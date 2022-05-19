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
	
	public void setOrd_buyer( String ord_buyer ) {
		this.ord_buyer = Integer.parseInt(ord_buyer);
	}
}
