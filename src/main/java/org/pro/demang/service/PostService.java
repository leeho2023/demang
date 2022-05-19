package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.LikeDTO;
import org.pro.demang.model.PostDTO;

public interface PostService {

	void postInsert( PostDTO dto ); // 게시글 등록(리뷰)
	void postInsertN(PostDTO dto); // 게시글 등록(일반)
	void postInsertS(PostDTO dto); // 게시글 등록(판매)
	void postInsertImg(int p_id, byte[] bytes); // 게시물에 달릴 이미지 등록
	List<CommentDTO> commentShow(String p_id); // 댓글 불러오기
	void commentInsert(CommentDTO dto); // 댓글 등록하기
	public PostDTO getPost( String no );
	public PostDTO getPost( int no );
	public List<CommentDTO> getCommentList( String no );
	public List<CommentDTO> getCommentList( int no );
	public List<Integer> getPostList_writer( String no );
	public List<Integer> getPostList_writer( int no );
	public List<Integer> getPostList_followee( String no );
	public List<Integer> getPostList_followee( int no );
   List<PostDTO> postSearch(String searchVal);
   List<Integer> tagForGetPostNO(String reSearchVal);
	List<Integer> getPostNO(String searchVal);
	void addLike(String l_id, String l_postNo); // 좋아요 누르기
	void addLikeCount(String l_postNo); // 좋아요 누르면 올라가는 카운트
	String likeCheck(String l_id, String l_postNo); // 좋아요 불러오기
	String likeCount(String l_postNo); // 좋아요 갯수 불러오기
	
}
