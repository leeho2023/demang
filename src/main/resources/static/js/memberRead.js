

//// 탈퇴하기 버튼을 눌렀을 때
function withdraw(){
	let password = prompt('확인을 위해 비밀번호를 한 번 더 입력해 주십시오.');
	if( password == null ) return;// 취소 누르면 아무것도 안 함
	
	//// 비밀번호를 넘겨주며 탈퇴 시도
	// post 방식으로 넘겨주기 용도로 form 태그 만들기
	$('body').append('<form id="withdrawForm" action="/withdraw" method="post">'
		+'<input type="text" name="password" value="'+password+'">'
		+'</form>');
	// 제출
	$('#withdrawForm').submit();
}

