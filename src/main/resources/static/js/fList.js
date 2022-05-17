const modal = document.querySelector('.modal');
    const btnOpenPopup = document.querySelector('.btn-open-popup');
    btnOpenPopup.addEventListener('click', () => {
         modal.style.display = 'block'; 
        });
    const close = document.querySelector('.close');
    close.addEventListener('click', () => {
        modal.style.display = 'none'; 
        });


// 이메일 인증 관련 모달 숨겨놓기
$('.mailCheckModalBack').hide();
$('.mailCheckModalBack2').hide();

// 이메일 인증 관련 모달 닫기
$('.cancel').click(function(){
    $('.mailCheckModalBack').hide();
    $('.mailCheckModalBack2').hide();
});

// 이메일 중복 체크하고 없으면 메일 발송
var email
$('.mail').click(function(){
    email = $('.email').val(); // 입력한 이메일
    $('.sendEmail').text(email) // 모달창에 이메일 정보
    // 이메일 중복 체크
    $.ajax({
        type: "post",
        url: "emailReduplicationCheck",
        data: { email : email},
        success: function ( data ) {
            // 리턴값이 1이면 중복된 이메일 없으므로 해당 이메일로 메일 발송
            if(data == 1){
                $('.mailCheckModalBack').show();
                // 이메일 발송 ajax
                $.ajax({
                    type: "post",
                    url: "sendMail",
                    data: { email : email },
                    success: function ( data ) {
                        
                    }
                });
            }else{      // 그게 아니면 이메일 중복이란 메시지 담긴 모달 띄우기
                $('.mailCheckModalBack2').show();
            }
        }
    });
});

// 인증 버튼을 누르면 DB에 있는 이메일과 인증코드를 확인
$('.authentication').click(function(){
    $('.mailCheckModalBack').hide(); // 인증코드 입력 모달창 닫기
    var authenticationCode = $('.authenticationCode').val(); // 입력된 인증번호
    // 이메일과 인증번호 DB에 비교하는 ajax
    $.ajax({
        type: "post",
        url: "reEmailCheck",
        data: {
            m_email : email,
            e_code : authenticationCode
        },
        success: function ( data ) {
            // 리턴값이 1이면 인증 완료 0이면 인증 실패 alert 띄우고 다시 인증번호 입력창 띄울까?
            if(data == 1){
                alert("인증 완료");
            }else{
                alert("인증 실패");
                $('.mailCheckModalBack').show();
            }
        }
    });
});
        
        
$('.fListBtn').click(function(){
	
	$.ajax({
        type: "post",
        url: "fList",
        data: {
            follower : 1
		},
        success: function (data) {
            $('#fList').html("");
			$('#fList').append(data);
        }
    });
	
	
});


    