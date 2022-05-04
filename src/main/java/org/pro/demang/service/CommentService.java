package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.CommentDTO;

public interface CommentService {

	List<CommentDTO> commentShow(String p_id);
	void commentInsert(CommentDTO dto);

	
}
