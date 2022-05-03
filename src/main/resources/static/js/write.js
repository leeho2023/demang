$(function(){

    $('#textInput').hide();

    // 글 작성(다음) 버튼 클릭 시 작동하는 function
    $('#textInputBtn').click(function (e) { 
        e.preventDefault();

        // 글 내용으로 들어갈 문자값
        var textInput = $('#textInput').val();
        var imgInput = $('#innerImg').val();
        
        if(textInput == "" && imgInput !== null){
        	alert('내용을 입력해 주세요');
        $('#write_img').hide();
        $('#write_imgNone').hide();
        $('#fileLabel').hide();
        $('#textInput').show();
        $('#innerImg').hide();
        }
		 if(textInput !== ""){
		 	alert('전송완료');
		 	$('#post').submit();
		 }

    });

        var imgTarget = $('#imgWrap #input-file');
    
        imgTarget.on('change', function(){
            var parent = $(this).parent();
            parent.children('#innerImg').remove();
    
            if(window.FileReader){
                //image 파일만
                if (!$(this)[0].files[0].type.match(/image\//)) return;
                var reader = new FileReader();
                reader.onload = function(e){
                    var src = e.target.result;
                    parent.prepend('<img src="'+src+'" class="innerImg" id="innerImg">');
                    $('#fileLabel').hide();
                }
                reader.readAsDataURL($(this)[0].files[0]);
            }
    
            else {
                $(this)[0].select();
                $(this)[0].blur();
                var imgSrc = document.selection.createRange().text;
    
                var img = $(this).siblings('#innerImg').find('img');
                img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";   
            }
        });

    $('#write_imgNone').click(function(e){
        e.preventDefault();

        $('#fileLabel').show();
        $('#innerImg').remove();
        
    });

});