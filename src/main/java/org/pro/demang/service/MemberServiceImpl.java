package org.pro.demang.service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.AnswerDTO;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MainMapper mapper;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	public String memberInsert( MemberDTO dto, String e_code ) {
		
		boolean eCheck = signUpEmailCheck(dto.getM_email());
		boolean cCheck = emailVerifyCheck( dto.getM_email(), e_code );
		boolean nCheck = signUpNicknameCheck(dto.getM_nickname());
		boolean pCheck = signUpPasswordCheck(dto.getM_password());
		
		String errmsg = "";
		if( !eCheck )
			errmsg += "이메일이 형식에 맞지 않습니다.\n";
		if( !cCheck )
			errmsg += "이메일 인증에 실패하였습니다.\n";
		if( !nCheck )
			errmsg += "이름이 형식에 맞지 않습니다.\n";
		if( !pCheck )
			errmsg += "비밀번호가 형식에 맞지 않습니다.\n";
		
		if( !errmsg.equals("") ) {// 오류메시지 있음 = 유효성 검사 탈락
			return errmsg;
		}
		
		//// 유효성 검사 통과
		//// 코드 생성
		String code;
		do{
			code = createMemberCode();
		}while(codeCheck(code));
		dto.setM_code(code);
		//// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(dto.getM_password());
		dto.setM_password(encodedPassword);
		//// DB에 등록
		mapper.memberInsert(dto);
		mapper.emailAuthenticationDelete(dto.getM_email());// 이메일 인증 테이블에서 삭제
		return "";
	}
	
	// 입력된 이메일과 생성된 인증코드를 DB에 등록하고 해당 메일로 인증코드 발송
	@Override
	public void sendMailCode(String m_email) throws MessagingException {
		String emailCheckCode = createMailCode();// 인증코드 생성
		System.out.println("생성된 인증 코드: " + emailCheckCode);
		//// DB에 인증코드 넣기 ??? 해당 이메일이 이미 DB에 있으면 update, 없으면 insert
		if( tempEmailDuplicateCheck(m_email) ) {
			mapper.emailCodeInsert( m_email, emailCheckCode );}
		else {
			mapper.emailCodeUpdate( m_email, emailCheckCode );}
		//// 메일 발송
		MimeMessage message = javaMailSender.createMimeMessage();
		message.setSubject("demang 가입을 환영합니다.");
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(m_email));
		message.setText("demang 회원 가입 인증 코드 ==>" + emailCheckCode);
		message.setSentDate(new Date());
		javaMailSender.send(message);
	}
	
	// 회원 테이블에서 이메일 중복 체크
	@Override
	public boolean emailDuplicateCheck(String m_email) {
		if( mapper.emailDuplicateCheck(m_email) > 0 )
			return false;// 중복 있음
		else
			return true;// 중복 없음
	}
	// 인증 테이블에서 이메일 중복 체크
	@Override
	public boolean tempEmailDuplicateCheck(String m_email) {
		if( mapper.tempEmailDuplicateCheck( m_email ) > 0 )
			return false;// 중복 있음
		else
			return true;// 중복 없음
	}

	// 이메일 인증코드 검증 (true: 검증됨)
	@Override
	public boolean emailVerifyCheck( String email, String code ) {
		if( mapper.emailVerifyCheck( email, code ) > 0 )
			return true;
		else
			return false;
	}
	

	//// 회원번호로 회원 찾기
	@Override
	public MemberDTO getMember_no( int no ) {
		MemberDTO dto = mapper.getMember_no(no);
		return dto;
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
		if( pwmatch(dto.getM_password(), member.getM_password()) ) {
			return member;
		}else {// 일치하지 않으면 null
			return null;
		}
	}
	
	
	
	//// 회원정보 수정
	////// 닉네임 수정
	@Override
	public void memberUpdate_nickname(int loginId, String m_nickname) {
		mapper.memberUpdate_nickname(loginId, m_nickname.trim());// 입력값 trim
	}
	////// 비밀번호 수정
	@Override
	public void memberUpdate_password(int loginId, String m_password) {
		String encodedPassword = passwordEncoder.encode(m_password);
		mapper.memberUpdate_password(
				loginId, 
				passwordEncoder.encode(// 암호화된 비밀번호
						m_password.trim())// 입력값 trim
				);
	}
	////// 성별 수정
	@Override
	public void memberUpdate_gender(int loginId, String m_gender) {
		mapper.memberUpdate_gender(loginId, m_gender);
	}
	////// 자기소개 수정
	@Override
	public void memberUpdate_introduce(int loginId, String m_introduce) {
		mapper.memberUpdate_introduce(loginId, m_introduce.trim());// 입력값 trim
	}
	

	
	
	//// 탈퇴
	@Override
	public void memberWithdraw(int loginId) {
		mapper.memberWithdraw(loginId);
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

	// 문의하기 사진 DB등록
	@Override
	public void contactUsImgInsert(int c_id, byte[] i_image) {
		mapper.contactUsImgInsert(c_id, i_image);
	}

	// 문의 답변 하기
	@Override
	public String answerInsert(String m_email, AnswerDTO dto) throws MessagingException {
		
		mapper.answerInsert(dto);
		
		MimeMessage message = javaMailSender.createMimeMessage();
	    message.setSubject("고객님 문의하신 내용은 확인 했습니다.");
	    message.setRecipient(Message.RecipientType.TO, new InternetAddress(m_email));
	    message.setText(dto.getA_content());
	    message.setSentDate(new Date());
	    javaMailSender.send(message);
		
		
		return null;
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

	// 문의 확인 했는지 업데이트
	@Override
	public void updateC_checked(String c_id) {
		mapper.updateC_checked(c_id);
	}

	// 문의 내용 하나만 가져오기
	@Override
	public ContactUsDTO messageOneSelect(String c_id) {
		
		return mapper.messageOneSelect(c_id);
	}

	// 문의에 답변한거 가져오기
	@Override
	public AnswerDTO answerSelect(String c_id) {
		return mapper.answerSelect(c_id);
	}

	// 회원 경고 1회 추가
	@Override
	public void warnCountUp(String m_id) {
		mapper.warnCountUp(m_id);
	}
	
	// 해당 회원 경고 횟수 가져오기
	@Override
	public int getWarnCount(String m_id) {
		return mapper.getWarnCount(m_id);
	}

	// 회원 경고 1회 감소
	@Override
	public void warnCountDown(String m_id) {
		mapper.warnCountDown(m_id);
	}
	
	
	



	// 회원 코드 생성
	public String createMemberCode(){
		return createCode(4);
	}
	
	// 이메일 인증 코드 생성
	public String createMailCode(){
		return createCode(7);
	}
	
	// 인증 코드 생성
	public String createCode( int num ){// num글자수만큼
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
		for(i = 0; i < num; i++){
			buf.append(possibleCharacters[rnd.nextInt(possibleCharacterCount)]);
		}

		return buf.toString();
	}
	
	//// 유효성 검사: 맞으면 true, 틀리면 false
	//// 회원 정보 유효성 검사 - 이메일
	public static boolean signUpEmailCheck(String m_email) {
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(m_email); 
		if(m.matches()) {
			return true;
		} 
		return false;
	}
	//// 회원 정보 유효성 검사 - 이름
	public static boolean signUpNicknameCheck(String m_nickname) {
		String regex = "^[a-zA-Zㄱ-힣0-9-_.]{2,30}$";
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(m_nickname); 
		if(m.matches()) {
			return true;
		} 
		return false;
	}
	//// 회원 정보 유효성 검사 - 비밀번호
	public static boolean signUpPasswordCheck(String m_password) {
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,60}$";
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(m_password); 
		if(m.matches()) {
			return true;
		} 
		return false;
	}
	
	//// 암호화된 비밀번호 일치 검사
	@Override
	public boolean pwmatch( String fromInput, String fromDB  ) {
		return pwEncoder.matches(fromInput, fromDB);
	}
	//// 회원번호로 회원 찾고, 암호화된 비밀번호 일치 검사
	@Override
	public boolean memberPwmatch( int m_id, String inputPW ) {
		return pwmatch( inputPW, mapper.getMember_no(m_id).getM_password() );
	}

}
