

 $(function(){
                //email유효성 검사
                $("#m_email").on("input",function(){
                     var regex = /.+@[a-z]+(\.[a-z]+){1,2}$/;
                     var result = regex.exec($("#m_email").val());
                    
                    if(result != null){
                       $(".email.regex").html("");  
                    }else{
                        $(".email.regex").html("올바른 이메일이 아닙니다");
                    }
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
                    $(".pw.regex").html("최소 8 자, 하나 이상의 대문자, 하나의 소문자 및 하나의 숫자 ");
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
                    $(".pw.regex").html("최소 8 자,하나 이상의 대문자,소문자 및 숫자 ");
                    $(".pw.regex").css("color","red")
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
        	   
        	   
        	  
        	   
             //빈칸 없을 때 제출.
        	   $("#signUp").submit();
            	   
           
           })
        })
            
            