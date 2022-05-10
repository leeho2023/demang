$(function(){

	// 기본적으로 글 입력칸은 숨겨진 상태
    $('#textInput').hide();

    // 글 작성(다음) 버튼 클릭 시 작동하는 function
    $('#textInputBtn').click(function (e) { 
        e.preventDefault();

        // 글 내용으로 들어갈 문자값
        var textInput = $('#textInput').val();
        var imgInput = $('#innerImg').val();

        if(imgInput !== null){
        // 처음 버튼을 누르면 첫 요소들이 숨겨지고 글을 입력하는 input이 보여짐
        $('#write_img').hide();
        $('#write_imgNone').hide();
        $('#fileLabel').hide();
        $('#imgPreview').hide();
        $('#textInput').show();
        $('#innerImg').hide();
        	}

		// 이미지(선택 안했을 시 기본이미지 중 랜덤이 골라지게 할 예정)나 내용이 입력되지 않으면 경고메세지를 뜨게 함
        if(textInput == ""){
            alert('내용을 입력해 주세요');
        }
        // 정상적으로 작성 시 form태그가 작동되고 게시글이 작성 됨
        if(textInput !== ""){
            alert('전송완료');
            $('#post').submit();
        }
    });
    	//이미지가 삽입되는 id와 추가 될 이미지마다의 번호 지정
        var imgTarget = $('#input-file');
        var fileNum = 0;
        
        imgTarget.on('change', function(){ // 업로드 버튼에 마우스가 올려지면 요소가 변함(reader로 읽어옴)
            var parent = $('.imgPreview'); //이미지가 보여질 위치
    
            if(window.FileReader){
                //image 파일만
                if (!$(this)[0].files[0].type.match(/image\//)) return;
                //이미지가 아니면 return됨
                var reader = new FileReader();
                
                reader.onload = function(e){ // 순서상 마지막에 작동함
                    var src = e.target.result; // 이미지 주소 가져오기
                    parent.prepend('<li><img onclick="deleteImg(this)" src="'+src+'" class="innerImg" id="innerImg'+fileNum+'"></li>'); 
                    // ul내부에 li생성
                    fileNum += 1; // 다음에 추가 될 이미지 번호 늘려주기
                }
                reader.readAsDataURL($(this)[0].files[0]); // 등록된 이미지 읽어오기, 작동 순서상 2번째
            }
            else {
                $(this)[0].select();
                $(this)[0].blur();
                var imgSrc = document.selection.createRange().text;
                var img = $(this).siblings('#innerImg').find('img');
                img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";   
            }
	});
});
		function deleteImg(a){ // 이미지를 누르면 삭제
			$(a).parent().remove();
		};