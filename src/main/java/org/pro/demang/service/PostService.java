package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.PostDTO;

public interface PostService {

	void postInsert(String p_content, byte[] bytes); // 게시물 등록
	List<CommentDTO> commentShow(String p_id); // 댓글 불러오기
	void commentInsert(CommentDTO dto); // 댓글 등록하기
	public PostDTO getPost( String no );
	public PostDTO getPost( int no );
	public List<CommentDTO> getCommentList( String no );
	public List<CommentDTO> getCommentList( int no );
	public List<Integer> getPostList_writer( String no );
	public List<Integer> getPostList_writer( int no );
	public List<Integer> getPostList_followee( String no );
	
}
