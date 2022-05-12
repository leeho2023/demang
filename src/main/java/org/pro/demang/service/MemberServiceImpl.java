package org.pro.demang.service;

import java.util.List;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.CommentDTO;
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
	@Override
    public String emailCheck(String m_email) {
       String resultPW =  mapper.emailCheck(m_email);
       System.out.println("emailCheck 값 : "+resultPW);
       if(resultPW != null || m_email == "") {
          System.out.println("22222222");
          return "useUser_email";
         }else {
           System.out.println("111111");
             return "notUseUser_email";
       }
   }
	

	// 유저 검색
	@Override
	public List<MemberDTO> memberSearch(String reSearchVal) {
		return mapper.memberSearch(reSearchVal);
	}
	
	//// 댓글 등록 후 표시할 html 요소를 반환
	@Override
	public String commentInsert(CommentDTO dto) {
		mapper.commentInsert(dto);
		dto.setMemberDTO( getMember_no( dto.getC_writer() ) );// 댓글 작성자 번호로 회원 조회하여 정보에 넣기
		return "<img src=\"data:image/png;base64,"+dto.getMemberDTO().getM_profilePicString()+"\" height=30>"
				+ "<span>"+dto.getMemberDTO().getM_nickname()+"</span>"
				+ "<span>"+dto.getC_content()+"</span> 내가 쓴 댓글이 이렇게 표시됩니다. ~  MemberServiceImpl";
	}



}
