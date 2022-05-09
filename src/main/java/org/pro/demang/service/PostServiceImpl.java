package org.pro.demang.service;

import java.util.List;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private MainMapper mapper;

	//게시글 등록하기
	@Override
	public void postInsert(int p_origin, String p_type, String p_writer, String p_content) {
		mapper.postInsert(p_origin, p_type, p_writer, p_content);
	}
	
	//게시글 이미지 등록하기
	@Override
	public void postImgInsert(byte[] bytes) {
		mapper.postinsertImg(bytes);
	}

	//댓글 불러오기
	@Override
	public List<CommentDTO> commentShow(String p_id) {
		return mapper.commentShow(p_id);
	}

	// 댓글 등록하기
	@Override
	public void commentInsert(CommentDTO dto) {
		mapper.commentInsert(dto);
	}

	
	
	//// 회원 번호로; 해당 회원의 게시글 목록(게시글번호만)(최신순)
	@Override
	public List<Integer> getPostList_writer( String no ){
		return mapper.getPostList_writer(no);
	}
	@Override
	public List<Integer> getPostList_writer( int no ){
		return mapper.getPostList_writer(no);
	}
	//// 회원 번호로; 해당 회원이 팔로우한 회원이 작성한 게시글 목록(게시글번호만)(최신순)
	@Override
	public List<Integer> getPostList_followee( String no ){
		return mapper.getPostList_followee(no);
	}
	@Override
	public List<Integer> getPostList_followee( int no ){
		return getPostList_followee(""+no);
	}

	
	//// 번호로 게시글 찾기
	@Override
	public PostDTO getPost( String no ) {
		return mapper.getPost(no);
	}
	@Override
	public PostDTO getPost( int no ) {
		return mapper.getPost(no);
	}
	
	//// 게시글 번호로 해당 게시글의 댓글들 찾기
	@Override
	public List<CommentDTO> getCommentList(String no) {
		return mapper.getCommentList(no);
	}
	@Override
	public List<CommentDTO> getCommentList(int no) {
		return mapper.getCommentList(no);
	}	
}
