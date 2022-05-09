package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.MemberDTO;

public interface MemberService {

	List<MemberDTO> fList(int follower);
	void memberInsert(MemberDTO dto);
	public MemberDTO getMember_no( String no );
	public MemberDTO getMember_no( int no );
    List<MemberDTO> memberSearch(String searchVal);
	
}
