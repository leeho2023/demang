package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.AnswerDTO;

public interface MemberService {

	List<MemberDTO> fList(int follower);
	int memberInsert(MemberDTO dto);
	public MemberDTO getMember_no( String no );
	public MemberDTO getMember_no( int no );

	MemberDTO login(MemberDTO dto);
	void memberUpdate(MemberDTO dto);

    List<MemberDTO> memberSearch(String reSearchVal);
	public String emailCheck(String m_email);
    void contactUsInsert(ContactUsDTO dto);
	void contactUsImgInsert(int c_id, byte[] i_image);
	
	// admin 검색 관련
	int memberSearchCount(String search);
	int contactSearchCount(String search);
	List<ContactUsDTO> contactSearch(String search);
	List<MemberDTO> memberSearchAdmin(String search);
	List<ContactUsDTO> contactSearchAdmin(String search);
    void updateC_checked(String c_id);
	ContactUsDTO messageOneSelect(String c_id);
	AnswerDTO answerSelect(String c_id);




}
