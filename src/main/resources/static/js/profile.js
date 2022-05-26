/* 팔로우 관련

팔로우 확인
팔로우 버튼 눌러서 팔로우

준비물: #followBtn

*/

/* 팔로우 버튼 누를 시 실행됨
팔로우 하기(이미 팔로우 중인 경우 팔로우 취소하기: 서버에서 구분하여 조작)

계정 주인의 id
m_id = [[${dto.m_id}]];
*/
function doFollow( m_id ){// m_id: 팔로우 대상 회원 id
	$.ajax({
		type: 'post',
		url: 'func/doFollow',
		async: true,
		data:{ m2: m_id },
		success: function(){// 동작 성공시
			followCheck( m_id )// 팔로우 상태 새로고침
		},error: function(){
			alert('팔로우 실패');
		}
	});
}

/* 현재 로그인 회원이 현재 페이지의 주인을 팔로우 하고 있는지 확인 후
팔로우 여부에 따라 팔로우 버튼 표시를 "팔로우 하기", "팔로우 취소"로 바꾸기
*/
function followCheck( m_id ){
	$.ajax({
		type: 'get',
		url: 'func/followCheck',
		async: true,
		data: { m2: m_id },
	success: function( data ){
		if( data ){// 팔로우 되어있으면
			$('#followBtn').text('팔로우 취소');// 팔로우 취소 버튼으로 표시
		}else{// 아니면
			$('#followBtn').text('팔로우 하기');// 팔로우 버튼으로 표시
		}
	},error: function(){
			console.log('팔로우 확인 실패');
		}
	});
}

