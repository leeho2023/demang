package org.pro.demang.service;

import java.util.Arrays;
import java.util.List;

import org.pro.demang.mapper.MainMapper;
import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.model.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private MainMapper mapper;

	// 게시글 등록하기
	@Override
	public void postInsert( PostDTO dto ) {
		//// p_origin이 있냐null이냐에 따라 게시글 삽입 쿼리문이 다르다
		if( dto.getP_origin() == 0 )
			mapper.postInsert_noOrigin(dto);
		else
			mapper.postInsert(dto);
		postHashtagInsert(dto);// DB에 해시태그 등록
	}
	
	//// 게시글 수정(본문만)
	@Override
	public void postUpdate( PostDTO dto ) {
		mapper.deleteHashtags( dto.getP_id() );// DB에 해시태그 삭제 후
		postHashtagInsert(dto);// 새로 등록
		mapper.postUpdate( dto.getP_id(), dto.getP_content() );// 본문만 update
	}
	
	//// 게시글의 본문에서 해시태그 찾아 등록
	private void postHashtagInsert( PostDTO dto ) {
		//// 해시태그 등록하기
		for( String temp: findHashtags(dto.getP_content()) ) {// 게시글의 해시태그 하나마다
			mapper.hashtagInsert( temp );// 디비에 해시태그 등록
			mapper.hashtagOnTableInsert( dto.getP_id(), temp );// 디비에 해시태그 등록
		}
	}
	
	//// 게시글 삭제하기
	@Override
	public void postDelete(int p_id) {
		mapper.postDelete(p_id);
	}
	
	//// 번호로 게시글 찾기
	@Override
	public PostDTO getPost_raw( int no ) {
		PostDTO dto = mapper.getPost(no);// 게시글 읽어오기
		return dto;
	}
	
	//// 번호로 게시글 찾기 (해시태그를 a태그로 링크 거는 처리)
	@Override
	public PostDTO getPost_view( int no ) {
		PostDTO dto = mapper.getPost(no);// 게시글 읽어오기
		List<String> tags = mapper.getHashTags(no);//// 해시태그 읽어오기
		// 본문의 해시태그 부분들을 <#>로 감싸기 … 뷰단의 스크립트에서 <#>를 a태그로 치환한다.
		for( String tag: tags ) {
			dto.setP_content(
					dto.getP_content().replace(
							"#"+tag,
							"<#>" + "#"+tag + "</#>")
					);
		}
		return dto;
	}
	
	//// 회원 번호로; 해당 회원의 게시글 목록(게시글번호만)(최신순)
	@Override
	public List<Integer> getPostList_writer( int no ){
		return mapper.getPostList_writer(no);
	}
	//// 회원 번호로; 해당 회원이 팔로우한 회원이 작성한 게시글 목록(게시글번호만)(최신순)
	@Override
	public List<Integer> getPostList_followee( int no ){
		return mapper.getPostList_followee(no);
	}
	//// 회원 번호로; 해당 회원이 좋아한 게시글 목록(게시글번호만)
	@Override
	public List<Integer> getPostList_like( int no ){
		return mapper.getPostList_like(no);
	}
	
	// 이미지
	//// 게시글 이미지 등록하기
	@Override
	public void postInsertImg(int p_id, byte[] bytes) {
		mapper.postInsertImg(p_id, bytes);
	}
	
	// 좋아요
	@Override
	public void addLike(int l_id, int l_postNo) {
		mapper.addLike(l_id, l_postNo);
	}
	@Override
	public void removeLike(int l_id, int l_postNo) {
		mapper.removeLike(l_id, l_postNo);
	}
	@Override
	public boolean likeCheck(int l_id, int l_postNo) {
		if( mapper.likeCheck(l_id, l_postNo) > 0 ) return true;
		return false;
	}
	//// 게시글의 좋아요 개수. null이면 0으로 변환해 반환
	@Override
	public String likeCount(int l_postNo) {
		String count = mapper.likeCount(l_postNo);
		if( count == null ) return "0";
		return count;
	}
	
	// 댓글
	//// 댓글 등록하기
	@Override
	public void commentInsert(CommentDTO dto) {
		dto.setC_content( dto.getC_content().trim() );// 댓글 내용 앞뒤 공백 제거
		mapper.commentInsert(dto);
		dto.setMemberDTO( // 회원정보
				mapper.getMember_no( 
						dto.getC_writer()
						) );
	}
	
	//// 댓글 삭제하기
	@Override
	public void commentDelete(String c_id) {
		mapper.commentDelete(c_id);
	}
	
	//// 게시글 번호로 해당 게시글의 댓글들 찾기
	@Override
	public List<CommentDTO> getCommentList(int no) {
		return mapper.getCommentList(no);
	}
	

	
	
	// 리뷰 작성 전 구매자 확인
	@Override
	public boolean reViewCheck(int ord_target, int ord_buyer) {
		return mapper.reViewCheck(ord_target, ord_buyer);
	}
	public int postSearchCount(String search) {
		return mapper.postSearchCount(search);
	}
	@Override
	public List<PostDTO> postSearchAdmin(String search) {
		return mapper.postSearchAdmin(search);
	}
	
	

	
	
	

	////문자열에서 해시태그 찾아내기 (문자열 배열로 반환)
	private static String[] findHashtags( String inputString ) {
		byte[] bytes = inputString.getBytes();// 바이트 배열로 만들어서 분석
		int tagsNMax = 10;// 태그 개수 최대치
		
		//// #에서부터 문자열 끝 혹은 분리문자 만나기 전까지를 해시태그로 보기 // 띄어쓰기와 #로만 해시태그 끝내기
		String[] tags = new String[tagsNMax];// 태그들의 배열
		int tagsN = 0;// 태그 개수
		for( int i = 0; i<bytes.length; i++ ) {// 바이트 단위로 문자열 분석
			if( bytes[i] == 35 ) {// #: 해시태그 시작
				i++;
				byte[] tagBuild = new byte[20];
				int tagBuildLength = 0;
				for( ; i<bytes.length; i++ ) {
					if(       bytes[i] != 32// 띄어쓰기
							&& bytes[i] != 35// #
							) {
						tagBuild[ tagBuildLength++ ] = bytes[i];
					}else {// 띄어쓰기이면 해시태그 끝
						if( bytes[i] == 35 ) i--;// 새로 나온 #으로써 해시태그가 끝난 경우 이 새로 나올 해시태그 인식하려고 i-=1 함
						break;
					}
				}// 해시태그 끝
				//// 바이트배열로 문자열 해시태그 만들기 (해시태그 내용물이 없으면(길이=0) 안 만들기)
				if( tagBuildLength > 0 ) {
					tags[tagsN++] = new String( 
							Arrays.copyOfRange(tagBuild, 0, tagBuildLength) );
					if( tagsN >= tagsNMax ) break;// 태그 개수 가득차면 더이상 태그 찾지 않고 종료
				}
			}
		}
		
		//// 태그 목록 중 있는 부분까지만 잘라 보내줌
		return Arrays.copyOfRange(tags, 0, tagsN);
	}

}
