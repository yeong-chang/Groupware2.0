<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원등록</title>
<link rel ="stylesheet" href = "/ehr/resources/assets/css/main/main.css?date=<%=new Date()%>">
<script src = "/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
<script src = "/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
<script src = "/ehr/resources/assets/js/admin/admin_reg.js?date=<%=new Date()%>"></script> <!--  서버 전송 -->
</head>
<body>
<div id="container">
<jsp:include page = "/WEB-INF/views/include/header.jsp"></jsp:include>
<jsp:include page = "/WEB-INF/views/include/aside.jsp"></jsp:include>
<main id="contents" >
<div class = "main-container">
    <h2>회원 등록</h2>
    <div>
        <input type = "button" id = "doSave" value = "등록">
        <input type = "button" id = "moveToPrevious" value = "돌아가기">
    </div>
    <!-- ------------------- -->
    <div>
        <form action = "#" method = "post" class = "search-form" name ="userForm" id = "userForm">
            <div>
                <label for = "userId">사용자 ID</label>
                <input type = "text" maxlength = "4" name = "userId" id = "userId">
            </div>      
            <div>
                <label>부서</label>
                <select name = "deptNo" id = "deptNo">
                    <option value = "1000">SW 개발 (SW development)</option>
                    <option value = "2000">마케팅     (Marketing)</option>
                    <option value = "3000">인사/총무(Human Resource) </option>
                </select>
            </div>
             <div>
                <label for ="supUserId">상급자ID</label>
                 <input type = "text" maxlength = "4" name = "supUserId" id = "supUserId">
            </div>
            <div>
                <label for = "name">이름</label>
                <input type = "text" maxlength = "30" name = "name" id ="name">
            </div>
            
            <div>
                <label for = "password">비밀번호</label>
                <input type ="password" maxlength = "50" name = "password" id = "password">
            </div>
            
            <div>
                <label for = "position">직급</label>
                <select name = "position" id = "position" >
                    <option value = "Assistant Manager">대리</option>
                    <option value = "Department Head">부서장</option>
                    <option value = "CEO">CEO</option>
                </select>
            </div>
            
            <div>
                <label for = "birthday">생년월일</label>
                <input type = "date" id ="birthday" name = "birthday" max = "2004-01-01" min = "1950-01-01" value = "2000-01-01">
            </div>      
            
            <div>
                <label for = "phoneNo">연락처</label>
                <input type = "text" maxlength = "11" name = "phoneNo" id = "phoneNo">
            </div>
            
            <div>
                <label for = "status">상태</label>
                 <input type="number" name="status" id="status" min="0" max="20" step="10" value="10">
            </div>
            
        </form>
    </div>
    </div>
    </main>
    </div>
</body>
</html>