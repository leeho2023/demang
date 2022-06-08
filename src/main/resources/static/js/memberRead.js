

//// 탈퇴하기 버튼을 눌렀을 때
function withdraw(){
	if( $('#withdrawPWInput').val() === '' ){// 탈퇴하기 버튼 처음 눌러서 비밀번호 입력란 드러내기
		alert('확인을 위해 비밀번호를 한 번 더 입력해 주십시오.');
		$('#withdrawForm').css('display', 'block');
	}else{
		if( confirm('계정 정보가 영구삭제됩니다. 정말 탈퇴하시겠습니까?') == false ) return;// 취소 누르면 아무것도 안 함
		
		$('#withdrawForm').submit();// 탈퇴 요청 제출
	}
}

