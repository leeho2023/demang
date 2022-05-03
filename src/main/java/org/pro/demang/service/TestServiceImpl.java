package org.pro.demang.service;

import org.pro.demang.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{

	@Autowired
	private TestMapper mapper;

	@Override
	public void testInsert(String name, byte[] bytes) {
		System.out.println("매퍼 받아오기");
		mapper.testInsert(name, bytes);
		System.out.println("매퍼 받아오기 끝");
	}

	
}
