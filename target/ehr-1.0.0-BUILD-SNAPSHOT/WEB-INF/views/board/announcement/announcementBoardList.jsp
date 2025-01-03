<%@ page import="com.pcwk.ehr.cmn.StringUtil" %>
<%@ page import="com.pcwk.ehr.cmn.SearchVO" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../layout/Header.jsp" %>
<%@ include file="../../include/aside.jsp" %>
<%@ include file="../../include/header.jsp" %>
<%--<c:set var="CP" value="${pageContext.request.contextPath}"/>--%>
<%
    /**
     *
     * @param maxNum : 총 글수
     * @param currentPageNo: 현재 페이지번호
     * @param rowPerPage : 페이지 사이즈 (10,20,...100)
     * @param bottomCount: 10/5
     * @param url : 서버 호출 URL
     * @param scriptName : 자바스크립트 함수명
     * @return "html 텍스트"
     */
    int bottomCount = 10;
    int pageSize    = 0;
    int pageNo      = 0;

    int maxNum      = Integer.parseInt(request.getAttribute("totalCnt").toString()) ;//총 글수

    //out.print("****:"+maxNum);
    SearchVO  paramVO     = (SearchVO)request.getAttribute("search");
    pageSize  = paramVO.getPageSize();
    pageNo    = paramVO.getPageNo();

    //out.print("pageSize****:"+pageSize);
    //out.print("pageNo****:"+pageNo);

    String cp = request.getContextPath();
    String pageHtml =
            StringUtil.renderingPager(maxNum,
                    pageNo,
                    pageSize,
                    bottomCount,
                    cp+"/board/announcement.do", "pageDoRetrieve");

%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항 게시판</title>
    <link rel="stylesheet" type="text/css" href="/ehr/resources/assets/css/board/board.css">
 <!--    <style>
        body{
            align-items: normal !important;
            background-color: white !important;
        }
        .aside-nav-container {
            display: flex;
            flex-direction: unset !important;
            justify-content: center !important;
        }
    </style> -->

</head>
<body>

    <!-- 메인 콘텐츠 영역 -->
    <div class="main-content">
        <h1 class="board-title">공지사항 게시판</h1>
        <hr class="title-underline" />
        <form action = "#" class="search-form" name = "userForm" id = "userForm" method = "get" enctype="application/x-www-form-urlencoded">
            <input type ="hidden" name = "pageNo" id = "pageNo"> <!-- 히든할 것들은 form 바로 밑에 주는 게 암묵적 약속 -->
            <div class="search-group">
                <label for = "searchDiv">구분</label>
                <select name = "searchDiv" id = "searchDiv">
                    <option value = "">전체</option>
                    <option value = "10" <c:if test = "${10 == search.searchDiv}">selected</c:if> >회원ID</option>
                    <option value = "20" <c:if test = "${20 == search.searchDiv}">selected</c:if> >제목</option>
                    <option value = "30" <c:if test = "${30 == search.searchDiv}">selected</c:if> >작성자</option>
                </select>

                <input type = "search" name = "searchWord" id = "searchWord" value = "${search.searchWord}">
                <select name = "pageSize" id = "pageSize">
                    <option value = "10" <c:if test = "${10 == search.pageSize}">selected</c:if> >10</option>
                    <option value = "20" <c:if test = "${20 == search.pageSize}">selected</c:if> >20</option>
                    <option value = "30" <c:if test = "${30 == search.pageSize}">selected</c:if> >30</option>
                    <option value = "40" <c:if test = "${40 == search.pageSize}">selected</c:if> >40</option>
                    <option value = "50" <c:if test = "${50 == search.pageSize}">selected</c:if> >50</option>
                    <option value = "100"<c:if test = "${100 == search.pageSize}">selected</c:if> >100</option>
                </select>
                <input type="button" value="조회" id="doRetrieveBtn">
            </div>
        </form>
        <table>
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                </tr>
            </thead>
            <tbody>
<%--<c:choose>--%>
    <%--<c:when test = "${list.size()>0}">--%>
                <c:forEach var="board" items="${list}">
                    <c:if test="${board.article_board_div == 10}">
                        <tr>
                            <td>${board.article_no}</td>
                            <td><a href="boardDetail.do?article_no=${board.article_no}">${board.article_title}</a></td>
                            <td>${board.userVO.name}</td>
                            <td>${board.article_reg_date}</td>
                        </tr>
                    </c:if>
                </c:forEach>
    <%--</c:when>--%>
<%--</c:choose>--%>
            </tbody>
        </table>
        <br>
        <% out.print(pageHtml); %>
        <!-- 게시글 작성 버튼 -->
        <a href="announcementwrite.do"><button>공지 게시글 작성</button></a>
    </div>
    <script src="/ehr/resources/assets/js/board/board_page.js"></script> <!-- 여기에 스크립트 파일 경로 -->
</body>
</html>
