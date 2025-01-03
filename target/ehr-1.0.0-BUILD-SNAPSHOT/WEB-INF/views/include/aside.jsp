<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="CP" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="/ehr/resources/assets/css/main/main.css">
<nav class = "aside-nav-container">
	<ul>
	   <c:if test="${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.position == 'CEO'}">
	  <li><a href="${CP}/user/doRetrieve.do">회원관리</a></li>
	</c:if>
	
	      <c:if test="${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.position == 'CEO'}">
	        <li><a href = "${CP}/user/user_reg_index.do">회원등록</a></li>
	      </c:if>
	   
		<li id="lili"><a href = "${CP}/board/announcement.do">게시판</a></li>
		<li><a href = "${CP}/calendar/show.do">일정관리</a></li>
		<li><a href = "${CP}/approval/doRetrieve.do">전자결재</a></li>  
		<li><a href = "${CP}/contact/doRetrieve.do">주소록</a></li>
		<li><a href = "${CP}/chatroom/doRetrieve.do">메신저</a></li>
	
	<c:if test="${sessionScope.user.position != 'ADMINISTRATOR'}">
	        <li><a href = "${CP}/user/myPage.do">마이페이지</a></li>
	      </c:if>
	
	
	<c:choose>
		<c:when test = "${empty sessionScope.user.name}">
			<li><a href = "${CP}/login/login_index.do">로그인</a></li>
		</c:when>
		
		<c:otherwise>
			<li id = "logout"><a href = "${CP}/login//logout.do">로그아웃</a></li>
			
		</c:otherwise>
	</c:choose>
	</ul>
</nav>