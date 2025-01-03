<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>부서 게시글 작성</title>
    <link rel="stylesheet" type="text/css" href="/ehr/resources/assets/css/board/boardwrite.css">
    <link rel="stylesheet" type="text/css" href="/ehr/resources/assets/css/board/simplemde_min.css"> <!-- SimpleMDE CSS -->
    <script src="/ehr/resources/assets/js/board/simplemde_min.js"></script> <!-- SimpleMDE JS -->
</head>
<body>

    <div class="form-container">
        <h2>부서 게시글 작성</h2>
        <form action="departmentboard.do" method="post">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" placeholder="게시글 제목을 입력하세요" required>

            <label for="content">내용</label>
            <textarea id="content" name="content" rows="10" placeholder="게시글 내용을 입력하세요" required></textarea>

            <label for="author">작성자 ID</label>
            <input type="text" id="author" name="author"value = "${vo.userId }" readonly = "readonly" required>

            <label for="department">부서</label>
            <select id="department" name="department" required>
                <option value="">부서를 선택하세요.</option>
                <option value=30>개발</option>
                <option value=40>인사</option>
                <option value=50>마케팅</option>
            </select>

            <button type="submit">작성 완료</button>
        </form>
        <a href="announcement.do"><button class="back-button">목록으로 돌아가기</button></a>
    </div>

    <script src="/ehr/resources/assets/js/board/board_write.js"></script> <!-- 여기에 스크립트 파일 경로 -->
</body>
</html>
