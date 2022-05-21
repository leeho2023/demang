/* 누르면 부모요소의 클래스에 open을 넣었다 뺐다 */
function parentOpen( me ){// 누를 요소에서 onclick="이함수(this)"
	if( me.parentNode.classList.contains('open') ){// 부모가 열려있으면 
		me.parentNode.classList.remove('open'); // open 클래스 지우기
	}else{ // 부모가 닫혀있으면
		me.parentNode.className += ' open' // open 클래스 추가
	}
}






