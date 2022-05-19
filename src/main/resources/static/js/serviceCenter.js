$(function(){
    
})

$('.contactUsModalBack').hide();
$('.contactUsModalBack2').hide();

$('.contactUs').click(function(){
    $('.contactUsModalBack').show();
});

$('.close').click(function(){
    $('.contactUsModalBack').hide();
});

$('.cancel').click(function(){
    $('.contactUsModalBack2').hide();
});
// 보내기를 눌렀을 때 컨트롤러로 가는지
$('.sendBtn').click(function(){
    // preventDefault();
    var contactInsert = $('#contactInsert');
    var title = $('.title').val();
    var contactUsValues = $('#c_contactUsValues').val();
    var content = $('#content').val();

    console.log(title);
    console.log(contactUsValues);
    console.log(content);

    if(title == ""){
        
        $('.contactUsModalBack2').show();
        $('.text').text('문의내용(제목, 문의종류, 내용)을 전부 입력해주세요');
        preventDefault();
    }

    if(contactUsValues == null &&
        contactUsValues === "계정" &&
        contactUsValues === "게시글" &&
        contactUsValues === "판매/구매" &&
        contactUsValues === "기타"
        ){
        
        $('.contactUsModalBack2').show();
        $('.text').text('문의내용(제목, 문의종류, 내용)을 전부 입력해주세요');
        preventDefault();
    }

    if(content == ""){
        
        $('.contactUsModalBack2').show();
        $('.text').text('문의내용(제목, 문의종류, 내용)을 전부 입력해주세요');
        preventDefault();
    }
    
    


    contactInsert.submit();
    $('.contactUsModalBack').hide();
    


});


// 이미지 파일 다중 업로드 js

var selFile = document.querySelector("input[type=file]");

/* 첨부파일 추가 */
function addFile(obj){
    
    var files = obj.files;
    let htmlData = '';
    for (var i = 0; i < files.length; i++) {
        const file = files[i];
        // 목록 추가
        htmlData += '<div id="file' + i + '" class="filebox">';
        htmlData += '   <div class="name">' + file.name;
        htmlData += '   <a class="delete" onclick="deleteFile(' + i + ');"><i class="far fa-minus-square"></i></a></div>';
        htmlData += '</div>';
    }
    document.querySelector('.file-list').innerHTML = htmlData;

    var maxFileCnt = 5;   // 첨부파일 최대 개수
    var curFileCnt = obj.files.length;  // 현재 선택된 첨부파일 개수

    // 첨부파일 개수 확인
    if (curFileCnt > maxFileCnt) {
        alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
        document.querySelector("input[type=file]").value = "";
        document.querySelector('.file-list').innerHTML = "";
    }
    
    

    
}

/* 첨부파일 삭제 */
function deleteFile(num) {
    var dt = new DataTransfer()
    var { files } = selFile;

    for (var i = 0; i < files.length; i++) {
        var file = files[i];
        if (num !== i) dt.items.add(file);
        selFile.files = dt.files;
    }

    document.querySelector("#file" + num).remove();
}

/* 폼 전송 */
function submitForm() {
    // 폼데이터 담기
    var form = document.querySelector("form");
    var formData = new FormData(form);
    for (var i = 0; i < filesArr.length; i++) {
        // 삭제되지 않은 파일만 폼데이터에 담기
        if (!filesArr[i].is_delete) {
            formData.append("files", filesArr[i]);
        }
    }

    $.ajax({
        method: 'POST',
        url: '/contactInsert',
        data: formData,
        success: function () {
            alert("파일업로드 성공");
        },
        error: function (xhr, desc, err) {
            alert('에러가 발생 하였습니다.');
            return;
        }
    })
}

