/*

게시글 div.post
├게시글 내용 article.postView
├메뉴바div.menubar
└댓글영역div.comments
　├댓글 작성 form.commentForm
　└댓글 목록 ul.commentList


좋아요
메뉴바 하위에 div 하위에 각각 좋아요 버튼, 취소 버튼
좋아요 눌린 상태 구별 지표: div의 클래스에 liked가 있고 아니면 없다
좋아요 안 눌린 상태이면 좋아요 버튼만,
좋아요 눌린 상태이면 취소 버튼만 표시된다: CSS

*/
//// 게시글(div.post) 초기설정
function postDivInitialize( postDiv, p_id ){
	//// 좋아요 여부 확인
	$.ajax({
		type: 'post',
		url: 'likeCheck',
		data:{
			p_id : p_id
		}, success: function(data){// data: 누름 여부 boolean
			if( data ){
				$(postDiv).find('.likeDiv').addClass('liked');
			}
		},error: function(){
			console.log('좋아요 불러오기 에러');
		}
	});
	
	//// 이미지 슬라이더 적용
	$( postDiv ).find('.bxSlider').bxSlider({
		captions: true
	});
}

//// 댓글 삭제
function deleteComment( button, c_id ){
	// 가져오는 c_id = comment.html의 th:c_id = ${comment.c_id}를 가져온다
	
	$.ajax({
		type: 'post',
		url: 'deleteComment', // SjhController
		async: true,
		data:{
			c_id : c_id
		},success: function(){
			$(button).parents('li').remove();
		},error: function(){
			console.log('댓글 삭제 실패');
		}
	});
}

//// 새 댓글 작성
function newComment( form ){// form: 댓글을 제출할 html form 요소
	var contentNode = $(form).find('.c_content');// 댓글 내용 적는 input 요소
	var postNo = $(form).find('.c_postNo').val();// 게시글 번호
	$.ajax({
		type: 'post',
		url: 'func/newComment',
		data: {
			c_content: $(contentNode).val(),
			c_postNo: postNo
		}, success: function( data ){
			$(contentNode).val('');// 댓글 입력창 비우기
			$(form).parents('.post').find('.commentList').prepend(data);// 댓글 전체 새로고침 안 하고 방금 쓴 댓글 하나만 댓글 목록에 갖다붙인다 그냥이다
		}, error: function( err ){
			console.log('댓글 작성 실패');
		}
	});
}

$('.heart-like-button').click(function(){
	
})


//// 좋아요 버튼 누름
function like( p_id, btn ){
	postDiv = $(btn).parents('.post');// 누른 버튼에 해당하는 게시글 div 요소 찾기
	//// 좋아요 추가하기 ajax
	$.ajax({
		type: 'post',
		url: 'likeToPost',
		async: true,
		data:{
			p_id : p_id
		},success: function(){
			likeCount( postDiv, p_id );// 좋아요 개수 새로고침
			$(btn).parent().addClass('liked');// 부모 요소에 liked 클래스 추가 (좋아요 누른 상태임을 표시)
		},error: function(){
			console.log('좋아요 실패');
		}
	});
}

//// 좋아요 취소 버튼 누름
function unlike( p_id, btn ){
	postDiv = $(btn).parents('.post');// 누른 버튼에 해당하는 게시글 div 요소 찾기
	//// 좋아요 취소하기 ajax
	$.ajax({
		type: 'post',
		url: 'unlikeToPost',
		async: true,
		data:{
			p_id : p_id
		},success: function(){
			likeCount( postDiv, p_id );// 좋아요 개수 새로고침
			$(btn).parent().removeClass('liked');// 부모 요소에 liked 클래스 제거 (좋아요 안 누른 상태임을 표시)
		},error: function(){
			console.log('좋아요 취소 실패');
		}
	});
}

// 좋아요 개수 디비에서 가져와서 표시
function likeCount( postDiv, p_id ){
		$.ajax({
		type: 'post',
		url: 'likeCount',
		data:{
			p_id : p_id
		}, success: function(data){
			$(postDiv).find('.likeCount').text(data);// 가져온 좋아요 수 갖다붙이기
		},error: function(){
			console.log('좋아요 개수 불러오기 에러');
		}
	});
};

//// 게시글 삭제
function deletePost( p_id ){
	// 가져오는 p_id = postView.html의 th:p_id = ${post.p_id}를 가져온다
	
	$.ajax({
		type: 'post',
		url: 'deletePost', // SjhController
		async: true,
		data:{
			p_id : p_id
		},success: function(){
			alert('게시글 삭제 완료');
			$(location).attr('href', "vip");
		},error: function(){
			console.log('댓글 삭제 실패');
		}
	});
}

// $(function(){

// 	likeCheck();
// 	likeCount();
// 	commentShow();
// 	reViewShow();

// 	var p_type = $('#p_type').val();
// 	var m_id = $('#m_id').val();
// 	var p_id = $('#p_id').val();

// 	$('#like').click(function like(){
// 		// 해당 글에 좋아요를 누르면 작동하는 ajax
// 		$.ajax({
// 		type: 'post',
// 		url: 'likeToPost',
// 		async: true,
// 		data:{
// 			m_id : m_id,
// 			p_id : p_id
// 		},success: function(){
// 			likeCheck();
// 			$('#like').remove();
// 		},error: function(){
// 			console.log('에러');
// 			}
// 		});
// 	});
	// $('#commentInsert').click(function commentInsert(){
	// 	// 댓글 쓰기
		
	// 	var commentWrite = $('#commentWrite').val();
	// 	var m_id = $('#m_id').val();
	// 	var p_id = $('#p_id').val();
		
	// 	$.ajax({
	// 	type: 'post',
	// 	url: 'commentInsert2',
	// 	data:{
	// 		m_id : m_id,
	// 		p_id : p_id,
	// 		commentWrite : commentWrite
	// 	},success: function(data){
	// 		if(data == "OK"){
	// 			console.log("작성완료");
	// 			$('#commentWrite').val("");
	// 			commentShow();
	// 		}
	// 	},error: function(){
	// 		console.log('댓글쓰기 에러');
	// 		}
	// 	});
	// })
	
	// // 판매 상태를 변경하는 function UPDATE 구문을 통해 DB에서 상태를 바꿔야함
	// $('#changeSell').click(function(){
	
	// 	// 상태를 판매중으로 바꿈
	// 	p_type = "S";
	
	// 	if($('#selling').val() == 'selling'){
		
	// 	// DB로 값을 넘겨주기 위한 ajax를 실행
	// 	$.ajax({
	// 	type: 'post',
	// 	url: 'changeSell',
	// 	data:{
	// 		p_type : p_type,
	// 		p_id : p_id
	// 	}, success: function(data){ 
	// 		alert('상태를 판매중 으로 변경했습니다'); 
	// 		location.reload();
	// 	 }, error: function(){
	// 		console.log('판매상태 변경 에러');
	// 	}
	//  });
	// 	}
		
	// 	if($('#selling').val() == 'stop'){

	// 	// 타입을 판매 중지(X)로 변경
	// 		p_type = "X"
	// 	// 이하 반복(판매 중지)
	// 	$.ajax({
	// 	type: 'post',
	// 	url: 'changeSell',
	// 	data:{
	// 		p_type : p_type,
	// 		p_id : p_id
	// 	}, success: function(data){
	// 		alert('상태를 판매 중지 으로 변경했습니다'); 
	// 		location.reload();
	// 	 }, error: function(){
	// 		console.log('판매상태 변경 에러');
	// 	}
	//  });
	// 	}
		
	// 	if($('#selling').val() == 'sellOut'){
		
	// 	// 타입을 품절(O = outofstock)로 변경
	// 		p_type = "O"
	// 	// 이하 반복(품절)
	// 	$.ajax({
	// 	type: 'post',
	// 	url: 'changeSell',
	// 	data:{
	// 		p_type : p_type,
	// 		p_id : p_id
	// 	}, success: function(data){
	// 		alert('상태를 품절 으로 변경했습니다'); 
	// 		location.reload();
	// 	 }, error: function(){
	// 		console.log('판매상태 변경 에러');
	// 	}
	//  });
	// 	}
	// })
	
	// // 리뷰작성 누르면 ajax가 작동하게 하기
	// $('#reView').click(function(){
	// 	alert('리뷰쓰기');
	// 	$.ajax({
	// 		type: 'post',
	// 		url: 'reViewCheck',
	// 		data:{
	// 			p_id : p_id
	// 		}, success: function(data){
	// 			if(data == "found"){ 
	// 				alert('리뷰 쓰기 페이지로 넘어갑니다')
	// 			$(location).attr('href', 'http://localhost:8080/postInsert2?p_id='+p_id)
	// 			}else if(data == "not found"){
	// 				alert('구매 정보가 없습니다')
	// 			}
	// 		 }, error: function(){
	// 			console.log('리뷰쓰기 에러');
	// 		}
	// 	});
	// })

// });

// 댓글 목록을 가져옴
// function commentShow(){
	
// 	var p_id = $('#p_id').val();
	
// 	$.ajax({
// 		type: 'post',
// 		url: 'CommentShow',
// 		data:{
// 			p_id : p_id
// 		}, success: function(data){
// 					$('#commentRead').html(data);
// 		},error: function(){
// 			console.log('댓글 목록 불러오기 에러');
// 		}
// 	});
// }




// 리뷰 목록을 가져옴
function reViewShow(){
	var p_id = $('#p_id').val();
	
	$.ajax({
		type: 'post',
		url: 'reViewShow',
		data:{
			p_id : p_id
		}, success: function(data){
			console.log(data);
			$(data).each(function(){
				$('.p_content').prepend('<button> 리뷰 갯수 ( ' + this.p_id + ' )</button> ' +'<br><br>');
			})
		},error: function(){
			console.log('리뷰 불러오기 에러');
		}
	});
}
