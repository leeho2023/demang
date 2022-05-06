package org.pro.demang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.PostDTO;

@Mapper
public interface MainMapper {

	List<MemberDTO> fList(int follower); // 회원 코드로 해당 친구 찾기
	
	public void memberInsert(MemberDTO dto); // 회원가입

	List<CommentDTO> commentShow(String p_id); //댓글 목록 불러오기
	void commentInsert(CommentDTO dto); // 댓글 입력하기
	void postInsert(String p_content, @Param("p_image")byte[] bytes); // post작성
	public MemberDTO getMember_no(String no);// 회원번호로 회원 찾기
	public MemberDTO getMember_no(int no);// 회원번호로 회원 찾기
	public MemberDTO getMember_code(String code);// 회원코드로 회원 찾기
	
	public int followingCount(int no);// 내가 팔로우한 사람 수
	public int followerCount(int no);// 나를 팔로우한 사람 수
	
	public List<Integer> getPostList_writer( String no );// 회원 번호로; 해당 번호의 회원의 게시글들(최신순)
	public List<Integer> getPostList_writer( int no );// 회원 번호로; 해당 번호의 회원의 게시글들(최신순)
	public List<Integer> getPostList_followee( String no );// 회원 번호로; 해당 회원이 팔로우한 회원이 작성한 글 목록 (최신순)

	public PostDTO getPost( String no );// 게시글 번호로 게시글 찾기
	public PostDTO getPost( int no );// 게시글 번호로 게시글 찾기
	public List<CommentDTO> getCommentList(String no);// 게시글 번호로 해당 게시글의 댓글들 찾기
	public List<CommentDTO> getCommentList(int no);// 게시글 번호로 해당 게시글의 댓글들 찾기
	
	public MemberDTO login(MemberDTO dto);//로그인 
	public String getRealPassword(String m_email);//로그인관련
}
