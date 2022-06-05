package org.pro.demang.service;

import java.util.List;

import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.PostDTO;

public interface PostService {

	void postInsert( PostDTO dto ); // 게시글 등록
	void postDelete( int p_id ); // 게시글 삭제
	void postUpdate( PostDTO dto ); // 게시글 수정(본문만)
	boolean reViewCheck(int ord_target, int ord_buyer); // 리뷰 작성 전 구매자 확인
	void postInsertImg(int p_id, byte[] bytes); // 게시물에 달릴 이미지 등록
	void commentInsert(CommentDTO dto); // 댓글 등록하기
	void commentDelete(String c_id); // 댓글 삭제하기
	int postReviewList(int p_origin); // 글에 있는 리뷰 개수 불러오기
	List<PostDTO> postReviewShow(int p_origin); // 리뷰 불러오기
	public PostDTO getPost_raw( int no );
	public PostDTO getPost_view( int no );
	public List<CommentDTO> getCommentList( int no );
	public List<Integer> getPostList_writer( int no );
	public List<Integer> getPostList_followee( int no );
	public List<Integer> getPostList_like( int no );
	void addLike(int l_id, int l_postNo); // 좋아요 누르기
	void removeLike(int loginId, int l_postNo);// 좋아요 취소
	boolean likeCheck(int l_id, int l_postNo); // 좋아요 불러오기
	String likeCount(int l_postNo); // 좋아요 갯수 불러오기
	int postSearchCount(String search);
	List<PostDTO> postSearchAdmin(String search);
	
}
