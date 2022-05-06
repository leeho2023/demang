

$('#searchBtn').click(function (e) { 
    var searchVal = $('#search').val();

    $.ajax({
        type: "get",
        url: "userSearch",
        data: {
            searchVal : searchVal
        },
        success: function (data) {
            
        }
    });

    $.ajax({
        type: "get",
        url: "boardSearch",
        data: {
            searchVal : searchVal
        },
        success: function (data) {
            
        }
    });
    
});

