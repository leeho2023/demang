package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.MemberDTO_ext;

public interface MemberService {

	List<MemberDTO> fList(int follower);
	void memberInsert(MemberDTO dto);
	public MemberDTO getMember_no( String no );
	public MemberDTO getMember_no( int no );
	public MemberDTO_ext getMember_ext( String code );
	
}
