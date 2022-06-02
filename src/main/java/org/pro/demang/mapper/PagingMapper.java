package org.pro.demang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.OrderDTO;
import org.pro.demang.model.PostDTO;

import com.github.pagehelper.Page;

@Mapper
public interface PagingMapper {

	Page<ContactUsDTO> findContact();
	Page<MemberDTO> findUser();
	Page<PostDTO> findPost();
	Page<OrderDTO> findOrder(int loginId);
	
    
}
