document.addEventListener("DOMContentLoaded",function() {
  console.log('DOMContentLoaded');

  const doRetrieveButton = document.querySelector("#doRetrieveBtn");
  const moveToRegButton = document.querySelector("#moveToRegBtn");
  
  const pageNoInput     = document.querySelector("#pageNo");
  const searchDivSelect = document.querySelector("#searchDiv");
  const searchWordInput = document.querySelector("#searchWord");
  const pageSizeSelect  = document.querySelector("#pageSize");

  const rows = document.querySelectorAll("#listTable>tbody>tr");

  rows.forEach(function(row){
    row.addEventListener('dblclick', function(event){
        event.preventDefault();
        let cells = row.getElementsByTagName("td");

        //userId 배열에서 추출
        const roomId = cells[0].innerText;
        const senderId = cells[1].innerText;
        const receiverId = cells[2].innerText;
        
        if(sessionUserId !== senderId && sessionUserId !== receiverId){
          alert('채팅방 멤버가 아닙니다.');
          return;
        }
        
        if(confirm('채팅방에 입장하시겠습니까?') === false) return;

        //UserController.java
        //'/user/doSelectOne.do'를 get방식으로 userId를 전달!
        window.location.href = "/ehr/chat/sendChat_index.do?roomId=" + roomId+"&receiverId="+receiverId;
    });
});

  const userForm = document.querySelector("#userForm");
  doRetrieveButton.addEventListener('click', function(event){
    event.preventDefault();
    console.log("doRetrieveButton click");

    userForm.pageNo.value = 1;
    userForm.action = "/ehr/user/doRetrieve.do";

    userForm.submit();
  });

  moveToRegButton.addEventListener('click',function(event){
    event.preventDefault();
    console.log("moveToRegButton click");

    if(confirm('채팅방 생성 화면으로 이동하시겠습니까?') === false) return;

    window.location.href = "/ehr/chatroom/create_chatroom_index.do";
  });
});