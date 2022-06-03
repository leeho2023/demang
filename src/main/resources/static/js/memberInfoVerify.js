//// 유효성 검사용 정규식들
let regex_email = /.+@[a-z]+(\.[a-z]+){1,2}$/;
let regex_nickname = /^[a-zA-Zㄱ-힣0-9-_.]{2,30}$/;
let regex_password = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,60}$/;

//// 문자열 유효성 검사 (true, false)
function verify( str, regex ){// str이 regex에 맞는가
	return regex.test( str );
}

