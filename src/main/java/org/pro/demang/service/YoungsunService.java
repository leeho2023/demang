package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.MemberDTO_ext;
import org.pro.demang.model.PostDTO;

public interface YoungsunService {
	public PostDTO getPost( String no );
	public PostDTO getPost( int no );
	public MemberDTO getMember_no( String no );
	public MemberDTO getMember_no( int no );
	public MemberDTO_ext getMember_ext( String code );
	public List<CommentDTO> getCommentList( String no );
	public List<CommentDTO> getCommentList( int no );
	public List<Integer> getPostList_writer( String no );
	public List<Integer> getPostList_writer( int no );
	public List<Integer> getPostList_followee( String no );
	
	
}
