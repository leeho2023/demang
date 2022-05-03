package org.pro.demang.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

	void postInsert(Map<String, Object> hmap);

}
