
function search() {
    var searchVal = $('#search').val();

    // $.ajax({
    //     type: "get",
    //     url: "userSearch",
    //     data: {
    //         searchVal : searchVal
    //     },
    //     success: function (data) {
    //         $('.content1').html("");
    //         $('.content1').append(data);
    //     }
    // });

    $.ajax({
        type: "get",
        url: "boardSearch",
        data: {
            searchVal : searchVal
        },
        success: function (data) {
            $('.content2').html("");
            $('.content2').append(data);
        }
    });
}



$('#searchBtn').click(function (e) { 
    search(); 
});





