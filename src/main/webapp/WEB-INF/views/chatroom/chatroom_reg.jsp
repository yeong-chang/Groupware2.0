<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>채팅방 생성</title>
    <link rel="stylesheet" href="/ehr/resources/assets/css/main/main.css?date=<%=new Date()%>">
    <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
    <script src="/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
    <script src="/ehr/resources/assets/js/chatroom/chatroom_reg1.js?date=<%=new Date()%>"></script> <!-- 서버 전송 -->

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fa;
            margin: 0;
            padding: 0;
        }

        header, aside {
            background-color: #2c3e50;
            color: white;
            padding: 10px;
        }

        h1 {
            color: #34495e;
            margin-top: 20px;
        }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .form-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 100%;
            max-width: 400px;
            box-sizing: border-box;
            margin-top: 50px;
        }

        .form-container label {
            font-weight: bold;
            color: #34495e;
            display: block;
            margin-bottom: 8px;
        }

        .form-container input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-size: 16px;
            box-sizing: border-box;
        }

        .form-container input[type="text"]:focus {
            outline: none;
            border-color: #3498db;
        }

        .form-container input[type="submit"],
        .form-container input[type="button"] {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            box-sizing: border-box;
        }

        .form-container input[type="submit"]:hover,
        .form-container input[type="button"]:hover {
            background-color: #2980b9;
        }

        .form-container .buttons {
            display: flex;
            justify-content: space-between;
        }

        .form-container .buttons input {
            width: 48%;
        }

        .form-container .buttons input:first-child {
            background-color: #2ecc71;
        }

        .form-container .buttons input:first-child:hover {
            background-color: #27ae60;
        }

        .form-container .buttons input:last-child {
            background-color: #e74c3c;
        }

        .form-container .buttons input:last-child:hover {
            background-color: #c0392b;
        }
    </style>

</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<jsp:include page="/WEB-INF/views/include/aside.jsp"></jsp:include>

<div class="container">
    <h1>채팅방 생성</h1>
    <div class="form-container">
        <form action="createChatRoom.do" method="post">
            <div>
                <label for="userId1">유저 1</label>
                <select name="userId1" id="userId1">
                    <option value="">유저를 선택하세요</option>
                    <c:forEach var="user" items="${userList}">
                        <option value="${user.userId}">${user.name}</option>
                    </c:forEach>
                </select>
            </div>


            <div class="buttons">
                <input type="submit" id="doCreate" value="등록">
                <input type="button" id="moveToPrevious" value="돌아가기" onclick="window.history.back();">
            </div>
        </form>
    </div>
</div>

</body>
</html>
