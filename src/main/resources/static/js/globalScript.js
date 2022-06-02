$(function(){
	// 햄버거 메뉴바
	const menuTrigger = document.querySelector('.menu-trigger');

	menuTrigger.addEventListener('click', (event) => {
		event.currentTarget.classList.toggle('active-1');
	});

})

/* 부모요소의 클래스에 open을 넣었다 뺐다하는 함수 */
function parentOpen( me ){// 누를 요소에서 onclick="이함수(this)"
	if( me.parentNode.classList.contains('open') ){// 부모가 열려있으면 
		me.parentNode.classList.remove('open'); // open 클래스 지우기
	}else{ // 부모가 닫혀있으면
		me.parentNode.className += ' open' // open 클래스 추가
	}
}

//// 검색
function homeNavSearch() {
	// 입력된 검색어 첫글자에 따라 검색 유형 달리
	var searchVal = $('#search').val();
	let first_char = searchVal.charAt(0);
	let reSearchVal = searchVal.substring(1, searchVal.length);
	
	if(first_char === '@'){// 회원 검색
		location.href = "search?type=member&val="+reSearchVal;
		return;
	}

	if(first_char === '#'){// 태그 검색
		location.href = "search?type=hashtag&val="+reSearchVal;
		return;
	}
	
	// 기타: 게시글 검색
	location.href = "search?type=post&val="+searchVal;
	return;
}


$(function(){
	
	//// 로그인 돼있어야 이하 실행
	if( sessionLogin == null ) return;
	//// 팔로우 목록 불러오기 
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
	
	//// 로그인 정보 표시 (상단바에) 
	//// ??? 미구현
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