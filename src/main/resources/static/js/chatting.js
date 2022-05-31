/* chatting.html에 실제 채팅 기능 덧붙이기 
*/
 //// 초기 설정 
function chat_initialize( chatDiv ){
	chat_refresh( chatDiv );// 처음에 보여줄 이전 채팅 내역
	$(chatDiv).children('form').submit(function(){// 폼의 submit 이벤트 가로채기 (입력창에서 엔터키누르면)
		chat_send( this );// this: html form element
		return false;
	});
	setInterval( chat_refresh, 500, chatDiv );// 주기적으로 새로고침(새로 쌓인 채팅내역 불러오기)
}

//// ajax로 메시지 한 개 보내기 (이 버튼을 누른 form)
function chat_send( form ){
	let msg = $(form).find('.chat_content').val()// 메시지 내용
	if( msg.length == 0 ) return;// 내용 없으면 전송 안 함
	$.ajax({
		type: 'post',
		url: 'chat/send',
		data:{
			to: $(form).find('.chat_to').val(),// 메시지 받을 상대 회원번호
			h_content: msg
		},
		success: function(){
			chat_refresh( $(form).parents('.chatDiv') );// 채팅 내용 새로고침 하기
			$(form).find('.chat_content').val('');// 입력창 비우기
		},error: function(){console.log('메시지 전송 실패')}
	});
}

////// 채팅 내용 새로고침 (새로고침할 채팅 상자)
function chat_refresh( chatDiv ){
	//// 새로고침 전 스크롤이 끝까지 내려가있는 상태이면 채팅내용 새로고침 후 자동으로 스크롤 다시 끝까지 내리기
	var autoscrollCheck = endOfChatCheck( chatDiv );
	$.ajax({
		type: 'post',
		url: 'chat/refresh',
		data:{
			to: $(chatDiv).find('.chat_to').val(),
			since: $(chatDiv).find('.lastH_id').text()
		},
		success: function( data ){
			if( data.length > 0 ){// 새 채팅내역 있을 때만 (없는 경우에는 controller에서 empty.html을 반환)
				$(chatDiv).find('.lastH_id').remove();// 기존 <span#lastH_id> 삭제
				$(chatDiv).find('.chatHistory').append(data);// 새로 받은 채팅 내용 덧붙이기
				if( autoscrollCheck ) {
					$(chatDiv).find('.chatHistory_box').scrollTop(
							$(chatDiv).find('.chatHistory').outerHeight());// 맨 아래로 자동스크롤 (chatBox(채팅 보이는 영역) 안에서 스크롤을 chatHistory(채팅내역)의 높이만큼)
				}
			}
		},error: function(){console.log('불러오기 실패')}
	});
}

function endOfChatCheck( chatDiv ){// 채팅 내역 상자에서 스크롤 끝까지 내렸는가 검사
//// ??? 경계선, 패딩의 높이의 반까지 따져야 계산히 정확히 되지만 귀찮으니 일단 미뤄둠
	if(// 채팅의 끝 표시가 채팅박스의 밑변보다 위에 있으면(높이값이 작으면) 스크롤을 끝까지 내렸다고 판명
			$(chatDiv).find('.chatHistory_box').height() + $(chatDiv).find('.chatHistory_box').offset().top// 채팅박스의 밑변의 위치
			>= $(chatDiv).find('.endOfChat').offset().top - 50// 채팅의 끝 표시의 위치 + 여유 조금
	){
		return true;
	}else return false;
}



/*
팔로우목록에서 채팅 버튼 눌렀을 때
현재 페이지가 채팅 페이지이면 그 회원과의 채팅 페이지로 이동
현재 페이지가 채팅 페이지가 아니면 채팅 모달 띄우기
 */
function newChat( listener ){
	if( window.location.pathname == '/chat' ){// 현재 채팅페이지이면
		window.location.href = '/chat?to='+listener // 그 회원과의 채팅 페이지
	}else{// 채팅페이지 말고 다른 페이지이면
		chat_modal( listener )// 채팅모달
	}
}

/* 채팅 모달 만들기 
준비물: 채팅 모달들이 들어갈 상자 div#chatModals
*/
function chat_modal( listener ){// 회원번호 → body 하위에 그 회원과의 채팅 모달 만들기
	//// 그 회원과의 채팅이 이미 열려있으면 아무것도 안 하기
	chats = document.querySelectorAll('.chatDiv .chat_to');// ??? var
	for( let i=0; i < chats.length; i++ ){
		if( listener == chats[i].value ) return;// 열려는 채팅과 같은 회원번호 찾으면 더이상 아무것도 않고 이 함수 끝내기
	}
	//// ajax로 채팅 불러와서 모달에 넣기
	$.ajax({
		url: 'chatModal',
		type: 'get',
		data: {
			to: listener
		},success: function( data ){
			//// 모달창 만들기
			var modal = document.createElement('div');
			modal.className = 'chatModal'; 
			$('#chatModals').append(modal);
			//// 닫기 버튼
			var xbutton = document.createElement('button');// 요소 만들기
			modal.appendChild( xbutton );
			xbutton.className = 'xButton tinyButton';// 클래스
			xbutton.innerText = 'X';
			xbutton.addEventListener('click', function(){ this.parentNode.remove() });// 버튼 누르면 모달 닫기
			//// 채팅Div 붙이기
			$(modal).append( data );
			chat_initialize( $(modal).children('.chatDiv') );
			//// ??? 입력창에 자동 포커스
		},error: function(){
		}
	});
}

