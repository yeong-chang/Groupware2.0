/*
document.addEventListener("DOMContentLoaded",function() {
  console.log("DOMContentLoaded");

  const contentInput = document.querySelector("#content");
  const chatContainer = document.getElementById("chat-container");
  const roomId = new URLSearchParams(window.location.search).get('roomId');
  const receiverId = new URLSearchParams(window.location.search).get('receiverId');

  function loadMessages(roomId) {
    $.get("/ehr/chat/getMessages.do", { roomId: roomId })
        .done(function (data) {
            if (!data) {
                console.warn("서버로부터 빈 응답을 받았습니다.");
                return;
            }

            try {
                const messages = JSON.parse(data);
                chatContainer.innerHTML = ""; // 기존 메시지 초기화
                messages.forEach(msg => {
                    const messageElement = document.createElement("div");
                    const senderLabel = msg.senderId === sessionUserId ? "나" : msg.senderId;
                    messageElement.innerHTML = `<b>${senderLabel}:</b> ${msg.content}`;
                    chatContainer.appendChild(messageElement);
                });
                chatContainer.scrollTop = chatContainer.scrollHeight; // 스크롤 맨 아래로 이동
            } catch (error) {
                console.error("JSON 파싱 오류:", error);
            }
        })
        .fail(function () {
            alert("채팅 메시지를 불러오는 데 실패했습니다.");
        });
}

  const moveToPreviousBtn = document.querySelector("#moveToPrevious");
  moveToPreviousBtn.addEventListener("click", function(event){
    event.preventDefault();
    console.log("moveToPreviousBtn click");

    if(confirm('돌아가시겠습니까?')===false) return;

    window.location.href = "/ehr/chatroom/doRetrieve.do";
  })

  const sendChatBtn = document.querySelector("#sendChat");
  sendChatBtn.addEventListener("click",function(event){
    event.preventDefault();
    console.log("sendChatBtn click");
  
    const content = contentInput.value;
    if(!content){
      alert("메시지를 입력하세요.");
      return;
    }
      
    $.post("/ehr/chat/sendChat.do",{
      content: content,
      roomId: roomId,
      receiverId: receiverId
    })
      .done(function(response){
        const result = JSON.parse(response);
        const messageElement = document.createElement("div");
        messageElement.innerHTML = `<b>나: </b> ${content}`;
        chatContainer.appendChild(messageElement);
        contentInput.value ="";
        chatContainer.scrollTop = chatContainer.scrollHeight;
      })

    // const messageElement = document.createElement('div');
    // messageElement.textContent = `${sessionId}: ${content}`;
    // chatContainer.appendChild(messageElement);
    //   $.ajax({
    //     type: "POST",
    //     url: "/ehr/chat/sendChat.do",
    //     async: true,
    //     dataType: "html", //form 보낼 땐 html 쓴다고 생각하면 됨
    //     data: {
    //       "content" : content
    //     },
    //     success: function(response) {
    //         console.log("success response:" + response);
    //         const message = JSON.parse(response);        
            
    //         contentInput.value = '';
    //     },
    //     error: function(response) {
    //         console.log("error:" + response);
    //     }
    // });

    })
    loadMessages(roomId);
  });
*/
