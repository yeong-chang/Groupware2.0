<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="CP" value="${pageContext.request.contextPath }" />
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set> 

<style>
#header {
    grid-column: span 2;
    background: #5c21a9;
    color: white;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;
    position: relative;
    height: 70px;
}

#header #logo {
    display: flex;
    align-items: center;
}

#header #logo img {
    height: 50px; /* 로고 크기 조정 */
    margin-right: 15px; /* 이미지와 텍스트 간격 */
}

#header a {
    color: white;
    margin: 0 15px;
    text-decoration: none;
}

#login ul {
    list-style-type: none;
    display: flex; /* 수평 정렬 */
    padding: 0;
    margin: 0;
}

#login li {
    margin: 0 15px;
}
</style>

<header id="header">
    <div id="logo">
        <a href="main.jsp">
            <img src="/ehr/resources/assert/images/logo.png" alt="로고 이미지">
        </a>
    </div>
    <nav id="login">
        <ul>
            <li><a href="${CP}/user/doRetrieve.do">회원관리</a></li>
            <li><a href="#">게시판</a></li>
            <li><a href="${CP}/resources/assets/user/reg_template.html">등록-템플릿</a></li>
            <li><a href="${CP}/resources/assets/user/list_template.html">리스트-템플릿</a></li>
            <c:choose>
                <c:when test="${empty sessionScope.user.name}">
                    <li><a href="${CP}/login/login_index.do">로그인</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${CP}/login/logout.do">로그아웃</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>
</header>