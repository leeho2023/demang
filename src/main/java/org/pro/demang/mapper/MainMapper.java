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

@Mapper
public interface MainMapper {

	List<MemberDTO> fList(int follower); // 특정 회원(번호)이 팔로우한 회원 목록
	List<MemberDTO> fList(String follower); // 특정 회원(번호)이 팔로우한 회원 목록

	void memberInsert(MemberDTO dto); // 회원가입

	List<CommentDTO> commentShow(String p_id); //댓글 목록 불러오기

	void postInsert( PostDTO dto ); // post작성
	void postInsertN(PostDTO dto); // 게시글 등록(일반)
	void postInsertS(PostDTO dto); // 게시글 등록(판매)
	
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

	void emailCodeInsert(EmailCheckDTO dto);


	void emailAuthenticationDelete(String m_email);

	void emailDelete(String m_email);

	String emailAuthenticationCheck(String m_email);
    int reEmailCheck(EmailCheckDTO dto);
	String likeCheck(String l_id, String l_postNo); // 좋아요 누르면 오르는 카운트
	void addLikeCount(String l_postNo); // 좋아요 누르면 오르는 카운트
	String likeCount(String l_postNo); // 게시물 좋아요 갯수

    void contactUsInsert(ContactUsDTO dto);
	void contactUsImgInsert(int c_id, byte[] i_image);


	
	
	
	
	//// 주문 및 결제 관련
	String getMerName(int mer_id);// 상품 이름
	int getMerPrice(int mer_id);// 상품 가격
	void merCountUp( int mer_id );// 주문 시도 횟수 +1
	int getMerCount(int mer_id);// 주문 시도 횟수 조회
	void orderInsert(OrderDTO dto);// 디비에 주문 넣기
	int getOrderPrice( String ord_id );// 주문의 금액 조회
}
