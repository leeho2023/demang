package org.pro.demang.service;

import java.util.List;

import org.pro.demang.mapper.TestMapper;
import org.pro.demang.model.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private TestMapper mapper;
	
	@Override
	public List<MemberDTO> fList(int follower) {
		return mapper.fList(follower);
	}

}
