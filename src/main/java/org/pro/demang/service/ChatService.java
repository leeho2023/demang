package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.PostDTO;

public interface ChatService {
	void chatSend( String m1, String m2, String h_content );
}
