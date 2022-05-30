$(function(){
	$('#m_profilePic').on('change', filechange);
});

function filechange(){ // 이미지 업로드시 실행
	if(window.FileReader){
		
		// 이미지 아닌 파일을 올린 경우
		if (!$(this)[0].files[0].type.match(/image\//)){
			propicMode_current();// 기존프사 모드로
			alert('이미지 파일만 업로드하십시오.');
			return;
		}
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
	// 전송 전 프사 선택 여부 체크
	if( $('#profilePicLabel').hasClass('preview') ){
		$('#isTherePropic').prop('checked', true);
	}else{// 선택 x이면 파일전송 굳이 안 하기
		$('#m_profilePic').val('');
	}
	
	return true;
}


/* var placeholderTarget = $('.textbox input[type="text"], .textbox input[type="password"]');
//포커스시
placeholderTarget.on('focus', function(){
	 $(this).siblings('label').fadeOut('fast');
});

//포커스아웃시
placeholderTarget.on('focusout', function(){
	 if($(this).val() == ''){
		$(this).siblings('label').fadeIn('fast');
	 }
});*/
$(function(){
	 /* 
					 
					//이름 유효성검사
					 $("#m_nickname").on("input",function(){
						var regex = /^[a-zA-Zㄱ-힣0-9-_.]{2,20}$/;
						var result = regex.exec($("#m_nickname").val());
						
						if(result != null){
							$(".name.regex").html("");
						}else{
								$(".name.regex").html("최소 2 자,한글과 영문,숫자만 입력가능합니다.");
								 $(".name.regex").css("color","red")
						}
						
					 })
			
				//비밀번호 유효성검사
				$("#m_password").on("input",function(){
					 var regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,1000}$/;
					 var result = regex.exec($("#m_password").val())
					 
					 if(result != null){
						$(".pw.regex").html("");
					 }else{
						$(".pw.regex").html("최소 8 자, 하나 이상의 특수문자,대문자,소문자,숫자를 입력해주세요. ");
						$(".pw.regex").css("color","red")
					 }
				});
				
			 
				$("#m_password").on("input",function(){
					 var regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,1000}$/;
					 var result = regex.exec($("#m_password").val())
					 
					 if(result != null){
						$(".pw.regex").html("");
					 }else{
						$(".pw.regex").html("최소 8 자, 하나 이상의 특수문자,대문자,소문자,숫자를 입력해주세요. ");
						$(".pw.regex").css("color","red");
					 }
				});
				
			

						
			 //회원가입 버튼 눌렀을 때, 빈칸 있으면 다시 유효성 검사진행
			$("#signupbtn").on("click",function(){
			
				var name = $("#m_nickname").val();
				var pw = $("#m_password").val();
				
			
			
				
		
				var nameregex = /^[a-zA-Zㄱ-힣0-9-_.]{2,20}$/;
				var pwregex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,1000}$/;
		
			
				
			
				 var nameregex = nameregex.exec(name);
				if(nameregex == null){
					alert("이름양식을 다시 확인해주세요");
					retrun;
				}
				var pwregex = pwregex.exec(pw);
				if(pwregex == null){
					alert("비밀번호양식을 다시 확인해주세요");
					retrun;
				}
				
				
				
			
				
				 //빈칸 없을 때 제출.
				$("#signUp").submit();
						alert("적용 완료");
			
			})*/
		})
		
		