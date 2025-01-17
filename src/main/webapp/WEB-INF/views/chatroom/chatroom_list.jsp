<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="com.pcwk.ehr.cmn.StringUtil"%>
<%@page import="com.pcwk.ehr.cmn.SearchVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="CP" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${CP}/resources/assets/css/user/list.css?date=<%=new Date()%>">
    <link rel ="stylesheet" href = "${CP}/resources/assets/css/main/main.css?date=<%=new Date()%>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src = "${CP}/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
    <script src = "${CP}/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
    <script src = "${CP}/resources/assets/js/chatroom/chatroom_list.js?date=<%=new Date()%>"></script> <!--  서버 전송 -->
    <script>

    </script>
    <title>Insert title here</title>
</head>
<body>
<div id="container">
    <!-- header-->
    <jsp:include page = "/WEB-INF/views/include/header.jsp"></jsp:include>
    <!--// header-------------------------------------------------->

    <!-- aside-->
    <jsp:include page = "/WEB-INF/views/include/aside.jsp"></jsp:include>
    <!--// aside--------------------------------------------------->

    <div class = "main-container">
        <h2>채팅방 목록</h2>
        <hr class="title-underline" />
        <div>
            <input type="button" value="조회" id="doRetrieveBtn">
            <input type="button" value="등록" id="moveToRegBtn">
        </div>
        <form action="deletechatroom.do" method="post">
            <table border="1" id="listTable" class="table">
                <thead>
                <tr>
                    <th class="table-head">선택</th>
                    <th class="table-head">방 번호</th>
                    <th class="table-head">ID 1</th>
                    <th class="table-head">ID 2</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty chatRoomList}">
                    <c:forEach var="chatRoom" items="${chatRoomList}">
                        <tr title="더블클릭하면 상세 정보를 볼 수 있습니다.">
                            <td class="table-cell text-center">
                                <input type="radio" name="roomId" value="${chatRoom.roomId}">
                            </td>
                            <td class="table-cell text-center">${chatRoom.roomId}</td>
                            <td class="table-cell text-center">${chatRoom.senderId}</td>
                            <td class="table-cell text-center">${chatRoom.receiverId}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>

            <div>
                <input type="submit" value="삭제">
            </div>
        </form>

    </div>
</div>
</body>
</html>
