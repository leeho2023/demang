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
import org.pro.demang.model.MerchandiseDTO;

@Mapper
public interface MainMapper {

	List<MemberDTO> fList(int follower); // 특정 회원(번호)이 팔로우한 회원 목록
	List<MemberDTO> fList(String follower); // 특정 회원(번호)이 팔로우한 회원 목록

	void memberInsert(MemberDTO dto); // 회원가입

	List<PostDTO> postReviewShow(String p_origin); // 리뷰 불러오기

	void postInsert( PostDTO dto ); // 게시글 작성
	void postInsert_noOrigin( PostDTO dto );// 게시글 작성 (원글 없음)
	void postSellUpdate(String p_id, String p_type); // 판매 상태를 변경하기
	boolean reViewCheck(String p_id, String ord_buyer); // 리뷰 작성 전 구매자 확인
	
	void hashtagInsert( String hashtag );// 해시태그 등록
	void hashtagOnTableInsert( int p_id, String hashtag );// 게시글의 해시태그 등록

	void postInsertImg(int p_id, @Param("i_image")byte[] bytes); // 게시글 이미지 등록하기
	
	MemberDTO getMember_no(String no);// 회원번호로 회원 찾기
	MemberDTO getMember_no(int no);// 회원번호로 회원 찾기
	MemberDTO getMember_no(Object no);//세션값으로 회원찾기
	MemberDTO getMember_email(String m_email);
	
	
	
	int followingCount(int no);// 내가 팔로우한 사람 수
	int followerCount(int no);// 나를 팔로우한 사람 수
	void doFollow( String m1, String m2 );// m1이 m2를 팔로우하기
	int followCheck( String m1, String m2);// 팔로우 여부 체크
	
	List<Integer> getPostList_writer( String no );// 회원 번호로; 해당 번호의 회원의 게시글들(최신순)
	List<Integer> getPostList_writer( int no );// 회원 번호로; 해당 번호의 회원의 게시글들(최신순)
	List<Integer> getPostList_followee( String no );// 회원 번호로; 해당 회원이 팔로우한 회원이 작성한 글 목록 (최신순)


	PostDTO getPost( String no ); // 게시글 번호로 게시글 찾기
	PostDTO getPost( int no ); // 게시글 번호로 게시글 찾기
	List<CommentDTO> getCommentList(String no); // 게시글 번호로 해당 게시글의 댓글들 찾기
	List<CommentDTO> getCommentList(int no); // 게시글 번호로 해당 게시글의 댓글들 찾기
	List<CommentDTO> getCommentList_recent(String no); // 게시글 번호로 해당 게시글의 댓글들 찾기
	List<CommentDTO> getCommentList_recent(int no); // 게시글 번호로 해당 게시글의 댓글들 찾기
	List<PostImgDTO> getImageList(String no); //게시글 번호로 해당 게시글의 이미지들 찾기
	List<PostImgDTO> getImageList(int no); //게시글 번호로 해당 게시글의 이미지들 찾기
	List<MerchandiseDTO> getMerchandiseList(String no); // 게시글의 상품 목록 불러오기
	void commentInsert( CommentDTO dto ); // 댓글 등록

    List<MemberDTO> memberSearch(String reSearchVal); // 검색창 입력된 단어가 포함된 이메일이나 닉네임 검색

	List<PostDTO> postSearch(String searchVal); // 검색창에 입력된 단어가 포함된 게시글 검색

	public MemberDTO memberRead(String m_id);
	void memberUpdate(MemberDTO dto);

    List<Integer> tagForGetPostNO(String reSearchVal); // 검색창에 입력된 단어가 태그인 게시물 검색
	public String emailCheck(String m_email);
	
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

	List<Integer> getPostNO(String searchVal);
	PostImgDTO getImage(int no);

    int codeCheck(String code);


	void addLike(String l_id, String l_postNo); // 좋아요 누르기
	void removeLike(String l_id, String l_postNo);// 좋아요 취소

	void emailCodeInsert(EmailCheckDTO dto);


	void emailAuthenticationDelete(String m_email);

	void emailDelete(String m_email);

	String emailAuthenticationCheck(String m_email);
    int reEmailCheck(EmailCheckDTO dto);
	int likeCheck(String l_id, String l_postNo); // 좋아요 눌렀는지 검사
	void addLikeCount(String l_postNo); // 좋아요 누르면 오르는 카운트
	String likeCount(String l_postNo); // 게시물 좋아요 갯수

    void contactUsInsert(ContactUsDTO dto); // 문의 내용 DB 입력
	void contactUsImgInsert(int c_id, byte[] i_image); // 문의 내용 번호에 따른 이미지 DB 입력
	
	// 페이징 관련
	public List<ContactUsDTO> selectContactList(ContactUsDTO dto);
	public int selectContactTotalCount(ContactUsDTO dto);

	// 내가 만든 페이징 관련
	List<ContactUsDTO> messageList(int c_id);
    int contactAllNumCount();
	
	int userCount();
	int postCount();
	List<PostDTO> postTOP();

	
	
	//// 주문 및 결제 관련
	String getMerName(int mer_id);// 상품 이름
	int getMerPrice(int mer_id);// 상품 가격
	void merCountUp( int mer_id );// 상품의 주문 시도 횟수 +1
	int getMerCount(int mer_id);// 상품의 주문 시도 횟수 조회
	int getMerAmount( int mer_id );// 상품 판매가능 수량 확인
	void orderInsert(OrderDTO dto);// 디비에 주문 넣기
	void ordComplete(String ord_id);// 주문 정보를 결제 완료로 바꾸기
	void merSubtract(String ord_id);// 주문한 수만큼 상품 수량 차감
	int getOrderPrice( String ord_id );// 주문의 금액 조회
	void merchandiseInsert(MerchandiseDTO merDTO); // 상품 정보 등록
}
