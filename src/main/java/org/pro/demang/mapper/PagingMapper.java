package org.pro.demang.mapper;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;

@Mapper
public interface PagingMapper {

	Page<ContactUsDTO> findContact();
	Page<MemberDTO> findUser();
	
    
}
