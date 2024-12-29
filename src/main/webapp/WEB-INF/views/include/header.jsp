<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="CP" value="${pageContext.request.contextPath}"/>

<!-- 회원관리 게시판 목록 - 템플릿 리스트-템플릿 로그인 -->
<style>
</style>
<header id="header">
	<h2>PCWK</h2>
	<nav>
		<ul>
	    <c:if test="${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.position == 'CEO'}">
		  <li><a href="${CP}/user/doRetrieve.do">회원관리</a></li>
		</c:if>
		
        <c:if test="${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.position == 'CEO'}">
          <li><a href = "${CP}/user/user_reg_index.do">회원등록</a></li>
        </c:if>
     
			<li><a href = "">게시판</a></li>
			<li><a href = "">일정관리</a></li>
			<li><a href = "${CP}/approval/doRetrieve.do">전자결재</a></li>  
			<li><a href = "${CP}/contact/doRetrieve.do">주소록</a></li>
		
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
</header>
