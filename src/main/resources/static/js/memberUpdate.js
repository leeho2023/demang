$(function(){
	$('#m_profilePic').on('change', filechange);// 프사 업로드 시
	
	//// nickname 유효성검사
	$("#m_nickname").on("input",function(){
		//// 빈칸이면 검사 안 함
		if( $("#m_nickname").val() == '' ){
			$(".nickname.regex").text("");
			return;
		}
		
		//// 검사
		if( !verify( 
				$("#m_nickname").val(), 
				regex_nickname ) 
			){// 검사 탈락시
			$(".nickname.regex").text("2자에서 30자, 한글과 영문, 숫자만 입력가능합니다.");// 경고메시지 띄우고
			return;// 종료
		}
		$(".nickname.regex").text("");// 통과했으면 경고메시지 없애기
	})
	
	//// 비밀번호 유효성검사
	$("#m_password").on("input",function(){
		//// 빈칸이면 검사 안 함
		if( $("#m_password").val() == '' ){
			$(".password.regex").text("");
			return;
		}
		
		//// 검사
		if( !verify( 
				$("#m_password").val(), 
				regex_password ) 
			){// 검사 탈락시
			$(".password.regex").text("최소 8자, 하나 이상의 특수문자,대문자,소문자,숫자를 입력해주세요.");// 경고메시지 띄우고
			return;// 종료
		}
		$(".password.regex").text("");// 통과했으면 경고메시지 없애기
	})
});

function filechange(){ // 이미지 업로드시 실행
	if(window.FileReader){
		
		// 이미지 아닌 파일을 올린 경우
		if (!$(this)[0].files[0].type.match(/image\//)){
			propicMode_current();// 기존프사 모드로
			alert('이미지 파일만 업로드하십시오.');
			return;
		}
		
		//// 이미지 파일을 올린 경우: 그 프사 미리보기
		var reader = new FileReader();
		reader.onload = function(e){// 로딩 완료시 실행할 함수
			$('#profilePicPreview').attr('src', e.target.result);
			propicMode_preview();
		}
		reader.readAsDataURL( this.files[0] );// 등록된 이미지 읽어오기
	}
}

function easePropicChange( na ){// 프사 지우기 체크박스 누를 시 실행
	if( $(na).prop('checked') ){// 체크됨
		propicMode_default();// 기본프사모드로
	}else if( $('#m_profilePic').val() == '' ){// 업로드한 파일이 없으면
		propicMode_current();// 기존프사모드
	}else{// 업로드한 파일이 있으면
		propicMode_preview();// 새프사 모드
	}
}

function propicMode_default(){// 기본프사 모드
	$('#eraseProfilepic').prop(true);// 프사지우기 o
	//// 라벨의 클래스 바꾸기
	$('#profilePicLabel').removeClass('current');
	$('#profilePicLabel').removeClass('preview');
	$('#profilePicLabel').addClass('default');
}
function propicMode_current(){// 기존프사 모드
	$('#eraseProfilepic').prop('checked', false);// 프사지우기 x
	$('#m_profilePic').val('');// 파일선택 x
	$('#profilePicPreview').attr('src', '');// 미리보기 지우기
	//// 라벨의 클래스 바꾸기
	$('#profilePicLabel').removeClass('default');
	$('#profilePicLabel').removeClass('preview');
	$('#profilePicLabel').addClass('current');
}
function propicMode_preview(){// 새프사 모드
	$('#eraseProfilepic').prop('checked', false);// 프사지우기 x
	//// 라벨의 클래스 바꾸기
	$('#profilePicLabel').removeClass('current');
	$('#profilePicLabel').removeClass('default');
	$('#profilePicLabel').addClass('preview');
}

function memberUpdateSubmit(){// 제출시
	//// 제출값 유효성 검사 탈락시 제출 안 함
	if( $(".name.regex").text()      != '' ){ alert('이름을 다시 확인하십시오.'); return false;}
	if( $(".pw.regex").text()        != '' ){ alert('비밀번호를 다시 확인하십시오.'); return false;}
	
	// 전송 전 프사 선택 여부 체크
	if( $('#profilePicLabel').hasClass('preview') ){
		$('#isTherePropic').prop('checked', true);
	}else{// 선택 x이면 파일전송 굳이 안 하기
		$('#m_profilePic').val('');
	}
	
	return true;
}
