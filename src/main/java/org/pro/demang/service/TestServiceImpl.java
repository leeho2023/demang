package org.pro.demang.service;

import org.pro.demang.mapper.TestMapper;
import org.pro.demang.model.testDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{

	@Autowired
	private TestMapper mapper;

	@Override
	public void testInsert(testDTO dto) {
		mapper.testInsert(dto);
	}
	
}
