package org.pro.demang.service;

import java.util.List;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.MemberDTO_ext;
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
		return mapper.getMember_no(no);
	}
	@Override
	public MemberDTO getMember_no( int no ) {
		return mapper.getMember_no(no);
	}
	//// 회원코드로 회원 찾기 +추가정보(팔로잉, 팔로워 수)
	//// 못 찾으면 null
	@Override
	public MemberDTO_ext getMember_ext( String code ) {
		try{MemberDTO member = mapper.getMember_code( code );
			return new MemberDTO_ext(
					member,// 찾는 멤버
					mapper.followingCount( member.getM_id()),// 팔로잉 수
					mapper.followerCount( member.getM_id())// 팔로워 수
					);
		}catch(Exception e) {
			System.out.println("no found");
			return null;
		}
	}
	@Override
	public String login(MemberDTO dto) {
		
		String resultPW = mapper.getRealPassword(dto.getM_email());
		System.out.println("resultPW 값 : "+resultPW);
		boolean loginFilter = pwEncoder.matches(dto.getM_password(),resultPW);
		if(loginFilter) {
			return "Success";
		}else {
			return "Fail";
		}
	}

}
