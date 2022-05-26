package org.pro.demang.service;

import java.util.List;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{
	@Autowired private MainMapper mapper;
	
	//// 메시지 보내기
	@Override
	public void chatSend( ChatDTO dto ) {
		mapper.chatSend( dto );
	}
	
	//// 지난 채팅 불러오기 (특정번호 다음부터 최신것까지만)
	@Override
	public List<ChatDTO> chatHistory( int m1, int m2, int since ){
		return mapper.chatHistory(m1, m2, since);
	}
}
