package org.pro.demang.service;

import java.util.List;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MainMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private BCryptPasswordEncoder pwEncoder;
	
	//친구목록불러오기
	@Override
	public List<MemberDTO> fList(int follower) {
		return mapper.fList(follower);
	}
	
	// 회원가입
	@Override
	public void memberInsert(MemberDTO dto) {
		
		String encodedPassword = passwordEncoder.encode(dto.getM_password());
		dto.setM_password(encodedPassword);
		mapper.memberInsert(dto);	
	}

	//// 회원번호로 회원 찾기
	@Override
	public MemberDTO getMember_no( String no ) {
		MemberDTO dto = mapper.getMember_no(no);
		return dto;
	}
	@Override
	public MemberDTO getMember_no( int no ) {
		return getMember_no(""+no);
	}
	
	//// 로그인
	//// 이메일, 비밀번호만 들어있는 dto를 받아서 그에 해당하는 회원이 있는 경우 해당 회원의 dto반환, 아니면 null.
	@Override
	public MemberDTO login(MemberDTO dto) {
		MemberDTO member = mapper.getMember_email( dto.getM_email() );// 입력된 이메일로 회원정보 찾기
		if( member == null ) {return null;}// 해당 이메일이 디비에 없는 경우: 찾는 회원 없음.
		if( pwEncoder.matches(// 비밀번호가 일치하는 경우
				dto.getM_password(), member.getM_password())) {
			return member;
		}else {// 일치하지 않으면 null
			return null;
		}
	}

	@Override
	public void memberUpdate(MemberDTO dto) {
		String encodedPassword = passwordEncoder.encode(dto.getM_password());
		dto.setM_password(encodedPassword);
		mapper.memberUpdate(dto);
		
	}

	

}
