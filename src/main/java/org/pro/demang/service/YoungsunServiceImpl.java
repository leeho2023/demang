package org.pro.demang.service;

import java.util.List;

import org.pro.demang.mapper.TestMapper;
import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.MemberDTO_ext;
import org.pro.demang.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoungsunServiceImpl implements YoungsunService {
	@Autowired private TestMapper mapper;
	
	//// 회원번호로 회원 찾기
	@Override
	public MemberDTO getMember_no( String no ) {
		return mapper.getMember_no(no);
	}
	@Override
	public MemberDTO getMember_no( int no ) {
		return mapper.getMember_no(no);
	}
	//// 회원코드로 회원 찾기 +추가정보(팔로잉, 팔로워 수)
	//// 못 찾으면 null
	@Override
	public MemberDTO_ext getMember_ext( String code ) {
		try{MemberDTO member = mapper.getMember_code( code );
			return new MemberDTO_ext(
					member,// 찾는 멤버
					mapper.followingCount( member.getM_id()),// 팔로잉 수
					mapper.followerCount( member.getM_id())// 팔로워 수
					);
		}catch(Exception e) {
			System.out.println("no found");
			return null;
		}
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
