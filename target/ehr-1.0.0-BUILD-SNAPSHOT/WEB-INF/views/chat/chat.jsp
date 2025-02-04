<%@page import="java.util.Date"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pcwk.ehr.user.domain.UserVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/ehr/resources/assets/css/main/main.css?date=<%=new Date()%>">
    <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
    <script src="/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
    <script src="/ehr/resources/assets/js/chat/chat.js?date=<%=new Date()%>"></script> <!-- 서버 전송 -->

    <style>
        #chat-container {
            border: 1px solid #ccc;
            padding: 10px;
            flex-grow: 1;
            overflow-y: auto;
            background-color: #f9f9f9;
            margin-bottom: 20px;
            max-height: 70vh;
        }

        .chat-message {
            background-color: #e9e9e9;
            padding: 8px;
            margin: 10px 0;
            border-radius: 5px;
        }

        .message-content {
            font-size: 16px;
            color: #333;
            margin: 5px 0;
        }

        .message-roomId {
            font-size: 14px;
            color: #666;
        }

        /* main-container 전체 스타일 */
        .main-container {
            display: flex !important;
            flex-direction: column !important;
            height: 100vh !important; /* 화면 전체 크기 차지 */
            padding: 20px !important; /* 여백 추가 */
            background-color: #f4f4f4 !important; /* 배경 색상 설정 */
        }

        /* 채팅 컨테이너 스타일 */
        #chat-container {
            border: 1px solid #ccc !important;
            padding: 10px !important;
            flex-grow: 1 !important; /* 남은 공간 차지 */
            overflow-y: auto !important; /* 내용 많으면 스크롤 생성 */
            margin-bottom: 20px !important;
            background-color: #fff !important; /* 배경 색상 */
            max-height: 80vh !important; /* 화면의 80%만큼 높이 설정 */
        }

        /* 메시지 입력창 및 전송 버튼 스타일 */
        input[type="text"] {
            width: 300px !important;
            padding: 10px !important;
            margin-right: 10px !important;
            border: 1px solid #ccc !important;
            border-radius: 5px !important;
        }

        input[type="button"] {
            padding: 10px 15px !important;
            background-color: #4CAF50 !important; /* 전송 버튼 색상 */
            color: white !important;
            border: none !important;
            border-radius: 5px !important;
            cursor: pointer !important;
        }

        input[type="button"]:hover {
            background-color: #45a049 !important; /* 전송 버튼 hover 효과 */
        }

        /* 메시지 테이블 스타일 */
        table {
            width: 100% !important;
            border-collapse: collapse !important;
        }

        th, td {
            padding: 10px !important;
            text-align: left !important;
            border-bottom: 1px solid #ddd !important;
        }

        /* 채팅 메시지 스타일 */
        p {
            padding: 5px !important;
            background-color: #e9e9e9 !important; /* 메시지 배경 */
            border-radius: 5px !important;
            margin: 5px 0 !important;
        }

        /* 돌아가기 버튼 스타일 */
        #moveToPrevious {
            background-color: #f44336 !important; /* 빨간색 */
            color: white !important;
        }

        #moveToPrevious:hover {
            background-color: #da190b !important; /* 돌아가기 버튼 hover 효과 */
        }
    </style>


    <title>메신저</title>
</head>

<body>
<!-- 헤더 및 사이드바 포함 -->
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<jsp:include page="/WEB-INF/views/include/aside.jsp"></jsp:include>

<div class="main-container">
    <!-- 채팅 제목 -->
    <h2>채팅</h2>

    <!-- 채팅 메시지 표시 영역 -->
    <div id="chat-container">
        <!-- 채팅 메시지 출력 (JSP 변수로부터 메시지 가져오기) -->
        <c:forEach var="chat" items="${getChat}">
            <%--<div class="chat-message">--%>
                <tbody>
                    <tr>
                        <td class="message-content"> ${chat.senderId}: ${chat.content}</td>
                        <td class="message-roomId">Room ID: ${chat.roomId}</td>
                    </tr>
                </tbody>
            <%--</div>--%>
        </c:forEach>
    </div>



    <div>
        <form id="chatForm" action="#" method="post">
            <table>
                <tr>
                    <th><label for="content">메시지</label></th>
                    <td><input type="text" name="content" id="content" placeholder="보내실 메시지를 입력하세요." required></td>
                    <td><input type="button" id="sendChat" value="전송"></td>
                    <td><input type="button" id="moveToPrevious" value="돌아가기"></td>
                </tr>
            </table>
        </form>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        let stompClient = null;

        // 웹소켓 연결
        function connect() {
            const socket = new SockJS('/ehr/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);

                // 구독 : /topic/messages.do
                stompClient.subscribe('/topic/messages.do', function(message){
                    console.log(message);
                    // 받은 메시지를 화면에 표시
                    showMessage(JSON.parse(message.body));
                });
            });
        }
        function showMessage(message) {
            const messageDiv = document.getElementById('message');
            const messageElement = document.createElement('div');
            messageElement.textContent = message.sender + ": " + message.content;
            messageDiv.appendChild(messageElement);
            messageDiv.scrollTop = messageDiv.scrollHeight;
        }
        // 메시지 전송
        function sendMessage() {
            const messageContent = document.getElementById('messageInput').value.trim();

            if (messageContent && stompClient) {
                // 메시지 전송
                stompClient.send('/ehr/sendMessage.do', {}, JSON.stringify({
                    sender: '이름',
                    content: messageContent
                }));
                document.getElementById('messageInput').value = '';
            }
        }
        window.onload = function() {
            connect();
        };
    </script>

    <!-- 채팅 내용 표시 영역 (서버로부터 수신한 메시지를 이곳에 표시) -->
    <div id="chatWindow">
        <!-- 기존 채팅 내용은 서버에서 동적으로 렌더링 후 표시됩니다 -->
    </div>

</div>
</body>

</html>
