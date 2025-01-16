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
        const sessionUserId = '${sessionScope.user.userId}'; // JSP에서 sessionScope 값 전달 to javascript
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
        <table border="1" id="listTable" class="table">
            <thead>
            <tr>
                <th class="table-head">방 번호</th>
                <th class="table-head">ID 1</th>
                <th class="table-head">ID 2</th>
            </tr>
            </thead>
            <tbody>
            <!-- 데이터가 있을 경우 -->
            <c:if test="${not empty chatRoomList}">
                <c:forEach var="chatRoomList" items="${chatRoomList}">
                    <tr title="더블클릭하면 상세 정보를 볼 수 있습니다.">
                        <td class="table-cell text-center">${chatRoomList.roomId}</td>
                        <td class="table-cell text-center">${chatRoomList.senderId}</td>
                        <td class="table-cell text-center">${chatRoomList.receiverId}</td>
                    </tr>
                </c:forEach>
            </c:if>

            <%--<!-- 데이터가 없을 경우 -->
            <c:if test="${empty list}">
                <tr>
                    <td colspan="99" class="table-cell text-center">조회된 데이터가 없습니다.</td>
                </tr>
            </c:if>--%>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
