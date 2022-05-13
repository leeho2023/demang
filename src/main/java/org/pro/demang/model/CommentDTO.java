package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentDTO {
	private int c_id;
	private int c_writer;
	private String c_content;
	private int c_postNo;
	private Timestamp c_regDate;
	private MemberDTO memberDTO;
}
