package org.pro.demang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {

	void testInsert( @Param("name")String name, byte[] bytes);

}
