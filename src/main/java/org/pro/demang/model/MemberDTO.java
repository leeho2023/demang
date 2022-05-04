package org.pro.demang.model;

import java.sql.Timestamp;

import lombok.Data;


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
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getM_email() {
		return m_email;
	}
	public void setM_email(String m_email) {
		this.m_email = m_email;
	}
	public String getM_nickname() {
		return m_nickname;
	}
	public void setM_nickname(String m_nickname) {
		this.m_nickname = m_nickname;
	}
	public String getM_password() {
		return m_password;
	}
	public void setM_password(String m_password) {
		this.m_password = m_password;
	}
	public Timestamp getM_birth() {
		return m_birth;
	}
	public void setM_birth(Timestamp m_birth) {
		this.m_birth = m_birth;
	}
	public String getM_introduce() {
		return m_introduce;
	}
	public void setM_introduce(String m_introduce) {
		this.m_introduce = m_introduce;
	}
	public String getM_profilePic() {
		return m_profilePic;
	}
	public void setM_profilePic(String m_profilePic) {
		this.m_profilePic = m_profilePic;
	}
	public String getM_gender() {
		return m_gender;
	}
	public void setM_gender(String m_gender) {
		this.m_gender = m_gender;
	}
	public boolean isM_licensed() {
		return m_licensed;
	}
	public void setM_licensed(boolean m_licensed) {
		this.m_licensed = m_licensed;
	}
	public int getM_warnCount() {
		return m_warnCount;
	}
	public void setM_warnCount(int m_warnCount) {
		this.m_warnCount = m_warnCount;
	}
	public boolean isM_available() {
		return m_available;
	}
	public void setM_available(boolean m_available) {
		this.m_available = m_available;
	}
	public Timestamp getM_regDate() {
		return m_regDate;
	}
	public void setM_regDate(Timestamp m_regDate) {
		this.m_regDate = m_regDate;
	}
	@Override
	public String toString() {
		return "MemberDTO [m_id=" + m_id + ", m_email=" + m_email + ", m_nickname=" + m_nickname + ", m_password="
				+ m_password + ", m_birth=" + m_birth + ", m_introduce=" + m_introduce + ", m_profilePic="
				+ m_profilePic + ", m_gender=" + m_gender + ", m_licensed=" + m_licensed + ", m_warnCount="
				+ m_warnCount + ", m_available=" + m_available + ", m_regDate=" + m_regDate + "]";
	}
	
	
	
	
	
	
	
	
}
