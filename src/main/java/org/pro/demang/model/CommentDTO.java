package org.pro.demang.model;

import java.sql.Timestamp;
import java.util.Base64;

import lombok.Data;

public class CommentDTO {
	
	private int m_id;// 작성자 아이디
	private String m_nickname; // 작성자 닉네임(memberTBL join 칼럼)
	private String m_code;
	private byte[] m_profilePic;
	private int c_id;
	private String c_content;
	private int c_postNo;
	private Timestamp c_regDate;
	
	
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getM_nickname() {
		return m_nickname;
	}
	public void setM_nickname(String m_nickname) {
		this.m_nickname = m_nickname;
	}
	public byte[] getM_profilePic() {
		return m_profilePic;
	}
	//// 뷰에서 이미지 불러오기용: 바이트 배열의 이미지를 Base64를 통해 인코딩한 뒤 문자열로 반환. 뷰에서 타임리프로 <img th:src="'data:image/png;base64,'+${dto.m_profilePicString}">등으로 쓰면 된다.
	//// MemberDTO에서와 같은 방식이다.
	public String getM_profilePicString() {
		return new String(Base64.getEncoder().encodeToString(m_profilePic));
	}
	public void setM_profilePic(byte[] m_profilePic) {
		this.m_profilePic = m_profilePic;
	}
	public String getM_code() {
		return m_code;
	}
	public void setM_code(String m_code) {
		this.m_code = m_code;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public int getC_postNo() {
		return c_postNo;
	}
	public void setC_postNo(int c_postNo) {
		this.c_postNo = c_postNo;
	}
	public Timestamp getC_regDate() {
		return c_regDate;
	}
	public void setC_regDate(Timestamp c_regDate) {
		this.c_regDate = c_regDate;
	}
	
}
