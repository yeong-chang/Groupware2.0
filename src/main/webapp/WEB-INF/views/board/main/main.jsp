<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.body {
  margin: 0;
  font-family: Arial, sans-serif;
  box-sizing: border-box;
  background-color: #f6efff;
}

.container {
  width: 100%;
  height: 100vh;
  display: grid;
  grid-template-columns: 300px 1fr;
  grid-template-rows: 100px 1fr;
  gap: 10px;
  margin: 0 auto;
}

}
@media (max-width: 768px) {
  .container {
    grid-template-columns: 1fr;
    width: 80%;
  }

  </style>
<title>Main</title>
<link rel="shortcut icon"	href="/ehr/resources/assert/main/favicon.ico" type="image/x-icon">
<link rel="stylesheet"      href="/ehr/resources/assert/css/main.css">
<title>Main</title>
<script src="/ehr/resources/assert/js/jquery_3_7_1.js"></script>
<script src="/resources/assert/js/cmn/common.js"></script>
</head>

<body>
	<div class="container">
		<!-- header-->
        <jsp:include page="header.jsp"></jsp:include>
		<!--// header-------------------------------------------------->

		<!-- aside-->
        <jsp:include page="aside.jsp"></jsp:include>
		<!--// aside--------------------------------------------------->

		<!-- main-->
		<main class="contents">

		</main>
		<!--// main---------------------------------------------------->

		<!-- footer-->
        <!-- footer<jsp:include page="footer.jsp"></jsp:include>-->
		<!--// footer-------------------------------------------------->
	</div>
</body>
</html>