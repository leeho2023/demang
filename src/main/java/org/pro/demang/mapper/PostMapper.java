package org.pro.demang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {

	void postInsert(String p_content, @Param("p_image")byte[] bytes);

}
