<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="layout/Header.jsp" %>
<%@ include file="layout/sidebar.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항 상세보기</title>
    <link rel="stylesheet" type="text/css" href="/ehr/resources/assets/css/board/boarddetail.css">
    <link rel="stylesheet" type="text/css" href="/ehr/resources/assets/css/board/simplemde_min.css"> <!-- SimpleMDE CSS -->
    <script src="/ehr/resources/assets/js/board/simplemde_min.js"></script> <!-- SimpleMDE JS -->
    <style>
        .CodeMirror-scroll {
            height: 0 !important;
        }
    </style>
</head>
<body>
<div class="main-content">
    <div class="content">
        <h1 class="title">${board.article_title}</h1>
        <hr>
        <p class="details">${board.article_contents}</p>
        <a href="announcement.do"><button class="back-button">목록으로 돌아가기</button></a>

        <!-- 게시글 수정 버튼이 로그인한 사용자의 ID와 작성자 ID가 같거나 관리자일 때만 보이도록 조건 추가 -->
        <c:if test="${vo.userId == board.article_user_id || vo.userId == 9999}">
            <button onclick="toggleForms()">게시글 수정</button>
        </c:if>

        <!-- 수정 폼과 삭제 폼 (초기에는 숨김) -->
        <div class="form-container" id="formContainer">
            <form action="updateBoard.do" method="post">
                <input type="hidden" name="article_no" value="${board.article_no}">
                <input type="text" name="article_title" value="${board.article_title}" placeholder="제목을 수정하세요">
                <textarea name="article_contents" id="content" placeholder="내용을 수정하세요">${board.article_contents}</textarea>
                <button type="submit">수정</button>
            </form>

            <form action="deleteBoard.do" method="post">
                <input type="hidden" name="article_no" value="${board.article_no}">
                <button type="submit" class="delete">삭제</button>
            </form>
        </div>
    </div>
</div>

<script src="/ehr/resources/assets/js/board/board_detail.js"></script> <!-- 여기에 스크립트 파일 경로 -->

</body>
</html>
