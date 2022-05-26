package org.pro.demang.service;

import javax.mail.MessagingException;

import org.pro.demang.model.EmailCheckDTO;
import org.pro.demang.model.AnswerDTO;

public interface MailService {
	
	String sendMail(String m_email) throws MessagingException;
	
	String answerInsert(String m_email, AnswerDTO dto) throws MessagingException;

	int emailCheck(String m_email);

	int reEmailCheck(EmailCheckDTO dto);
}
