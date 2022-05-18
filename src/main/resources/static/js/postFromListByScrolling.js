/* 필요
정수배열 postList
<section id="postList"></> 뒤에 <hr id="endOfPostListPosCheck">
*/

//// 게시글의 id가 적힌 목록을 처음에 가지고 있다. 스크롤을 끝까지 내리면 그 목록에서 꺼낸 숫자로 게시글 정보를 ajax로 가져와서 화면에 내보낸다.
num = 0;// 다음에 불러올 인덱스

//// 맨 처음 게시글 몇 개 불러오기
$(function(){
	if( postList.length == 0 ){// 불러올 게시글이 없는 경우
		$('section#postList').append("<div>게시글이 없습니다.</div>");
	}
	showPost();
});

////스크롤을 끝까지 내리면 글 더 불러오기
$(window).scroll(function(){// 스크롤 움직일 때마다 확인
	//// 스크롤을; 현재 화면에 나온 게시글 목록 끝까지 다 내리면
	if( $(window).height() + $(document).scrollTop()// 화면 맨아래의 위치 = 스크롤위치 + 화면세로크기
			>= $('#endOfPostListPosCheck').offset().top - 100 // 화면상 게시글 목록 끝 (메인 바로뒤의 요소의 위치) (100만큼 여분 추가)
			){
		showPost();}// 게시글 더 불러오기
	//// 그런데 왜 글이 한 개씩이 아니라 꼭 여러 개씩 불러지는지 모르겠는걸
});

//// 목록에서 가져온 번호(게시글id)를 게시글로 바꿔서 화면에 출력
function showPost(){
	if( num >= postList.length ) return;// 더이상 불러올 글이 없으면 아무것도 안 하기
	getPost( postList[num] );
	num += 1;
}

//// 글 번호를 통해 게시글 한 개 불러오기
function getPost( no ){$.ajax({
		type: 'post',
		url: 'getPostForFeed',
		async: false,// 결과가 정확히 최신순(목록에 있는 순)으로 나오도록 동기
		data:{
			no: no
		},success: function( data ){
			$('section#postList').append(data);
		},error: function( data ){
			console.log('게시글 불러오는 중 문제 발생 ― postFromListByScrolling.js');
		}
	});
}