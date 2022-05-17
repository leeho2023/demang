$(function(){

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
			$(".tag").append('<div>좋아요를 눌렀습니다</div>');
			$("#like").remove();
		},error: function(){
			console.log('에러');
		}
	});		
});
});