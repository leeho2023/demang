
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
	
    

    // if(first_char === '@'){
    //     $.ajax({
    //         type: "get",
    //         url: "userSearch",
    //         data: {
    //             reSearchVal : reSearchVal
    //         },
    //         success: function (data) {
    //             $('.contentBox').html("");
    //             $('.contentBox').append(data);
    //         }
    //     });
    // }


    // $.ajax({
    //     type: "get",
    //     url: "postSearch",
    //     data: {
    //         searchVal : searchVal
    //     },
    //     success: function (data) {
    //         $('.contentBox').html("");
    //         $('.contentBox').append(data);
    //     }
    // });

    
}



$('#searchBtn').click(function (e) { 
    search();
    return;
});





