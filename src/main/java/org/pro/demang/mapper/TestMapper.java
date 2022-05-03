package org.pro.demang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pro.demang.model.testDTO;

@Mapper
public interface TestMapper {

	void testInsert(testDTO dto);

}
