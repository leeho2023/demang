
function search() {

    var searchVal = $('#search').val();
    let first_char = searchVal.charAt(0);
    let reSearchVal = searchVal.substring(1, searchVal.length);
	

    if(first_char === '@'){
        location.href = "userSearch?reSearchVal="+reSearchVal;
        return;
    }

    if(first_char === '#'){
        location.href = "tagSearch?reSearchVal="+reSearchVal;
    }

	location.href = "postSearch?searchVal="+searchVal;
    return;
	
    

    // if(first_char === '@'){
    //     $.ajax({
    //         type: "get",
    //         url: "userSearch",
    //         data: {
    //             reSearchVal : reSearchVal
    //         },
    //         success: function (data) {
    //             $('.contentBox').html("");
    //             $('.contentBox').append(data);
    //         }
    //     });
    // }


    // $.ajax({
    //     type: "get",
    //     url: "postSearch",
    //     data: {
    //         searchVal : searchVal
    //     },
    //     success: function (data) {
    //         $('.contentBox').html("");
    //         $('.contentBox').append(data);
    //     }
    // });

    
}



$('#searchBtn').click(function (e) { 
    search(); 
});





/*(function() {

    const target = document.querySelector(".target");
    const links = document.querySelectorAll(".mynav .a12");
    const colors = ["deepskyblue", "orange", "firebrick", "gold", "magenta", "black", "darkblue"];
  
    function mouseenterFunc() {
      if (!this.parentNode.classList.contains("active")) {
        for (let i = 0; i < links.length; i++) {
          if (links[i].parentNode.classList.contains("active")) {
            links[i].parentNode.classList.remove("active");
          }
          links[i].style.opacity = "0.25";
        }
  
        this.parentNode.classList.add("active");
        this.style.opacity = "1";
  
        const width = this.getBoundingClientRect().width;
        const height = this.getBoundingClientRect().height;
        const left = this.getBoundingClientRect().left + window.pageXOffset;
        const top = this.getBoundingClientRect().top + window.pageYOffset;
        const color = colors[Math.floor(Math.random() * colors.length)];
  
        target.style.width = `${width}px`;
        target.style.height = `${height}px`;
        target.style.left = `${left}px`;
        target.style.top = `${top}px`;
        target.style.borderColor = color;
        target.style.transform = "none";
      }
    }
  
    for (let i = 0; i < links.length; i++) {
      links[i].addEventListener("click", (e) => e.preventDefault());
      links[i].addEventListener("mouseenter", mouseenterFunc);
    }
  
    function resizeFunc() {
      const active = document.querySelector(".mynav .li1.active");
  
      if (active) {
        const left = active.getBoundingClientRect().left + window.pageXOffset;
        const top = active.getBoundingClientRect().top + window.pageYOffset;
  
        target.style.left = `${left}px`;
        target.style.top = `${top}px`;
      }
    }
  
    window.addEventListener("resize", resizeFunc);
  
  })();*/

const indicator = document.querySelector('.nav-indicator');
const items = document.querySelectorAll('.nav-item');

function handleIndicator(el) {
  items.forEach(item => {
    item.classList.remove('is-active');
    item.removeAttribute('style');
  });
  
  indicator.style.width = `${el.offsetWidth}px`;
  indicator.style.left = `${el.offsetLeft}px`;
  indicator.style.backgroundColor = el.getAttribute('active-color');

  el.classList.add('is-active');
  el.style.color = el.getAttribute('active-color');
}


items.forEach((item, index) => {
  item.addEventListener('click', (e) => { handleIndicator(e.target)});
  item.classList.contains('is-active') && handleIndicator(item);
});

