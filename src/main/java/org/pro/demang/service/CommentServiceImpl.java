package org.pro.demang.service;

import java.util.List;

import org.pro.demang.mapper.TestMapper;
import org.pro.demang.model.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private TestMapper mapper;

	@Override
	public List<CommentDTO> commentShow(String p_id) {
		return mapper.commentShow(p_id);
	}

	@Override
	public void commentInsert(CommentDTO dto) {
		mapper.commentInsert(dto);
	}



	
	
}
