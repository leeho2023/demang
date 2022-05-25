//// 상품 항목 추가
function merAdd(){
	$('#merList').append('<li><label for="mer_name">상품명: <input type="text" id="mer_name" name="mer_name"></label><label for="mer_price">단가: <input type="number" id="mer_price" name="mer_price" ></label><label for="mer_amount">수량: <input type="number" id="mer_amount" name="mer_amount" value="1"> </label><button class="xbutton" onclick="deleteImg(this)">X</button></li>');
}

$(function(){
	//// 게시글 종류에서 판매 항목 누르면 상품목록 나오고 일반 누르면 없어지기
	$('#p_type_s').click((e)=>{// 판매
		$('#postForm').addClass('p_type_s');
	});
	$('#p_type_n').click((e)=>{// 일반
		$('#postForm').removeClass('p_type_s');
	});
});

/* 유효성 검사 항목
사진 1개 이상
본문 공백 아님
판매글일 경우 
	상품 1개 이상
	상품명 공백 아님
	단가, 수량 0 이상
 */



$(function(){

	$('#textInput').hide();
	$('#defaultPrice').hide();
	$('#defaultName').hide();
	$('#defaultAmount').hide();
	
	// 글 작성(다음) 버튼 클릭 시 작동하는 function
	$('#textInputBtn').click(function (e) { 
		e.preventDefault();
		
		// 글 내용으로 들어갈 문자값
		var textInput = $('#textInput').val();
		
		if($('.inputFile').length == 1 ){
			alert('사진을 등록 해 주세요');
			return;
		}
		if($('.inputFile').length > 1){
			$('#textInput').show();
			$('.labelWrap').attr('class', 'nextLabel');
			$('.innerImg').attr('onclick',"").unbind('click');
			$('.selectedImg > li:nth-child(1) > img').attr('class', 'thumb');
			$('.innerImg').hide();
			$('#file_label').hide();
		}
		if($('.inputFile').length > 1 && textInput == ""){
			alert('내용을 입력 해 주세요');
			return;
		}
		if(textInput !== "" && $('.inputFile').length > 1){
			alert('전송완료');
			$('#post').submit();
		}

	});

	$('#input_file').on('change', filechange);
	
// 	$("input:radio[name='p_type']:input[value='S']").click(function(){
// 		$('.radio').append('<div id="sell1">판매가격<input type="number" name="mer_price">원</div>');
// 		$('.radio').append('<div id="sell2">상품명<input type="text" name="mer_name"></div>');
// 		$('.radio').append('<div id="sell3">상품 갯수<input type="number" name="mer_amount"></div>');
// 		$('#defaultPrice').remove();
// 		$('#defaultName').remove();
// 		$('#defaultAmount').remove();
// 	});
	
	$("input:radio[name='p_type']:input[value='N']").click(function(){
		$('#post').append('<input type="number" name="mer_price" id="defaultPrice" value="0">');
		$('#post').append('<input type="text" name="mer_name" id="defaultName" value="">');
		$('#post').append('<input type="number" name="mer_amount" id="defaultAmount" value="0">');
		$('#sell1').remove();
		$('#sell2').remove();
		$('#sell3').remove();
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
		htmlData += '<li><input type="file" class= "inputFile" id="input_file" accept="image/*">';
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
