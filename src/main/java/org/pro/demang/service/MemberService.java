package org.pro.demang.service;

import java.util.List;

import javax.mail.MessagingException;

import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.AnswerDTO;

public interface MemberService {

	List<MemberDTO> fList( int follower );
	String memberInsert( MemberDTO dto, String e_code );
	MemberDTO getMember_no( int no );
	boolean pwmatch( String fromInput, String fromDB  );
	boolean memberPwmatch(int loginId, String password);
	void memberWithdraw(int loginId);// 탈퇴
	
	void sendMailCode(String m_email) throws MessagingException;
	boolean emailDuplicateCheck(String m_email);// 이메일 중복 체크 (중복 없으면 true 중복이면 false)
	boolean tempEmailDuplicateCheck(String m_email);
	boolean emailVerifyCheck( String email, String code );

	String answerInsert(String m_email, AnswerDTO dto) throws MessagingException;

	MemberDTO login(MemberDTO dto);
	void memberUpdate_nickname(int loginId, String m_nickname);
	void memberUpdate_password(int loginId, String m_password);
	void memberUpdate_gender(int loginId, String m_gender);
	void memberUpdate_introduce(int loginId, String m_introduce);

    List<MemberDTO> memberSearch(String reSearchVal);
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
