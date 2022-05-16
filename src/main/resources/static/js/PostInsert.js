$(function(){

	$('#textInput').hide();
	
	// 글 작성(다음) 버튼 클릭 시 작동하는 function
	$('#textInputBtn').click(function (e) { 
		e.preventDefault();
		
		// 글 내용으로 들어갈 문자값
		var textInput = $('#textInput').val();
		var imgInput = $('.innerImg').val();
		
		if(imgInput !== null){
			$('#textInput').show();
			$('.labelWrap').attr('class', 'nextLabel');
			$('.innerImg').attr('onclick',"").unbind('click');
			$('.selectedImg > li:nth-child(1) > img').attr('class', 'thumb');
			$('.innerImg').hide();
			$('#file_label').hide();
		}
		
		if(textInput == "" && imgInput !== null){
			alert('내용을 입력해 주세요');
		}
		
		if(textInput !== ""){
			alert('전송완료');
			$('#post').submit();
		}

	});

	$('#input_file').on('change', filechange);
		
});

	// 등록 된 이미지를 누르면 삭제하는 function
	function deleteImg(e){
		$(e).parent().remove();
	};

function filechange(){ // 업로드 버튼에 마우스가 올려지면 요소가 변함(reader로 읽어옴)
	if( $('.selectedImg > li').length > 5 ){
		return;
	}
	var parent = $('.selectedImg'); //이미지가 보여질 위치
	if(window.FileReader){
	//image 파일만
	if (!$(this)[0].files[0].type.match(/image\//)) return;
	//이미지가 아니면 return됨
	var reader = new FileReader();
	
	reader.onload = function(e){ // 순서상 마지막에 작동함
		var src = e.target.result; // 이미지 주소 가져오기
		$('#file_label').remove();  
		document.getElementById('input_file').setAttribute('name', 'p_image');
		$('#input_file').parent().append('<img onclick="deleteImg(this)" src="'+src+'" class="innerImg">');
		$('#input_file').removeAttr('id');
		let htmlData = '';
		htmlData += '<li><input type="file" id="input_file" accept="image/*">';
		htmlData += '<label for="input_file" class="label" id="file_label"></label>';
		htmlData += '</li>';
		parent.append(htmlData);
		$('#input_file').on('change', filechange);
	}
		reader.readAsDataURL($(this)[0].files[0]);  // 등록된 이미지 읽어오기, 작동 순서상 2번째
	}
	else {
                $(this)[0].select();
                $(this)[0].blur();
                var imgSrc = document.selection.createRange().text;
                var img = $(this).siblings('#innerImg').find('img');
                img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";   
	}
}
/*
//다중파일 업로드 복붙

var fileNo = 0;
var filesArr = new Array();

// 첨부파일 추가 
function addFile(obj){
    var maxFileCnt = 5;   // 첨부파일 최대 개수
    var attFileCnt = document.querySelectorAll('.filebox').length;    // 기존 추가된 첨부파일 개수
    var remainFileCnt = maxFileCnt - attFileCnt;    // 추가로 첨부가능한 개수
    var curFileCnt = obj.files.length;  // 현재 선택된 첨부파일 개수

    // 첨부파일 개수 확인
    if (curFileCnt > remainFileCnt) {
        alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
    } else {
        for (const file of obj.files) {
            // 첨부파일 검증
            if (validation(file)) {
                // 파일 배열에 담기
                var reader = new FileReader();
                reader.onload = function () {
                    filesArr.push(file);
                };
                reader.readAsDataURL(file);

                // 목록 추가
                let htmlData = '';
                htmlData += '<div id="file' + fileNo + '" class="filebox">';
                htmlData += '   <p class="name">' + file.name + '</p>';
                htmlData += '   <a class="delete" onclick="deleteFile(' + fileNo + ');"><i class="fa fa-minus-square-o"></i></a>';
                htmlData += '</div>';
                $('.file-list').append(htmlData);
                fileNo++;
            } else {
                continue;
            }
        }
    }
    // 초기화
    document.querySelector("input[type=file]").value = "";
}

// 첨부파일 검증
function validation(obj){
    const fileTypes = ['application/pdf', 'image/gif', 'image/jpeg', 'image/png', 'image/bmp', 'image/tif', 'application/haansofthwp', 'application/x-hwp'];
    if (obj.name.length > 100) {
        alert("파일명이 100자 이상인 파일은 제외되었습니다.");
        return false;
    } else if (obj.size > (100 * 1024 * 1024)) {
        alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
        return false;
    } else if (obj.name.lastIndexOf('.') == -1) {
        alert("확장자가 없는 파일은 제외되었습니다.");
        return false;
    } else if (!fileTypes.includes(obj.type)) {$(function(){

	$('#textInput').hide();
	
	// 글 작성(다음) 버튼 클릭 시 작동하는 function
	$('#textInputBtn').click(function (e) { 
		e.preventDefault();
		
		// 글 내용으로 들어갈 문자값
		var textInput = $('#textInput').val();
		var imgInput = $('#innerImg').val();
		
		if(imgInput !== null){
			$('#write_img').hide();
			$('#write_imgNone').hide();
			$('#textInput').show();
			$('#innerImg').hide();
			$('label').hide();
		}
		
		if(textInput == "" && imgInput !== null){
			alert('내용을 입력해 주세요');
		}
		
		if(textInput !== ""){
			alert('전송완료');
			$('#post').submit();
		}

	});

        
	$('#input_file').on('change', filechange);
});
// 등록 된 이미지를 누르면 삭제하는 function
function deleteImg(e){
	$(e).parent().remove();
};

function filechange(){ // 업로드 버튼에 마우스가 올려지면 요소가 변함(reader로 읽어옴)
	if( $('.selectedImg > li').length > 5 ){
		return;
	}
	var parent = $('.selectedImg'); //이미지가 보여질 위치
	if(window.FileReader){
	//image 파일만
	if (!$(this)[0].files[0].type.match(/image\//)) return;
	//이미지가 아니면 return됨
	var reader = new FileReader();
	
	reader.onload = function(e){ // 순서상 마지막에 작동함
		var src = e.target.result; // 이미지 주소 가져오기
		$('#file_label').remove();  
		document.getElementById('input_file').setAttribute('name', 'p_image');
		$('#input_file').parent().append('<img onclick="deleteImg(this)" src="'+src+'" class="innerImg">');
		$('#input_file').removeAttr('id');
		let htmlData = '';
		htmlData += '<li><input type="file" id="input_file" accept="image/*">';
		htmlData += '<label for="input_file" class="label" id="file_label">라벨입니다</label>';
		htmlData += '</li>';
		parent.append(htmlData);
		$('#input_file').on('change', filechange);
	}
		reader.readAsDataURL($(this)[0].files[0]);  // 등록된 이미지 읽어오기, 작동 순서상 2번째
	}
	else {
                $(this)[0].select();
                $(this)[0].blur();
                var imgSrc = document.selection.createRange().text;
                var img = $(this).siblings('#innerImg').find('img');
                img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";   
	}
}
/*
//다중파일 업로드 복붙

var fileNo = 0;
var filesArr = new Array();

// 첨부파일 추가 
function addFile(obj){
    var maxFileCnt = 5;   // 첨부파일 최대 개수
    var attFileCnt = document.querySelectorAll('.filebox').length;    // 기존 추가된 첨부파일 개수
    var remainFileCnt = maxFileCnt - attFileCnt;    // 추가로 첨부가능한 개수
    var curFileCnt = obj.files.length;  // 현재 선택된 첨부파일 개수

    // 첨부파일 개수 확인
    if (curFileCnt > remainFileCnt) {
        alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
    } else {
        for (const file of obj.files) {
            // 첨부파일 검증
            if (validation(file)) {
                // 파일 배열에 담기
                var reader = new FileReader();
                reader.onload = function () {
                    filesArr.push(file);
                };
                reader.readAsDataURL(file);

                // 목록 추가
                let htmlData = '';
                htmlData += '<div id="file' + fileNo + '" class="filebox">';
                htmlData += '   <p class="name">' + file.name + '</p>';
                htmlData += '   <a class="delete" onclick="deleteFile(' + fileNo + ');"><i class="fa fa-minus-square-o"></i></a>';
                htmlData += '</div>';
                $('.file-list').append(htmlData);
                fileNo++;
            } else {
                continue;
            }
        }
    }
    // 초기화
    document.querySelector("input[type=file]").value = "";
}

// 첨부파일 검증
function validation(obj){
    const fileTypes = ['application/pdf', 'image/gif', 'image/jpeg', 'image/png', 'image/bmp', 'image/tif', 'application/haansofthwp', 'application/x-hwp'];
    if (obj.name.length > 100) {
        alert("파일명이 100자 이상인 파일은 제외되었습니다.");
        return false;
    } else if (obj.size > (100 * 1024 * 1024)) {
        alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
        return false;
    } else if (obj.name.lastIndexOf('.') == -1) {
        alert("확장자가 없는 파일은 제외되었습니다.");
        return false;
    } else if (!fileTypes.includes(obj.type)) {
        alert("첨부가 불가능한 파일은 제외되었습니다.");
        return false;
    } else {
        return true;
    }
}

// 첨부파일 삭제
function deleteFile(num) {
    document.querySelector("#file" + num).remove();
    filesArr[num].is_delete = true;
}

// 폼 전송
function submitForm() {
    // 폼데이터 담기
    var form = document.querySelector("form");
    var formData = new FormData(form);
    for (var i = 0; i < filesArr.length; i++) {
        // 삭제되지 않은 파일만 폼데이터에 담기
        if (!filesArr[i].is_delete) {
            formData.append("attach_file", filesArr[i]);
        }
    }
}
*/


        alert("첨부가 불가능한 파일은 제외되었습니다.");
        return false;
    } else {
        return true;
    }
}

// 첨부파일 삭제
function deleteFile(num) {
    document.querySelector("#file" + num).remove();
    filesArr[num].is_delete = true;
}

// 폼 전송
function submitForm() {
    // 폼데이터 담기
    var form = document.querySelector("form");
    var formData = new FormData(form);
    for (var i = 0; i < filesArr.length; i++) {
        // 삭제되지 않은 파일만 폼데이터에 담기
        if (!filesArr[i].is_delete) {
            formData.append("attach_file", filesArr[i]);
        }
    }
}
*/

