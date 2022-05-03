package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberDTO {

	private int m_id;
	private String m_email;
	private String m_nickname;
	private String m_password;
	private Timestamp m_birth;
	private String m_introduce;
	private String m_profilePic;
	private String m_gender;
	private boolean m_licensed;
	private int m_warnCount;
	private boolean m_available;
	private Timestamp m_regDate;
	
}
