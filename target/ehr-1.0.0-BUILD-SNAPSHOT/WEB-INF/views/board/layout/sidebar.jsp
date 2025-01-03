<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <style>
        :root {
            --white: #fff;
            --blue50: #e8f3ff;
            --blue100: #c9e2ff;
            --blue200: #90c2ff;
            --blue300: #64a8ff;
            --blue400: #4593fc;
            --blue500: #3182f6;
            --blue600: #2272eb;
            --blue700: #1b64da;
            --blue800: #1957c2;
            --blue900: #194aa6;
        }
    .container {
      width: 100%;
        display: flex;
    }
    .container-fluid {
        flex-grow: 1;
        /*background-color: var(--blue50);*/
        height: 60px;
        margin-top: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow-y: auto;
        text-decoration: none;
        color: #333;
        transition: background-color 0.3s, color 0.3s;
    }
    .container-fluid:hover {
        background-color: #000000;
        color: #00ff00;
    }
    .sidebar {
        position: fixed;
        width: 10% !important;
        box-sizing: border-box;
        background: linear-gradient(to bottom, var(--blue100), var(--blue200), var(--blue400));
        height: 100vh !important;
        padding: 20px !important;
        left: 0;
    }
    .sidebar h2 {
        margin-top: 0;
    }
    .sidebar ul {
        list-style-type: none;
        padding: 0;
    }
    .main-content {
        flex-grow: 1;
        padding: 20px;
    }
</style>
    <title>게시판</title>
</head>
<body>
    <div class="container">
        <nav class="sidebar">
            <h2>메뉴</h2>
            <ul>
                <li><a href="/ehr/board/announcement.do" class="container-fluid">게시판</a></li>
<%--                <li><a href="/board/free.do" class="container-fluid">자유 게시판</a></li>--%>
<%--                <li><a href="/board/development.do" class="container-fluid">개발 부서 게시판</a></li>--%>
<%--                <li><a href="/board/hr.do" class="container-fluid">인사처 게시판</a></li>--%>
<%--                <li><a href="/board/marketing.do" class="container-fluid">마케팅 부서 게시판</a></li>--%>
                <li><a href="/ehr/calendar/show.do" class="container-fluid">캘린더</a></li>
                <li><a href="/ehr/approval/doRetrieve.do" class="container-fluid">전자결재</a></li>
                <li><a href="/ehr/user/myPage.do" class="container-fluid">마이페이지</a></li>
                <li><a href="/ehr/login//logout.do" class="container-fluid">로그아웃</a></li>
            </ul>
        </nav>
</body>
</html>
