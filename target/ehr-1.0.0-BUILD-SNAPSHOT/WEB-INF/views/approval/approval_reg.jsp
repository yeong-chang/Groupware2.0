<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	// 오늘 날짜를 생성
java.time.LocalDate today = java.time.LocalDate.now();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/ehr/resources/assets/css/main/main.css?date=<%=new Date()%>">
<script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
<script src="/ehr/resources/assets/js/cmn/common.js"></script>
<script src="/ehr/resources/assets/js/approval/approval_reg.js?date=<%=new Date()%>"></script>
<title>결재건 등록</title>
<style>
.main-container h2 {
	text-align: center;
	font-family: Arial, sans-serif;
	color: #333;
}

.main-container div {
	margin-bottom: 15px;
}

.main-container label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
	color: #555;
}

.main-container input[type="text"], .main-container input[type="date"],
	.main-container input[type="password"], .main-container select {
	width: calc(100% - 20px);
	padding: 8px 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

.main-container input[type="button"] {
	padding: 10px 20px;
	margin-right: 10px;
	border: none;
	border-radius: 4px;
	color: #fff;
	background-color: #007BFF;
	cursor: pointer;
	font-size: 14px;
}

.main-container input[type="button"]:hover {
	background-color: #0056b3;
}

.main-container form {
	display: flex;
	flex-direction: column;
	gap: 15px;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/include/aside.jsp"></jsp:include>
	<div class="main-container">
		<h2>결재건 등록</h2>
		<div>
			<input type="button" id="doSave" value="등록"> <input
				type="button" id="moveToPrevious" value="목록">
		</div>
		<form action="#" method="post">

			<div>
				<label for="approval_doc_no"></label> <input type="text"
					maxlength="8" name="approval_doc_no" id="approval_doc_no" value="0"
					style="display: none">
			</div>

			<div>
				<label for="approval_user_id">상신자</label> <input type="text"
					value="${sessionScope.user.userId}" name="approval_user_id"
					id="approval_user_id" disabled="disabled">
			</div>

			<div>
				<label for="approval_doc_reg_date">등록일</label> <input type="date"
					name="approval_doc_reg_date" id="approval_doc_reg_date"
					value="<%=today%>" disabled="disabled">
			</div>

			<div>
				<label for="approval_doc_title">제목</label> <input type="text"
					maxlength="30" name="approval_doc_title" id="approval_doc_title">
			</div>

			<div>
				<label for="approval_doc_approved_date"></label> <input type="date"
					name="approval_doc_approved_date" id="approval_doc_approved_date"
					style="display: none">
			</div>

			<div>
				<label for="approval_doc_closing_date">마감일</label> <input
					type="date" name="approval_doc_closing_date"
					id="approval_doc_closing_date">
			</div>

			<div>
				<label for="approval_status"></label> <input type="text"
					name="approval_status" id="approval_status" style="display: none">
			</div>

			<div>
				<label for=approval_contents>내용</label> <input type="text"
					name="approval_contents" id="approval_contents">
			</div>

		</form>
	</div>
</body>
</html>