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
	if( sessionLogin == null ) return;
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


// 검색
function search() {

    var searchVal = $('#search').val();
    let first_char = searchVal.charAt(0);
    let reSearchVal = searchVal.substring(1, searchVal.length);

    if(first_char === '@'){
        location.href = "userSearch?reSearchVal="+reSearchVal;
        return;
    }

    if(first_char === '#'){
        location.href = "tagSearch?reSearchVal="+reSearchVal;
        return;
    }

	location.href = "postSearch?searchVal="+searchVal;
    return;
}


