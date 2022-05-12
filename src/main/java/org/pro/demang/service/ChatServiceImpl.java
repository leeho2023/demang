package org.pro.demang.service;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{
	@Autowired private MainMapper mapper;
	
	//// 메시지 보내기
	public void chatSend( String m1, String m2, String h_content ) {
		System.out.println("service . chatSend 1 ~ "+m1+", "+m2+", "+h_content);
		ChatDTO dto = new ChatDTO( m1, m2, h_content );
		System.out.println("service . chatSend 1 ~ "+dto);
		mapper.chatSend( dto );
		System.out.println("service . chatSend 2 ~ "+dto);
	}
}
