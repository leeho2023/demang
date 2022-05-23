/* 팔로우 버튼 눌러서 팔로우 하기( 인자: 대상 회원 id )
준비물

#followDiv

계정 주인의 id
m_id = [[${dto.m_id}]];
*/
function doFollow( m_id ){
	$.ajax({
		type: 'post',
		url: 'func/doFollow',
		async: true,
		data:{ m2: m_id },
		success: function(){
			$('#followDiv').html('팔로우함');
		},error: function(){
			alert('팔로우 실패');
		}
	});
}

/* 팔로우 되어있는지 확인 (컨트롤러에서 비교대상은 현재 세션 로그인 회원)
준비물
#followDiv
└#followBtn

팔로우중이면 팔로우 버튼 지우고 팔로우하고있다고 표시
*/
function followCheck( m_id ){
	$.ajax({
		type: 'get',
		url: 'func/followCheck',
		async: true,
		data: { m2: m_id },
	success: function( data ){
		if( data == 'O' ){// 팔로우 되어있으면
			$('#followBtn').remove();// 팔로우 버튼 등 지우고
			$('#followDiv').append('<div>팔로우 중</div>');// '팔로우함' 표시
		}
	},error: function(){
			console.log('팔로우 확인 실패');
		}
	});
}

$(function(){
	followCheck( m_id );// 팔로우 되어있나 확인 ~~ js/doFollow.js
});
