$(function(){

	likeCheck();
	likeCount();
// 	commentShow();
	
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