//// 유효성 검사용 정규식들
let regex_email = /.+@[a-z]+(\.[a-z]+){1,2}$/;
let regex_nickname = /^[a-zA-Zㄱ-힣0-9-_.]{2,20}$/;
let regex_password = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;

//// 문자열 유효성 검사 (true, false)
function verify( str, regex ){// str이 regex에 맞는가
	return regex.test( str );
}


$(function(){
	document.getElementById('m_birth').value = new Date().toISOString().substring(0, 10);;
	
	/* 각 항목에 경고 메시지가 없음이 검사통과를, 메시지가 있음이 탈락을 의미한다. */
	
	//// email 유효성 검사
	$("#m_email").on("input",function(){// input에 입력될 때마다 검사
		$('.emailcode.regex').text('인증 미완료');// 이메일 input 값 바뀌면 이메일 인증 여부 초기화
		$(".email.regex").text("");
		//// 형식 유효성 검사
		if( !verify( 
				$("#m_email").val(), 
				regex_email 
			)){// 검사 탈락시
			$(".email.regex").text("올바른 이메일이 아닙니다");// 경고메시지 띄우고
			return;// 종료
		}
		
		//// 중복 검사
		$.ajax({
			url : "/emailDuplicateCheck",
			type : 'post',
			data : {
				m_email : $("#m_email").val()
			},
			success : function(data) {
				if (data) {// 모든 검사 통과: 경고메시지 제거
					$(".email.regex").text("");
					return;
				}else{// 중복시
					$(".email.regex").text("이메일이 중복됩니다.");
					return;
				}
			}, error: function(err){
				console.log('중복 검사 에러');
			}
		})
	})// email 유효성 검사
	
	
	
	//// 인증코드 보내기
	$('#sendMail').on('click', function(){
		//// 이메일 유효성 검사 탈락인 상태이면 아무것도 하지 않음.
		if( $(".email.regex").text() != '' || $("#m_email").val() == '' ){
			$(".emailcode.regex").text('올바른 이메일을 입력하십시오.');
			return;
		}
		
		//// 인증코드 발송 요청
		$.ajax({
			type: "post",
			url: "/sendMailCode",
			data: { 
				m_email : $("#m_email").val() 
			},
			success: function ( data ) {
				$(".emailcode.regex").text('이메일을 확인해주세요.');
			}, error: function ( data ) {
				console.log('이메일 요청 실패');
			}
		});
	});
	
	
	
	//// 인증코드 검사
	$('#verifyMail').on('click', function(){// 검사 버튼을 누를 때
		//// ajax로 검사
		$.ajax({
			type: "post",
			url: "/verifyEmail",
			data: {
				m_email : $('#m_email').val(),
				e_code : $('#e_code').val()
			},
			success: function ( data ) {
				if( data ){// 이메일 인증 완료
					$('.emailcode.regex').text('');
				}else{// 이메일 인증 실패
					$('.emailcode.regex').text('인증 실패');
				}
			}
		});
	});
	
	
	
	//// nickname 유효성검사
	$("#m_nickname").on("input",function(){
		if( !verify( 
				$("#m_nickname").val(), 
				regex_nickname ) 
			){// 검사 탈락시
			$(".name.regex").text("최소 2 자,한글과 영문,숫자만 입력가능합니다.");// 경고메시지 띄우고
			return;// 종료
		}
		$(".name.regex").text("");// 통과했으면 경고메시지 없애기
	})
	
	
	
	//// 비밀번호 유효성검사
	$("#m_password").on("input",function(){
		if( !verify( 
				$("#m_password").val(), 
				regex_password ) 
			){// 검사 탈락시
			$(".pw.regex").text("최소 8 자, 하나 이상의 특수문자,대문자,소문자,숫자를 입력해주세요.");// 경고메시지 띄우고
			return;// 종료
		}
		$(".pw.regex").text("");// 통과했으면 경고메시지 없애기
	});
	
	
	
	//// 비밀번호 일치 확인
	$("#m_re_password").on("input",function(){
		if( $("#m_password").val() != $("#m_re_password").val() ){// 불일치
			$(".repw.regex").text("비밀번호가 일치하지않습니다");
			return;
		}
		$(".repw.regex").text(""); // 통과했으면 경고메시지 없애기
	})
	
	
	
	//// 제출: 경고메시지 없고 빈칸 없으면 제출
	$("#signUp").on("submit",function(){
		//// 유효성 검사 모두 통과여야 제출됨
		if( $(".email.regex").text()     != '' ){ alert('이메일을 다시 확인하십시오.'); return false;}
		if( $(".emailcode.regex").text() != '' ){ alert('이메일 인증을 받으십시오.'); return false;}
		if( $(".name.regex").text()      != '' ){ alert('이름을 다시 확인하십시오.'); return false;}
		if( $(".pw.regex").text()        != '' ){ alert('비밀번호를 다시 확인하십시오.'); return false;}
		if( $(".repw.regex").text()      != '' ){ alert('비밀번호 확인란을 다시 입력하십시오.'); return false;}
		
		//// 빈칸 없어야 제출됨
		if( $("#m_email").val()       == '' ){ alert('이메일을 입력하십시오.'); return false;}
		if( $("#e_code").val()        == '' ){ alert('이메일 인증을 받으십시오.'); return false;}
		if( $("#m_nickname").val()    == '' ){ alert('이름을 입력하십시오.'); return false;}
		if( $("#m_password").val()    == '' ){ alert('비밀번호를 입력하십시오.'); return false;}
		if( $("#m_re_password").val() == '' ){ alert('비밀번호 확인란에 비밀번호를 똑같이 한 번 더 적으십시오.'); return false;}
		if( $("#m_birth").val()       == '' ){ alert('생일을 입력하십시오.'); return false;}
		
		//// 제출
		
	})
});

// 이메일 인증 관련 모달 숨겨놓기
$('.mailCheckModalBack2').hide();

// 이메일 인증 관련 모달 닫기
$('.cancel').click(function(){
	$('.mailCheckModalBack2').hide();
});


		
