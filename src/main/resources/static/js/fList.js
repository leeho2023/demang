const modal = document.querySelector('.modal');
    const btnOpenPopup = document.querySelector('.btn-open-popup');
    btnOpenPopup.addEventListener('click', () => {
         modal.style.display = 'block'; 
        });
    const close = document.querySelector('.close');
    close.addEventListener('click', () => {
        modal.style.display = 'none'; 
        });
        
        
$('.fListBtn').click(function(){
	
	$.ajax({
        type: "post",
        url: "fList",
        data: {
            follower : 1
		},
        success: function (data) {
            $('#fList').html("");
			$('#fList').append(data);
        }
    });
	
	
});


    