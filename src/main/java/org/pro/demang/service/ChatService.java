package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.ChatDTO;

public interface ChatService {
	void chatSend( ChatDTO dto );
	List<ChatDTO> chatHistory( String m1, String m2, int since );
}
