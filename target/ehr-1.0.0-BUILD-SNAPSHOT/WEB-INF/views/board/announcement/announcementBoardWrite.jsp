<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항 작성</title>
    <link rel="stylesheet" type="text/css" href="/ehr/resources/assets/css/board/boardwrite.css">
    <link rel="stylesheet" type="text/css" href="/ehr/resources/assets/css/board/simplemde_min.css"> <!-- SimpleMDE CSS -->

    <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
    <script src="/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
    <script src="/ehr/resources/assets/js/user/user_myPage.js"></script> <!-- 서버 전송 -->

    <script src="/ehr/resources/assets/js/board/simplemde_min.js"></script> <!-- SimpleMDE JS -->
</head>
<body>

<div class="form-container">
    <h2>공지사항 게시글 작성</h2>
    <form action="announcementboard.do" method="post">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" placeholder="게시글 제목을 입력하세요" required>

        <label for="content">내용</label>
        <!-- 기존의 textarea를 SimpleMDE 마크다운 에디터로 변환 -->
        <textarea id="content" name="content" rows="10" placeholder="게시글 내용을 입력하세요" ></textarea>

        <label for="author">등록자</label>
        <input type="text" id="author" name="author" value="${vo.userId}" readonly="readonly" required>

        <button type="submit">작성 완료</button>
    </form>
    <a href="announcement.do"><button class="back-button">목록으로 돌아가기</button></a>
</div>

<script src="/ehr/resources/assets/js/board/board_write.js"></script> <!-- 여기에 스크립트 파일 경로 -->

</body>
</html>
