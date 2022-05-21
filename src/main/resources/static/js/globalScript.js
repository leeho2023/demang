/* 부모요소의 클래스에 open을 넣었다 뺐다하는 함수 */
function parentOpen( me ){// 누를 요소에서 onclick="이함수(this)"
	if( me.parentNode.classList.contains('open') ){// 부모가 열려있으면 
		me.parentNode.classList.remove('open'); // open 클래스 지우기
	}else{ // 부모가 닫혀있으면
		me.parentNode.className += ' open' // open 클래스 추가
	}
}

/* 팔로우 목록 불러오기 */
$(function(){
	$.ajax({
		url: 'fList',
		method: 'post',
		data: {follower: sessionLogin},
		success: function( data ){
			$('#globalFList').replaceWith(data);
		},error: function(){
			console.log('팔로우 목록 불러오기 실패');
		}
	});
});
