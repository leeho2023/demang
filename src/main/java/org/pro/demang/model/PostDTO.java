package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class PostDTO {

	private int p_id;
	private int p_origin;
	private String p_type;
	private int p_writer;
	private String p_content;
	private Timestamp p_regDate;

	private MemberDTO memberDTO;

	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public int getP_origin() {
		return p_origin;
	}
	public void setP_origin(int p_origin) {
		this.p_origin = p_origin;
	}
	public String getP_type() {
		return p_type;
	}
	public void setP_type(String p_type) {
		this.p_type = p_type;
	}
	public int getP_writer() {
		return p_writer;
	}
	public void setP_writer(int p_writer) {
		this.p_writer = p_writer;
	}
	public String getP_content() {
		return p_content;
	}
	public void setP_content(String p_content) {
		this.p_content = p_content;
	}
	public Timestamp getP_regDate() {
		return p_regDate;
	}
	public void setP_regDate(Timestamp p_regDate) {
		this.p_regDate = p_regDate;
	}
	
	
	
}
