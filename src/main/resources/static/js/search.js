// 검색
function search() {

    var searchVal = $('#search').val();
    let first_char = searchVal.charAt(0);
    let reSearchVal = searchVal.substring(1, searchVal.length);
	

    if(first_char === '@'){
        location.href = "userSearch?reSearchVal="+reSearchVal;
        return;
    }

    if(first_char === '#'){
        location.href = "tagSearch?reSearchVal="+reSearchVal;
        return;
    }

	location.href = "postSearch?searchVal="+searchVal;
    return;
}





