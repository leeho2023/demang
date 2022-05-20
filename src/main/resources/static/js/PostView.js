$(function(){

	likeCheck();
	likeCount();
// 	commentShow();

	var p_type = $('#p_type').val();
	var m_id = $('#m_id').val();
	var p_id = $('#p_id').val();

	$('#like').click(function like(){
		// 해당 글에 좋아요를 누르면 작동하는 ajax
		$.ajax({
		type: 'post',
		url: 'likeToPost',
		async: true,
		data:{
			m_id : m_id,
			p_id : p_id
		},success: function(){
			likeCheck();
			$('#like').remove();
		},error: function(){
			console.log('에러');
			}
		});
	});
	
	$('#commentInsert').click(function commentInsert(){
		// 댓글 쓰기
		
		var commentWrite = $('#commentWrite').val();
		var m_id = $('#m_id').val();
		var p_id = $('#p_id').val();
		
		$.ajax({
		type: 'post',
		url: 'CommentInsert2',
		data:{
			m_id : m_id,
			p_id : p_id,
			commentWrite : commentWrite
		},success: function(data){
			if(data == "OK"){
				console.log("작성완료");
				$('#commentWrite').val("");
			}
		},error: function(){
			console.log('댓글쓰기 에러');
			}
		});
	})
	
	// 판매 상태를 변경하는 function UPDATE 구문을 통해 DB에서 상태를 바꿔야함
	$('#changeSell').click(function(){
		if($('#selling').val() == 'selling'){
			
			// 상태를 판매중으로 바꿈
			p_type = "S";
			
		// DB로 값을 넘겨주기 위한 ajax를 실행
		$.ajax({
		type: 'post',
		url: 'changeSell',
		data:{
			p_type : p_type,
			p_id : p_id
		}, success: function(data){
			if(data){ alert('상태를 판매중 으로 변경했습니다'); 
			}
		 }, error: function(){
			console.log('판매상태 변경 에러');
		}
	 });
			location.reload();
		}
		
		if($('#selling').val() == 'stop'){

		// 타입을 판매 중지(X)로 변경
			p_type = "X"
		// 이하 반복(판매 중지)
		$.ajax({
		type: 'post',
		url: 'changeSell',
		data:{
			p_type : p_type,
			p_id : p_id
		}, success: function(data){
			if(data){ alert('상태를 판매 중지 으로 변경했습니다'); 
			}
		 }, error: function(){
			console.log('판매상태 변경 에러');
		}
	 });
			location.reload();
		}
		
		if($('#selling').val() == 'sellOut'){
		
		// 타입을 품절(O = outofstock)로 변경
			p_type = "O"
		// 이하 반복(품절)
		$.ajax({
		type: 'post',
		url: 'changeSell',
		data:{
			p_type : p_type,
			p_id : p_id
		}, success: function(data){
			if(data){ alert('상태를 품절 으로 변경했습니다'); 
			}
		 }, error: function(){
			console.log('판매상태 변경 에러');
		}
	 });
			location.reload();
		}
	})
	
	// 리뷰작성 누르면 ajax가 작동하게 하기
	$('#reView').click(function(){
	
		$.ajax({
		type: 'post',
		url: 'reViewCheck',
		data:{
			p_id : p_id
		}, success: function(data){
			if(data == "found"){ 
				alert('리뷰 쓰기 페이지로 넘어갑니다')
			$(location).attr('href', 'http://localhost:8080/postInsert2?p_id='+p_id)
			}else if(data == "not found"){
				alert('구매 정보가 없습니다')
			}
		 }, error: function(){
			console.log('리뷰쓰기 에러');
		}
	 });
	 
	})
	
	
	
});

function likeCheck(){
// 게시글 들어갔을 때 좋아요를 누른 상태면 좋아요 버튼을 치우고 다른 버튼을 호출

	var p_id = $('#p_id').val();
	
	$.ajax({
		type: 'post',
		url: 'likeCheck',
		data:{
			p_id : p_id
		}, success: function(data){
			if(data >= 1){
				$('.tag').prepend('<button id="unlike">누름</button>');
				$('#like').remove();
				$('.likeCount').append('');
				likeCount();
				}
		},error: function(){
			console.log('좋아요 불러오기 에러');
		}
	});
};

function likeCount(){
	
	var p_id = $('#p_id').val();
	
		$.ajax({
		type: 'post',
		url: 'likeCount',
		data:{
			p_id : p_id
		}, success: function(data){
			if(data){
				$('#likeCount').text(data);
			}
		},error: function(){
			console.log('갯수 불러오기 에러');
		}
	});
};

// function commentShow(){
// 	
// 	var p_id = $('#p_id').val();
// 	
// 	$.ajax({
// 		type: 'post',
// 		url: 'CommentShow',
// 		data:{
// 			p_id : p_id
// 		}, success: function(data){
// 				$(data).each(function(){
// 					$('#commentRead').append(this.c_writer + "<br>");
// 					$('#commentRead').append(this.m_profilePic + "<br>");
// 					$('#commentRead').append(this.c_content + "<br>");
// 					$('#commentRead').append(this.c_regDate + "<br>");
// 				})
// 		},error: function(){
// 			console.log('댓글 목록 불러오기 에러');
// 		}
// 	});
// }