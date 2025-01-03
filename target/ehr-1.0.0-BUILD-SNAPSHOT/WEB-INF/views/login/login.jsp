<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="CP" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="shortcut icon" href="/ehr/resources/assets/images/favicon.ico" type="image/x-icon">
<style>
	@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Nanum+Pen+Script&display=swap');
	*{
		margin: 0;
		padding: 0;
		box-sizing: border-box;
	}
	body {
	  font-family: "Nanum Gothic", sans-serif;
	  margin: 0;
	  padding: 0;
	  display: flex;
	  flex-direction: column;
	  justify-content: center;
	  align-items: center;
	  min-height: 100vh;
	  background-color: #f4f4f9;
	  height: 100vh;
	}
    .loginMessage{
        color: rgb(240, 0, 0);
	    text-align: center;
	    margin-bottom: 15px;
    }
	.login-container{
		background-color: #ffffff;
		padding: 20px;
		border-radius: 10px;
		box-shadow: 0 4px 6px rgba(0,0,0,0.1);
	}
	
	.login-container h2{
		text-align: center;
		margin-bottom: 20px;
	}
	
	.form-group{
		margin-bottom: 15px;
	}
	
	.form-group label{
		display: block;
		font-size: 14px;
		margin-bottom: 5px;
		color: #333333;
	}
	
	.form-group input{
		border: 1px solid #ddd;
		border-radius: 5px;
		padding: 10px;
		width: 90%;
		font-size: 14px;
	}
	
	.form-actions{
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-top: 20px;
	}
	
	.form-actions button{
		padding: 10px 20px;
		font-size: 14px;
		border: none;
		border-radius: 5px;
		cursor: pointer;
		color: #fff;
		transition: background-color 0.3s;
	}
	
	.form-actions .login-btn{
		background-color: #007bff;
	}
	
	.form-actions .login-btn:hover{
		background-color: #0056b3;
	}
	
	.form-actions .signup-btn{
		background-color: #339933;
	}
	
	.form-actions .signup-btn:hover{
		background-color: #ffcc66;
	}
</style>
<script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script><!-- jquery    -->
<script src="/ehr/resources/assets/js/cmn/common.js"></script><!-- 공통 util -->
<script src="/ehr/resources/assets/js/login/login.js?date=<%=new Date()%>"></script><!-- 서버 전송 -->
<title>로그인</title>
</head>
<body>
    <c:if test="${not empty loginMessage}">
      <div class="loginMessage">
        ${loginMessage}
      </div>
    </c:if>
	<div class="login-container">
		<h2>로그인</h2>
			<form action = "#" method = "post">
				<div class = "form-group">
					<label for = "userId">아이디:</label>
					<input type ="text" name = "userId" id = "userId" placeholder = "아이디를 입력하세요." maxlength = "30">
				</div>
				
				<div class = "form-group">
					<label for = "password">비밀번호:</label>
					<input type ="password" name = "password" id = "password" placeholder = "비밀번호를 입력하세요." maxlength = "30">
				</div>
			</form>
				<div class = "form-actions">
					<button type = "submit" id ="loginBtn" class = "login-btn">로그인</button>
					<button type = "button" id ="memberRegBtn" class ="signup-btn">회원가입</button>
				</div>
	</div>
</body>
</html>