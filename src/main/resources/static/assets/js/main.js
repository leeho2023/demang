$(function(){
    // pageHide();
    // $('#home').show();
    // $('#messages').show();
    // $('#home').show();
    // $('#home').show();
    // $('#home').show();

    const week = ['일','월','화','수','목','금','토'];
    var now = new Date();
    var year = now.getDay();
    var dayOfWeek = week[now.getDay()];
    var dayOfWeekBox = $('#dayOfWeekBox');
    dayOfWeekBox.text(dayOfWeek+'요일');


});

/*==================== SHOW NAVBAR ====================*/
const showMenu = (headerToggle, navbarId) =>{
    const toggleBtn = document.getElementById(headerToggle);
    nav = document.getElementById(navbarId);
    
    // Validate that variables exist
    if(headerToggle && navbarId){
        toggleBtn.addEventListener('click', ()=>{
            // We add the show-menu class to the div tag with the nav__menu class
            nav.classList.toggle('show-menu');
            // change icon
            toggleBtn.classList.toggle('bx-x');
        });
    };
};
showMenu('header-toggle','navbar');

/*==================== LINK ACTIVE ====================*/
const linkColor = document.querySelectorAll('.nav__link');

function colorLink(){
    linkColor.forEach(l => l.classList.remove('active'));
    this.classList.add('active');
};

linkColor.forEach(l => l.addEventListener('click', colorLink));

/*======================== 페이지 숨김 ========================*/
// function pageHide(){
//     $('#home').hide();
//     $('#profile').hide();
//     $('#profile_user').hide();
//     $('#profile_post').hide();
//     $('#messages').hide();
//     $('#search').hide();
//     $('#log').hide();
// }

/*======================== 페이지 이동 ========================*/
/*======================== home 페이지 ========================*/
$('.home').click(function(){
    // pageHide();
    // $('#home').show();
});
/*======================== profile 페이지 ========================*/
$('.profile').click(function(){
    // pageHide();
    // $('#profile').show();
});
/*======================== profile_user 페이지 ========================*/
$('.user').click(function(){
    // pageHide();
    // $('#profile_user').show();
});
/*======================== profile_post 페이지 ========================*/
$('.post').click(function(){
    // pageHide();
    // $('#profile_post').show();
});
/*======================== messages 페이지 ========================*/
$('.messages').click(function(){
    // pageHide();
    // $('#messages').show();
    // $.ajax({
    //     type: "get",
    //     url: "contactAllNumCount",
    //     success: function ( data ) {
    //         $('#messageInfoList').html("");
    //         $('#messageInfoList').append(data);
    //     }
    // });
});


// 경고 관련 ajax

function warnCountUp(m_id, elem){
    $.ajax({
        type: "post",
        url: "warnCountUp",
        data: {
            m_id : m_id
        },
        success: function ( data ) {
            $(elem).parents('li').children('.m_warnCount').text(data);
        }
    });
};

function warnCountDown(m_id, elem){
    $.ajax({
        type: "post",
        url: "warnCountDown",
        data: {
            m_id : m_id
        },
        success: function ( data ) {
            $(elem).parents('li').children('.m_warnCount').text(data);
        }
    });
};


function closeBtn(){
    $('#messageOneSelect').show();
    $('.answerModalBack').hide();
};



// ajax로 contactUsDTO 한개 불러오기
function messageOneSelect(c_id){
    $.ajax({
        type: "post",
        url: "messageOneSelect",
        data: {
            c_id : c_id
        },
        success: function ( data ) {
            $('#messageOneSelect').html("");
            $('.answerModalBack').css('display','none');
            $('#messages').hide();
            $('#messageOneSelect').css('display','flex');
            $('#messageOneSelect').append(data);
        }
    });
};

// ajax로 메일 보내는 폼 나타내기
function writeMail(){
    var m_email = $('#m_email').val();
    var c_id = $('#c_id').val();

    $.ajax({
        type: "post",
        url: "sendMailForm",
        data: {
            m_email : m_email,
            c_id : c_id
        },
        success: function ( data ) {
            $('.answerModalBack').html("");
            $('#messageOneSelect').hide();
            $('.answerModalBack').css('display','flex');
            $('.answerModalBack').append(data);
        }
    });
};

// 한개 메시지 닫기
function messageClose(){
    $('#messageOneSelect').css('display','none')
    $('#messages').show();
    location.reload();
};



// 메일 보내는 부분
function sendMail(){

    var content = $('#content').val();
    var answerInsert = $('#answerInsert');
    if(content == ""){
        alert('내용을 입력해주세요');
        return;
    }

    answerInsert.submit();
    
}

/*======================== search 페이지 ========================*/
$('.search').click(function(){
    // pageHide();
    // $('#search').show();
});
/*======================== log 페이지 ========================*/
$('.log').click(function(){
    // pageHide();
    // $('#log').show();
});

function search(){
    var header_input = $('#header_input');
    var adminSearch = $('#adminSearch');


}