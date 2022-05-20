var emailCheckresult = 0;
var m_email
$(function(){
                //email유효성 검사
                $("#m_email").on("input",function(){
							var m_email = $("#m_email").val();
                     var regex = /.+@[a-z]+(\.[a-z]+){1,2}$/;
                     var result = regex.exec($("#m_email").val());
                    
                    if(result != null){
                       $(".email.regex").html("");  
                    }else{
                        $(".email.regex").html("올바른 이메일이 아닙니다");
                        $(".email.regex").css("color","red");
                        return;
                    }
                    
                    
                    $.ajax({
                  url : "/emailCheck",
                  type : 'post',
                  data : {
                     m_email : m_email
                  },
                  success : function(data) {
                     var result = regex.exec(m_email);
                     if (data == "bad") {
                        $(".email.regex").html("이메일이 중복됩니다.");
                           $(".email.regex").css("color", "red");
                           return;
                     }
                     
                     if(data != "bad"){
                        $(".email.regex").html("사용가능");
                           $(".email.regex").css("color", "green");
                           emailCheck = true;
                           return;
                        }
                     
                        
                     }
                  
                  })
                })
                
                 //이름 유효성검사
                $("#m_nickname").on("input",function(){
                    var regex = /^[a-zA-Zㄱ-힣0-9-_.]{2,20}$/;
                    var result = regex.exec($("#m_nickname").val());
                    
                    if(result != null){
                       $(".name.regex").html("");  
                    }else{
                        $(".name.regex").html("최소 2 자,한글과 영문,숫자만 입력가능합니다.");
                    }
                    
                })
           
           	//비밀번호 유효성검사
            $("#m_password").on("input",function(){
                var regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;
                var result = regex.exec($("#m_password").val())
                
                if(result != null){
                    $(".pw.regex").html("");
                }else{
                    $(".pw.regex").html("최소 8 자, 하나 이상의 특수문자,대문자,소문자,숫자를 입력해주세요. ");
                    $(".pw.regex").css("color","red")
                }
            });
            
           //비밀번호 확인    
               $("#m_re_password").on("keyup",function(){
                    if($("#m_password").val()==$("#m_re_password").val()){
                       $(".repw.regex").html("비밀번호가 일치합니다"); 
                    }else{
                     $(".repw.regex").html("비밀번호가 일치하지않습니다"); 
                     
                    }
               })
              $("#m_password").on("input",function(){
                var regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;
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
        	   var email = $("#m_email").val();
        	   var name = $("#m_nickname").val();
        	   var pw = $("#m_password").val();
        	  	var re_pw = $("#m_re_password").val();
        	  
        	  
        	   
        	   var emailregex = /.+@[a-z]+(\.[a-z]+){1,2}$/;
        	   var nameregex = /^[a-zA-Zㄱ-힣0-9-_.]{2,20}$/;
        	   var pwregex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;
        	    var repwregex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;
        	  
        	   
        	  var emailregex = emailregex.exec(email);
        	   if(emailregex == null){
        		   alert("이메일양식을 다시 확인해주세요");
        		   retrun;
        	   }
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
        	   var repwregex = repwregex.exec(re_pw);
        	   if(repwregex == null){
        		   alert("비밀번호확인양식을 다시 확인해주세요");
        		   retrun;
        	   }
               

            $.ajax({
                type: "post",
                url: "reEmailCheck",
                data: {
                    m_email : $('#m_email').val(),
                    e_code : $('#e_code').val()
                },
                success: function ( data ) {
                    // 리턴값이 1이면 인증 완료 0이면 인증 실패 alert 띄우고 다시 인증번호 입력창 띄울까?
                    if(data == 1){
                        alert("회원가입 완료");
                        $("#signUp").submit();
                    }else{
                        alert("인증 실패");
                        $('.mailCheckModalBack').show();
                    }
                }
            });
        	   
             //빈칸 없을 때 제출.
             
			
             
        	   
             $("#signUp").submit();   
           
           })

        });



// 이메일 인증 관련 모달 숨겨놓기
$('.mailCheckModalBack2').hide();

// 이메일 인증 관련 모달 닫기
$('.cancel').click(function(){
    $('.mailCheckModalBack2').hide();
});

// 이메일 중복 체크하고 없으면 메일 발송

$('#sendMail').click(function(){

    $('.authenticationCode').val("");
    m_email = $('#m_email').val(); // 입력한 이메일
    if(m_email == ""){
        $(".email.regex").html('유효한 이메일을 입력해 주세요');
        return;
    }
    $('.sendEmail').text(m_email) // 모달창에 이메일 정보
    // 이메일 중복 체크
    $.ajax({
        type: "post",
        url: "emailReduplicationCheck",
        data: { m_email : m_email},
        success: function ( data ) {
            // 리턴값이 1이면 중복된 이메일 없으므로 해당 이메일로 메일 발송
            if(data == 1){
                // 이메일 발송 ajax
                $.ajax({
                    type: "post",
                    url: "sendMail",
                    data: { m_email : m_email },
                    success: function ( data ) {
                        alert('이메일을 확인해주세요');
                    }
                });
            }else{      // 그게 아니면 이메일 중복이란 메시지 담긴 모달 띄우기
                $('.mailCheckModalBack2').show();
            }
        }
    });
});
