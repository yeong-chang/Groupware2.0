document.addEventListener('DOMContentLoaded',function(){
    console.log('DOMContentLoaded');
    
    const doCreateButton = document.querySelector("#doCreate");
    const roomIdInput = document.querySelector("#roomId");
    const userId1Input = document.querySelector("#userId1");
    const userId2Input = document.querySelector("#userId2");
    const moveToPreviousButton = document.querySelector("#moveToPrevious");

    doCreateButton.addEventListener('click',function(event){
        event.preventDefault();
        console.log('doCreateButton click');

        if(isEmpty(roomIdInput) === true){
            alert('채팅방 번호를 입력하세요.');
            roomIdInput.focus();
            return;
        }

        if(isEmpty(userId1Input) === true){
            alert('유저1의 ID를 입력하세요.');
            userId1Input.focus();
            return;
        }

        if(isEmpty(userId2Input) === true){
            alert('유저2의 ID를 입력하세요.');
            return;
        }

        if(confirm('채팅방을 생성하시겠습니까?') === false) return;

        $.ajax({
            type: "POST",   
            url: "/ehr/chatroom/createRoom.do",
            async: true,
            dataType: "html", //form 보낼 땐 html 쓴다고 생각하면 됨
            data: {
                "roomId"     : roomIdInput.value, 
                "senderId"    : userId1Input.value,
                "receiverId"    : userId2Input.value
            },
            success: function(response) {
                console.log("success response:" + response);
                const message = JSON.parse(response);
                if( 1 ===message.messageId){ //등록 성공
                    if(confirm('채팅방 등록 성공! 메신저로 넘어가시겠습니까?') === true){
                        //로그인 창으로 화면 이동
                        window.location.href = "/ehr/chatroom/doRetrieve.do";
                    } else{
                        window.location.href = "/ehr/main/main.do";
                    }
                    
                }else{ 
                    alert(message.message);
                }
              
            },
            error: function(response) {
                console.log("error:" + response);
            }
        });
    });

    moveToPreviousButton.addEventListener("click",function(event){
        event.preventDefault();
        console.log("moveToPreviousButton click");

        if(confirm("돌아가시겠습니까?") === false) return;

        window.location.href = "/ehr/chatroom/doRetrieve.do";
    });

});