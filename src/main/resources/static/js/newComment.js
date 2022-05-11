/*
준비물: <ul class="commentList">
ul 안에 li로 방금 쓴 댓글 넣기???
아니면 아예 댓글 전체 새로고침???
*/
function newComment( m_id, me, c_postNo ){
	var c_content = $(me).parent().children('input').val();
	$.ajax({
		type: 'post',
		url: 'func/newComment',
		data: {
			c_writer: m_id,
			c_content: c_content,
			c_postNo: c_postNo
		},
		success: function( data ){
			$(me).parent().html( data );
		},error: function(){
			console.log('댓글 등록 실패');
		}
	});
}