$(function(){
    pageHide();
    $('#home').show();
    // $('#messages').show();
    // $('#home').show();
    // $('#home').show();
    // $('#home').show();

    const week = ['일','월','화','수','목','금','토']
    var now = new Date();
    var year = now.getDay();
    var dayOfWeek = week[now.getDay()];
    var dayOfWeekBox = $('#dayOfWeekBox');
    dayOfWeekBox.text(dayOfWeek+'요일');


})

/*==================== SHOW NAVBAR ====================*/
const showMenu = (headerToggle, navbarId) =>{
    const toggleBtn = document.getElementById(headerToggle),
    nav = document.getElementById(navbarId)
    
    // Validate that variables exist
    if(headerToggle && navbarId){
        toggleBtn.addEventListener('click', ()=>{
            // We add the show-menu class to the div tag with the nav__menu class
            nav.classList.toggle('show-menu')
            // change icon
            toggleBtn.classList.toggle('bx-x')
        })
    }
}
showMenu('header-toggle','navbar')

/*==================== LINK ACTIVE ====================*/
const linkColor = document.querySelectorAll('.nav__link')

function colorLink(){
    linkColor.forEach(l => l.classList.remove('active'))
    this.classList.add('active')
}

linkColor.forEach(l => l.addEventListener('click', colorLink))

/*======================== 페이지 숨김 ========================*/
function pageHide(){
    $('#home').hide();
    $('#profile').hide();
    $('#profile_user').hide();
    $('#profile_post').hide();
    $('#messages').hide();
    $('#search').hide();
    $('#log').hide();
}

/*======================== 페이지 이동 ========================*/
/*======================== home 페이지 ========================*/
$('.home').click(function(){
    pageHide();
    $('#home').show();
});
/*======================== profile 페이지 ========================*/
$('.profile').click(function(){
    pageHide();
    $('#profile').show();
});
/*======================== profile_user 페이지 ========================*/
$('.user').click(function(){
    pageHide();
    $('#profile_user').show();
});
/*======================== profile_post 페이지 ========================*/
$('.post').click(function(){
    pageHide();
    $('#profile_post').show();
});
/*======================== messages 페이지 ========================*/
$('.messages').click(function(){
    pageHide();
    $('#messages').show();
    $.ajax({
        type: "post",
        url: "messageList",
        data: { c_id : 0 },
        success: function ( data ) {
            $('#messageInfoList').html("");
            $('#messageInfoList').append(data);
        }
    });
    $.ajax({
        type: "post",
        url: "contactAllNumCount",
        success: function ( data ) {
            $('#pageNumList').html("");
            $('#pageNumList').append(data);
        }
    });
});

function messagePage(c_id){
    $.ajax({
        type: "post",
        url: "messageList",
        data: { c_id : c_id },
        success: function ( data ) {
            $('#messageInfoList').html("");
            $('#messageInfoList').append(data);
        }
    });
}
/*======================== search 페이지 ========================*/
$('.search').click(function(){
    pageHide();
    $('#search').show();
});
/*======================== log 페이지 ========================*/
$('.log').click(function(){
    pageHide();
    $('#log').show();
});

