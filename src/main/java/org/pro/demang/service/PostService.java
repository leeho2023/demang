package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.PostDTO;

public interface PostService {

	void postInsert( PostDTO dto ); // 게시글 등록
	boolean reViewCheck(int ord_target, String ord_buyer); // 리뷰 작성 전 구매자 확인
	void postInsertImg(int p_id, byte[] bytes); // 게시물에 달릴 이미지 등록
	void commentInsert(CommentDTO dto); // 댓글 등록하기
	int postReviewList(String p_origin); // 글에 있는 리뷰 개수 불러오기
	List<PostDTO> postReviewShow(String p_origin); // 리뷰 불러오기
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
	void removeLike(String loginId, String l_postNo);// 좋아요 취소
	boolean likeCheck(String l_id, String l_postNo); // 좋아요 불러오기
	String likeCount(String l_postNo); // 좋아요 갯수 불러오기
	int postSearchCount(String search);
	List<PostDTO> postSearchAdmin(String search);
	
}
