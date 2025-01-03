<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ko"> <!-- 한국어로 변경 -->
<head>
  <title>게시판 헤더</title>
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
    /* 헤더 스타일 */
    header {
      background: #4B73E1;
      width: 90%;
      padding: 10px;
      margin-top: 7em;
      text-align: center;
      display: flex;
      height: 9%;
      justify-content: center;
      position: fixed;
      left: 10%;

    }
    header ul {
      list-style-type: none;
      padding: 0;
      margin: 0;
      display: flex;
      justify-content: center;
    }
    header ul li {
      margin: 0 20px;
    }
    header ul li a {
      text-decoration: none;
      color: white;
      font-size: 18px;
      transition: color 0.3s;
    }
    header ul li a:hover {
      color: #00ff00;
    }
    .header-fluid {
      flex-grow: 1;
      /*background-color: var(--blue50);*/
      height: 50px;
      display: flex;
      justify-content: center;
      align-items: center;
      overflow-y: auto;
      text-decoration: none;
      color: #333;
      transition: background-color 0.3s, color 0.3s;
    }
    .header-fluid:hover {
      background-color: #000000;
      color: #00ff00;
    }
  </style>
</head>
<body>
<header role="banner"> <!-- 접근성 향상 -->
  <ul>
    <li><a href="announcement.do" class="header-fluid">공지사항 게시판</a></li>
    <li><a href="free.do" class="header-fluid">자유 게시판</a></li>
    <li><a href="development.do" class="header-fluid">개발 부서 게시판</a></li>
    <li><a href="hr.do" class="header-fluid">인사처 게시판</a></li>
    <li><a href="marketing.do" class="header-fluid">마케팅 부서 게시판</a></li>
  </ul>
</header>
</body>
</html>
