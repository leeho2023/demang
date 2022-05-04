package org.pro.demang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.pro.demang.model.MemberDTO;

@Mapper
public interface TestMapper {

	List<MemberDTO> fList(int follower);

	void testInsert(String name, byte[] bytes);

}
