package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.AnswerDTO;

public interface MemberService {

	List<MemberDTO> fList( int follower );
	int memberInsert( MemberDTO dto );
	public MemberDTO getMember_no( int no );

	MemberDTO login(MemberDTO dto);
	void memberUpdate_nickname(int loginId, String m_nickname);
	void memberUpdate_password(int loginId, String m_password);
	void memberUpdate_gender(int loginId, String m_gender);
	void memberUpdate_introduce(int loginId, String m_introduce);

    List<MemberDTO> memberSearch(String reSearchVal);
	public String emailCheck(String m_email);
    void contactUsInsert(ContactUsDTO dto);
	void contactUsImgInsert(int c_id, byte[] i_image);

	
	// admin 페이지 관련
	int memberSearchCount(String search);
	int contactSearchCount(String search);
	List<ContactUsDTO> contactSearch(String search);
	List<MemberDTO> memberSearchAdmin(String search);
	List<ContactUsDTO> contactSearchAdmin(String search);
    void updateC_checked(String c_id);
	ContactUsDTO messageOneSelect(String c_id);
	AnswerDTO answerSelect(String c_id);
	void warnCountUp(String m_id);
	int getWarnCount(String m_id);
	void warnCountDown(String m_id);




}
