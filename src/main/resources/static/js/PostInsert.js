$(function(){

	$('#textInput').hide();
	$('#defaultPrice').hide();
	
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
	
	$("input:radio[name='p_type']:input[value='S']").click(function(){
		$('.radio').append('<div id="sell">판매가격<input type="number" name="p_price">원</div>');
		$('#defaultPrice').remove();
	});
	
	$("input:radio[name='p_type']:input[value='N']").click(function(){
		$('#post').append('<input type="number" name="p_price" id="defaultPrice" value="0">');
		$('#sell').remove();
	});
		
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
