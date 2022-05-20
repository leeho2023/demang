package org.pro.demang.model;

import lombok.Data;

@Data
public class MerchandiseDTO {

	private int mer_id;
	private int mer_target;
	private String mer_name;
	private int mer_price;
	private int mer_amount;
	private int mer_count;

	public MerchandiseDTO(int mer_target, String mer_name, int mer_price, int mer_amount) {
		this.mer_target = mer_target;
		this.mer_name = mer_name;
		this.mer_price = mer_price;
		this.mer_amount = mer_amount;
	}
}
