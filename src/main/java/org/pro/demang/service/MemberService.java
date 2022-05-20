package org.pro.demang.service;

import java.util.ArrayList;
import java.util.List;

import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;

public interface MemberService {

	List<MemberDTO> fList(int follower);
	void memberInsert(MemberDTO dto);
	public MemberDTO getMember_no( String no );
	public MemberDTO getMember_no( int no );
	public MemberDTO getMember_no( Object no );

	MemberDTO login(MemberDTO dto);
	void memberUpdate(MemberDTO dto);

    List<MemberDTO> memberSearch(String reSearchVal);
	String commentInsert(CommentDTO dto);
	public String emailCheck(String m_email);
    void contactUsInsert(ContactUsDTO dto);
	void contactUsImgInsert(int c_id, byte[] i_image);
	List<ContactUsDTO> messageList(int c_id);
    ArrayList<Integer> contactAllNumCount();


	
	

}
