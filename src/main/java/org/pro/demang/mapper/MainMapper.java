package org.pro.demang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.pro.demang.model.AnswerDTO;
import org.pro.demang.model.ChatDTO;
import org.pro.demang.model.CommentDTO;
import org.pro.demang.model.ContactUsDTO;
import org.pro.demang.model.MemberDTO;
import org.pro.demang.model.MerchandiseDTO;
import org.pro.demang.model.OrderDTO;
import org.pro.demang.model.PostDTO;
import org.pro.demang.model.PostImgDTO;

@Mapper
public interface MainMapper {
	
	// 회원 관련
	//// 회원가입
	void memberInsert(MemberDTO dto); // 새 회원 등록
	Integer codeCheck(String code);// 코드 중복 검사
	void emailCodeInsert( String m_email, String e_code );// 이메일 인증코드 등록
	void emailCodeUpdate( String m_email, String e_code );// 해당 이메일의 인증코드 바꾸기
	void emailAuthenticationDelete(String m_email);// 회원가입 완료 시 이메일 인증코드 삭제
	Integer emailVerifyCheck( String email, String code );// 이메일과 이메일 인증 코드 일치 확인
	Integer emailDuplicateCheck(String m_email);// 해당 이메일의 회원 존재 여부
	Integer tempEmailDuplicateCheck(String m_email);// 해당 이메일에 인증코드를 이미 보냈는지 확인
	//// 회원정보
	MemberDTO getMember_no(int no);// 회원번호로 회원 찾기
	MemberDTO getMember_email(String m_email);// 이메일로 회원찾기
	//// 회원정보 수정
	void memberUpdate_nickname(int m_id, String m_nickname);// 닉네임 수정
	void memberUpdate_password(int m_id, String m_password);// 비밀번호 수정
	void memberUpdate_gender(int m_id, String m_gender);// 성별 수정
	void memberUpdate_introduce(int m_id, String m_introduce);// 자기소개 수정
	void memberUpdate_profilePic(int m_id, byte[] m_profilePic);// 프사 수정
	void memberUpdate_eraseProfilePic(int m_id);// 프사 지우기
	//// 팔로우
	void doFollow( int m1, int m2 );// m1이 m2를 팔로우하기
	void unFollow(int m1, int m2);// m1이 m2를 팔로우 취소
	int followCheck( int m1, int m2);// 팔로우 여부 체크
	int followingCount(int no);// 내가 팔로우한 사람 수
	int followerCount(int no);// 나를 팔로우한 사람 수
	List<MemberDTO> fList(int follower); // 특정 회원(번호)이 팔로우한 회원 목록
	//// 탈퇴
	void memberWithdraw(int loginId);
	
	
	// 게시글 관련
    //// 순수 게시물
	void postInsert( PostDTO dto ); // 게시글 작성
	void postDelete( int p_id); // 게시글 삭제
	void postUpdate( int p_id, String p_content ); // 게시글 수정 (본문만)
	void postInsert_noOrigin( PostDTO dto );// 게시글 작성 (원글 없음)
	int getP_writer( int p_id );// 게시글의 작성자
	List<Integer> getPostList_writer( int no );// 회원 번호로; 해당 번호의 회원의 게시글들(최신순)
	List<Integer> getPostList_followee( int no );// 회원 번호로; 해당 회원이 팔로우한 회원이 작성한 글 목록 (최신순)
	List<Integer> getPostList_like(int no);// 회원 번호로; 해당 회원이 좋아한 글 목록
	List<Integer> getSomePostsInsteadOfFeed();// 가장 최근 게시물 몇 개 (피드에 보여줄 게시물 0개일 때 대신할 게시물 목록);
	PostDTO getPost( int no ); // 게시글 번호로 게시글 찾기
	//// 이미지
	void postInsertImg(int p_id, @Param("i_image")byte[] bytes); // 게시글 이미지 등록하기
	List<PostImgDTO> getImageList(int no); //게시글 번호로 해당 게시글의 이미지들 찾기
	//// 해시태그
	void hashtagInsert( String hashtag );// 해시태그 테이블에 해시태그 등록
	void hashtagOnTableInsert( int p_id, String hashtag );// 게시글의 해시태그 정보 등록
	List<String> getHashTags( int p_id );// 게시글의 해시태그들 가져오기
	void deleteHashtags( int p_id );// 게시글의 해시태그들 삭제
	//// 좋아요
	void addLike(int l_id, int l_postNo); // 좋아요 누르기
	void removeLike(int l_id, int l_postNo);// 좋아요 취소
	int likeCheck(int l_id, int l_postNo); // 좋아요 눌렀는지 검사
	String likeCount(int l_postNo); // 게시물 좋아요 갯수
	//// 댓글
	void commentInsert( CommentDTO dto ); // 댓글 등록
	void commentDelete(String c_id); // 댓글 삭제하기
	List<CommentDTO> getCommentList(int no); // 게시글 번호로 해당 게시글의 댓글들 찾기
	List<CommentDTO> getCommentList_recent(int no); // 게시글 번호로 해당 게시글의 댓글들 찾기(최신 몇 개)
	//// 상품
	MerchandiseDTO getMerchandise( int mer_id );// 상품 번호로 상품 찾기
	List<MerchandiseDTO> getMerchandiseList( int mer_id );// 게시글의 상품 목록 불러오기
	void merCountUp( int mer_id );// 상품의 주문 시도 횟수 +1
	int getMerCount(int mer_id);// 상품의 주문 시도 횟수 조회
	int getMerAmount( int mer_id );// 상품 판매가능 수량 확인
	void merchandiseInsert(MerchandiseDTO merDTO); // 상품 정보 등록
	//// 리뷰
	int getReviewNum(int p_origin); // 글에 있는 리뷰 개수 불러오기
	List<Integer> getReviewList(int p_origin); // 리뷰 불러오기
	boolean reViewCheck(int ord_target, int ord_buyer); // 특정 상품의 구매자인지 여부 확인
	
	
	//// 주문 및 결제 관련
	void orderInsert(OrderDTO dto);// 디비에 주문 넣기
	OrderDTO getOrder(String ord_id);// 주문 정보 가져오기
	void ordComplete(String ord_id);// 주문 정보를 결제 완료로 바꾸기
	void merSubtract(String ord_id);// 주문한 수만큼 상품 수량 차감
	int getOrderPrice( String ord_id );// 주문의 금액 조회
	List<OrderDTO> getOrderList(int loginId);// 회원의 주문 목록 조회
	void paymentWaiting( String event_id, String ord_id );// 결제 대기 시간 지났는데 결제 안 됐으면 결제취소 상태로 바꾸기 이벤트 스케줄러
	
	
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
			@Param("m1") int m1, 
			@Param("m2") int m2, 
			@Param("since") int since 
			);
	List<ChatDTO> chatHistory_recent( // 두 회원 사이의 채팅 읽어오기 (최신 몇 개만)
			@Param("m1") int m1, 
			@Param("m2") int m2
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
	void warnCountUp(String m_id);
	int getWarnCount(String m_id);
	void warnCountDown(String m_id);
	
	   
}
