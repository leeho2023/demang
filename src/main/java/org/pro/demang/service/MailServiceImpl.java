package org.pro.demang.service;

import java.util.Date;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.EmailCheckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private MainMapper mapper;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	// 입력된 이메일과 생성된 인증코드 DB에 등록과 동시에 인증코드 메일 발송
	@Override
	public String sendMail(String m_email) throws MessagingException {
		String emailCheckCode = createCode();
		System.out.println("생성된 인증 코드" + emailCheckCode);

		EmailCheckDTO dto = new EmailCheckDTO();
		dto.setE_code(emailCheckCode);
		dto.setM_email(m_email);
		mapper.emailCodeInsert(dto);


	    MimeMessage message = javaMailSender.createMimeMessage();
	    message.setSubject("메일 테스트 5");
	    message.setRecipient(Message.RecipientType.TO, new InternetAddress(m_email));
	    message.setText("이메일 인증 코드는 잘 가나요? ==>" + emailCheckCode);
	    message.setSentDate(new Date());
	    javaMailSender.send(message);
	    return "";
	}

	// 이메일 중복 체크
	@Override
	public int emailCheck(String m_email) {
		String rem_email = mapper.emailCheck(m_email);
		String e_email = mapper.emailAuthenticationCheck(m_email);
		if(rem_email == null && e_email == null){
			return 1;
		}else if(rem_email == null && e_email != null) {
			mapper.emailAuthenticationDelete(m_email);
			return 1;
		}else{
			return 0;
		}
		
	}

	// 입력된 인증코드 DB에 있는지 확인
	@Override
	public int reEmailCheck(EmailCheckDTO dto) {
		
		return mapper.reEmailCheck(dto);
	}

	// 이메일 인증 코드 생성
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
		for(i = 0; i < 7; i++){
			buf.append(possibleCharacters[rnd.nextInt(possibleCharacterCount)]);
		}
		String code = buf.toString();
		System.out.println("생성된 회원 코드 -> " + code);

		return code;
	}

	

	
	
}
