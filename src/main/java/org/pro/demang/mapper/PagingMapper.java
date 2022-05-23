package org.pro.demang.mapper;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;
import org.pro.demang.model.ContactUsDTO;

@Mapper
public interface PagingMapper {

	Page<ContactUsDTO> findUser();
	
    
}
