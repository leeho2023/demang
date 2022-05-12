$(document).ready(function() {
  var placeholderTarget = $('.textbox input[type="text"], .textbox input[type="password"]');
  
  //포커스시
  placeholderTarget.on('focus', function(){
    $(this).siblings('label').fadeOut('fast');
  });

  //포커스아웃시
  placeholderTarget.on('focusout', function(){
    if($(this).val() == ''){
      $(this).siblings('label').fadeIn('fast');
    }
  });
});

$(function(){
               
                
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
           
           })
        })