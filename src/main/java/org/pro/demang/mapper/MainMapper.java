package org.pro.demang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.pro.demang.model.ChatDTO;
import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.EmailCheckDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.OrderDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.model.PostImgDTO;
import org.pro.demang.model.AnswerDTO;
import org.pro.demang.model.MerchandiseDTO;

@Mapper
public interface MainMapper {
	
	// 회원 관련
	//// 회원가입
	void memberInsert(MemberDTO dto); // 새 회원 등록
    int codeCheck(String code);// 코드 중복 검사
	void emailCodeInsert(EmailCheckDTO dto);// 이메일 인증코드 등록
	void emailAuthenticationDelete(String m_email);// 회원가입 완료 시 이메일 인증코드 삭제
	void emailDelete(String m_email);
	String emailAuthenticationCheck(String m_email);
    int reEmailCheck(EmailCheckDTO dto);
	public String emailCheck(String m_email);
	//// 회원정보
	void memberUpdate(MemberDTO dto);
	MemberDTO getMember_no(String no);// 회원번호로 회원 찾기
	MemberDTO getMember_no(int no);// 회원번호로 회원 찾기
	MemberDTO getMember_email(String m_email);// 이메일로 회원찾기
	//// 팔로우
	void doFollow( String m1, String m2 );// m1이 m2를 팔로우하기
	int followCheck( String m1, String m2);// 팔로우 여부 체크
	int followingCount(int no);// 내가 팔로우한 사람 수
	int followerCount(int no);// 나를 팔로우한 사람 수
	List<MemberDTO> fList(int follower); // 특정 회원(번호)이 팔로우한 회원 목록
	List<MemberDTO> fList(String follower); // 특정 회원(번호)이 팔로우한 회원 목록
	
	
	// 게시글 관련
    //// 순수 게시물
	void postInsert( PostDTO dto ); // 게시글 작성
	void postDelete(String p_id); // 게시글 삭제
	void postInsert_noOrigin( PostDTO dto );// 게시글 작성 (원글 없음)
	List<Integer> getPostList_writer( String no );// 회원 번호로; 해당 번호의 회원의 게시글들(최신순)
	List<Integer> getPostList_writer( int no );// 회원 번호로; 해당 번호의 회원의 게시글들(최신순)
	List<Integer> getPostList_followee( String no );// 회원 번호로; 해당 회원이 팔로우한 회원이 작성한 글 목록 (최신순)
	PostDTO getPost( String no ); // 게시글 번호로 게시글 찾기
	PostDTO getPost( int no ); // 게시글 번호로 게시글 찾기
	//// 이미지
	void postInsertImg(int p_id, @Param("i_image")byte[] bytes); // 게시글 이미지 등록하기
	List<PostImgDTO> getImageList(String no); //게시글 번호로 해당 게시글의 이미지들 찾기
	List<PostImgDTO> getImageList(int no); //게시글 번호로 해당 게시글의 이미지들 찾기
	//// 해시태그
	void hashtagInsert( String hashtag );// 해시태그 테이블에 해시태그 등록
	void hashtagOnTableInsert( int p_id, String hashtag );// 게시글의 해시태그 정보 등록
	List<String> getHashTags( int p_id );// 게시글의 해시태그들 가져오기
	List<String> getHashTags( String p_id );// 게시글의 해시태그들 가져오기
	//// 좋아요
	void addLike(String l_id, String l_postNo); // 좋아요 누르기
	void removeLike(String l_id, String l_postNo);// 좋아요 취소
	int likeCheck(String l_id, String l_postNo); // 좋아요 눌렀는지 검사
	String likeCount(String l_postNo); // 게시물 좋아요 갯수
	//// 댓글
	void commentInsert( CommentDTO dto ); // 댓글 등록
	void commentDelete(String c_id); // 댓글 삭제하기
	List<CommentDTO> getCommentList(String no); // 게시글 번호로 해당 게시글의 댓글들 찾기
	List<CommentDTO> getCommentList(int no); // 게시글 번호로 해당 게시글의 댓글들 찾기
	List<CommentDTO> getCommentList_recent(String no); // 게시글 번호로 해당 게시글의 댓글들 찾기(최신 몇 개)
	List<CommentDTO> getCommentList_recent(int no); // 게시글 번호로 해당 게시글의 댓글들 찾기(최신 몇 개)
	//// 상품
	MerchandiseDTO getMerchandise( int mer_id );// 상품 번호로 상품 찾기
	List<MerchandiseDTO> getMerchandiseList( int mer_id );// 게시글의 상품 목록 불러오기
	void merCountUp( int mer_id );// 상품의 주문 시도 횟수 +1
	int getMerCount(int mer_id);// 상품의 주문 시도 횟수 조회
	int getMerAmount( int mer_id );// 상품 판매가능 수량 확인
	void merchandiseInsert(MerchandiseDTO merDTO); // 상품 정보 등록
	//// 리뷰
	int postReviewList(String p_origin); // 글에 있는 리뷰 개수 불러오기
	List<PostDTO> postReviewShow(String p_origin); // 리뷰 불러오기
	boolean reViewCheck(int ord_target, String ord_buyer); // 리뷰 작성 전 구매자 확인
	
	
	//// 주문 및 결제 관련
	void orderInsert(OrderDTO dto);// 디비에 주문 넣기
	void ordComplete(String ord_id);// 주문 정보를 결제 완료로 바꾸기
	void merSubtract(String ord_id);// 주문한 수만큼 상품 수량 차감
	int getOrderPrice( String ord_id );// 주문의 금액 조회
	
	
	// 검색 관련
	List<Integer> searchPost( String val );// 본문에 검색어를 포함하는 게시글 검색 (글번호 목록)
	List<Integer> searchTag( String tag );// 검색태그를 포함한 게시글 검색 (글번호 목록)
	List<MemberDTO> searchMember( String val );// 검색어를 포함하는 닉네임의 회원 검색
	// 아래는 일단 호현씨가 한 것들
    List<MemberDTO> memberSearch(String reSearchVal); // 검색창 입력된 단어가 포함된 이메일이나 닉네임 검색
	List<PostDTO> postSearch(String searchVal); // 검색창에 입력된 단어가 포함된 게시글 검색
	PostImgDTO getPostImage(int no);// (검색 결과용) 게시글의 이미지 한 개
	
	
	//// 채팅 관련
	void chatSend( ChatDTO dto );// 메시지 한 개 보내기
	List<ChatDTO> chatHistory( // 두 회원 사이의 채팅 읽어오기 (from 뒤의 것부터만)
			@Param("m1") String m1, 
			@Param("m2") String m2, 
			@Param("since") int since 
			);
	List<ChatDTO> chatHistory_recent( // 두 회원 사이의 채팅 읽어오기 (최신 몇 개만)
			@Param("m1") String m1, 
			@Param("m2") String m2
			);
	
	
	// 관리자 페이지 관련
	int userCount();
	int postCount();
	List<PostDTO> postTOP();
    void contactUsInsert(ContactUsDTO dto); // 문의 내용 DB 입력
	void contactUsImgInsert(int c_id, byte[] i_image); // 문의 내용 번호에 따른 이미지 DB 입력
	int memberSearchCount(String search);
	int contactSearchCount(String search);
	int postSearchCount(String search);
	List<MemberDTO> memberSearchAdmin(String search);
	List<ContactUsDTO> contactSearchAdmin(String search);
	List<PostDTO> postSearchAdmin(String search);
    void updateC_checked(String c_id);
	ContactUsDTO messageOneSelect(String c_id);
	void answerInsert(AnswerDTO dto); // 문의 답변 등록
	AnswerDTO answerSelect(String c_id); // 해당 문의의 답변 가져오기
}
