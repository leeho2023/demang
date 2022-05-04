package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentDTO {

	private String m_nickname; // 작성자 닉네임(memberTBL join 칼럼)
	private byte[] m_profilePic;
	private String m_code;
	private int c_id;
	private String c_content;
	private int c_postNo;
	private Timestamp c_regDate;
	
}
