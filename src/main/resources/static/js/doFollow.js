function doFollow( m_id ){
  var m2 = ""+m_id;
  $.ajax({
    type: 'get',
    url: 'func/doFollow',
    async: true,
    data:{ m1: '1', m2: m2 },// 1번 회원으로 로그인했다고 친다.
    success: function(){
      $('#followDiv').html('팔로우함');
    },error: function(){
      alert('팔로 실패');
    }
  });
}