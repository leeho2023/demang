package org.pro.demang.service;

import javax.mail.MessagingException;

import org.pro.demang.model.EmailCheckDTO;

public interface MailService {
	
	String sendMail(String m_email) throws MessagingException;

	int emailCheck(String m_email);

	int reEmailCheck(EmailCheckDTO dto);
}
