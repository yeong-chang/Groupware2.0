<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="CP" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel ="stylesheet" href = "/ehr/resources/assets/css/main/main.css?date=<%=new Date()%>">
<title>Main</title>
<script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script><!-- jquery    -->
<script src="/ehr/resources/assets/js/cmn/common.js"></script><!-- 공통 util -->
</head>
<body>
  <div class = "outer-container">
	<div class="nav-container">		
		<jsp:include page = "/WEB-INF/views/include/header.jsp"></jsp:include> <!-- header-->
		<jsp:include page = "/WEB-INF/views/include/aside.jsp"></jsp:include> <!-- aside-->
	</div>
	  <div class = "main-content-container"> <!-- 본문에 들어갈 내용은 여기에 붙이시면 됩니다. -->
	  
	  </div> <!-- //main-content-container --> 
  </div> <!-- //main-container -->	
</body>
</html>