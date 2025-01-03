<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="CP" value="${pageContext.request.contextPath}"/>

<nav class = "top-nav-container">
	<div class = "header-container">
		<div class="logo-container">
			<img alt="logo" src="${CP}/resources/assets/images/logo.png">
		</div>
		<div></div>
		<div class="profile-pic">
			<img src="/ehr/resources/assets/images/profile.png">
		</div>
		<div class="profile-info">
			<p>부서: ${sessionScope.user.deptNo}</p>
			<p>직급: ${sessionScope.user.position}</p>
			<p>성명: ${sessionScope.user.name}</p>
		</div>
		<div class="login-container">
		<c:choose>
			<c:when test = "${empty sessionScope.user.name}">
				<li><a href = "${CP}/login/login_index.do">로그인</a></li>
			</c:when>
			<c:otherwise>
				<li id = "logout"><a href = "${CP}/login//logout.do">로그아웃</a></li>
			</c:otherwise>
		</c:choose>
		</div>
		
	
	</div>
	
</nav>

