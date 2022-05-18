$(function(){
	
	likeCheck();
	
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
			$("#like").remove();
			location.reload();
		},error: function(){
			console.log('에러');
			}
		});
	});
});

function likeCheck(){
// 게시글 들어갔을 때 좋아요를 누른 상태면 좋아요 버튼을 치우고 다른 버튼을 호출

	var m_id = $('#m_id').val();
	var p_id = $('#p_id').val();
	
	$.ajax({
		type: 'post',
		url: 'likeCheck',
		async: true,
		data:{
			p_id : p_id,
			m_id : m_id
		}, success: function(data){
			if(data == "OK"){
				$('.tag').prepend('<button id="unlike">누름</button>');
				$("#like").remove();
			}
		},error: function(){
			console.log('좋아요 불러오기 에러');
		}
	});
}