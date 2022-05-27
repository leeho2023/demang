package org.pro.demang.service;

import org.pro.demang.mapper.PagingMapper;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagingServiceImpl {

	@Autowired
	private PagingMapper mapper;

	
	public Page<ContactUsDTO> getContactList(int pageNo) throws Exception {
        PageHelper.startPage(pageNo, 10);
        return mapper.findContact();
    }

    public Page<MemberDTO> getUserList(int pageNo) throws Exception {
        PageHelper.startPage(pageNo, 10);
        return mapper.findUser();
    }

	public Page<PostDTO> getPostList(int pageNo) throws Exception {
		PageHelper.startPage(pageNo, 10);
        return mapper.findPost();
	}

}
