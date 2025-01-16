<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel ="stylesheet" href = "/ehr/resources/assets/css/main/main.css?date=<%=new Date()%>">
<script src = "/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
<script src = "/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
<script src = "/ehr/resources/assets/js/chat/chat.js?date=<%=new Date()%>"></script> <!--  서버 전송 -->
<script>
   /* const sessionUserId = '${sessionScope.user.userId}'; // JSP에서 sessionScope 값 전달 to javascript    */
</script>
<title>메신저</title>
</head>
<style>
    #chat-container {
           border: 1px solid #ccc;
           padding: 10px;
           height: 300px;
           overflow-y: auto;
           margin-bottom: 20px;
       }
</style>
<body>
<jsp:include page = "/WEB-INF/views/include/header.jsp"></jsp:include>
<jsp:include page = "/WEB-INF/views/include/aside.jsp"></jsp:include>
<div class="main-container">
<h2>채팅</h2>
	<div id = "chat-container">
	    
    </div>
    <div>
        <form action = "#" method = "post">
           <div>
            <table>
                <tr>
                    <th>메시지</th>
                    <th><input type = "text" name = "content" id="content" placeholder="보내실 메시지를 입력하세요."></th>
                    <th><input type = button id = "sendChat" value = "전송"></th>
                    <th><input type = button id = "moveToPrevious" value = "돌아가기"></th>
                </tr>
            </table>
        </div>
        </form>
    </div>
    </div>
</body>
</html>