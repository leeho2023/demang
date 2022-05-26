package org.pro.demang.service;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.AnswerDTO;
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
	public int memberInsert(MemberDTO dto) {
		
		boolean eCheck = signUpEmailCheck(dto.getM_email());
		boolean nCheck = signUpNicknameCheck(dto.getM_nickname());
		boolean pCheck = signUpPasswordCheck(dto.getM_password());
		
		if(eCheck && nCheck && pCheck) {
			String code;
			do{
				code = createCode();
			}while(codeCheck(code));

			dto.setM_code(code);
			
			String encodedPassword = passwordEncoder.encode(dto.getM_password());
			dto.setM_password(encodedPassword);
			mapper.memberInsert(dto);
			mapper.emailAuthenticationDelete(dto.getM_email());
			return 1;
		}else {
			
			if(!eCheck) {
				return 2;
			}else if(!nCheck) {
				return 3;
			}else {
				return 4;
			}
		}
		
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
		// 해당 이메일이 디비에 없는 경우: 찾는 회원 없음.
		if( member == null ) {
			return null;
			}
		// 비밀번호가 일치하는 경우
		if(pwEncoder.matches(dto.getM_password(), member.getM_password())) {
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
          return "useUser_email";
         }else {
             return "notUseUser_email";
       }
   }
	

	// 유저 검색
	@Override
	public List<MemberDTO> memberSearch(String reSearchVal) {
		return mapper.memberSearch(reSearchVal);
	}
	


	// 문의하기 DB등록
	@Override
	public void contactUsInsert(ContactUsDTO dto) {
		mapper.contactUsInsert(dto);
	}

	
	public boolean signUpEmailCheck(String m_email) {
		boolean err = false;
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(m_email); 
		if(m.matches()) { err = true; 
		} 
		return err;

		
	}
	
	public boolean signUpNicknameCheck(String m_nickname) {
		boolean err = false;
		String regex = "^[a-zA-Zㄱ-힣0-9-_.]{2,20}$";
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(m_nickname); 
		if(m.matches()) { err = true; 
		} 
		return err;
	}
	
	public boolean signUpPasswordCheck(String m_password) {
		boolean err = false;
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(m_password); 
		if(m.matches()) { err = true; 
		} 
		return err;
	}

	// 문의하기 사진 DB등록
	@Override
	public void contactUsImgInsert(int c_id, byte[] i_image) {
		mapper.contactUsImgInsert(c_id, i_image);
	}


	// 회원 코드 중복 체크
	public boolean codeCheck(String code) {
		int check = mapper.codeCheck(code);
		if(check == 0){
			return false;
		}else{
			return true;
		}
	}

	// 회원 코드 생성 함수
	public String createCode(){
		final char[] possibleCharacters = {
			'1','2','3','4','5','6','7','8','9','0','A','B','C','D','E','F',
     		'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V',
     		'W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l',
			'm','n','o','p','q','r','s','t','u','v','w','x','y','z'
		};

		final int possibleCharacterCount = possibleCharacters.length;
		Random rnd = new Random();
		int i = 0;
		StringBuffer buf = new StringBuffer();
		for(i = 0; i < 4; i++){
			buf.append(possibleCharacters[rnd.nextInt(possibleCharacterCount)]);
		}
		String code = buf.toString();
		System.out.println("생성된 회원 코드 -> " + code);

		return code;
	}

//	admin 페이지 검색된 유저 수
	@Override
	public int memberSearchCount(String search) {
		return mapper.memberSearchCount(search);
	}

//	admin 페이지 검색된 문의 수
	@Override
	public int contactSearchCount(String search) {
		return mapper.contactSearchCount(search);
	}
//  admin 페이지 검색된 문의 3개
	@Override
	public List<ContactUsDTO> contactSearch(String search) {
		return null;
	}

	@Override
	public List<MemberDTO> memberSearchAdmin(String search) {
		return mapper.memberSearchAdmin(search);
	}

	@Override
	public List<ContactUsDTO> contactSearchAdmin(String search) {
		return mapper.contactSearchAdmin(search);
	}

	@Override
	public void updateC_checked(String c_id) {
		mapper.updateC_checked(c_id);
	}

	@Override
	public ContactUsDTO messageOneSelect(String c_id) {
		
		return mapper.messageOneSelect(c_id);
	}

	@Override
	public AnswerDTO answerSelect(String c_id) {
		return mapper.answerSelect(c_id);
	}
	
	
}
