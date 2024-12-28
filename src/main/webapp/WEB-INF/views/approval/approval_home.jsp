<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.pcwk.ehr.cmn.StringUtil"%>
<%@page import="com.pcwk.ehr.cmn.SearchVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="CP" value="${pageContext.request.contextPath}"/>
<%
  /**
 * @param maxNum: 총 글수
 * @param currentPageNo: 현재 페이지 번호
 * @param rowPerPage: 페이지 사이즈(10, 20, ... 100)
 * @param bottomCount: 10/5
 * @param url: 서버 호출 URL
 * @param scriptName: 자바스크립트 함수명
 * @return "html 텍스트"
 */
 //public static String renderingPager(int maxNum, int currentPageNo, int rowPerPage, int bottomCount, String url, String scriptName) {
  int bottomCount = 10;
  int pageSize = 10;
  int pageNo = 1;
  
  int maxNum = Integer.parseInt(request.getAttribute("totalCnt").toString()); // 총 글수
  
  // out.print("****:" + maxNum);
  
  SearchVO paramVO = (SearchVO) request.getAttribute("search");
  pageSize = paramVO.getPageSize();
  pageNo = paramVO.getPageNo();
  
  //out.print("****:" + pageSize);
  //out.print("****:" + pageNo);
  String cp = request.getContextPath();
  //out.print("cp****" + cp);
  String pageHtml = StringUtil.renderingPager(maxNum, pageNo, pageSize, bottomCount, cp + "/approval/doRetrieve.do", "pageDoRetrieve");
%>  
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="${CP}/resources/assets/images/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="${CP}/resources/assets/css/user/list.css?date=<%=new Date()%>">
<link rel ="stylesheet" href = "${CP}/resources/assets/css/main/main.css?date=<%=new Date()%>">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script src = "${CP}/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
<script src = "${CP}/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
<script src = "/ehr/resources/assets/js/approval/approval_home.js?date=<%=new Date()%>"></script>
<script>
   const sessionUserId = '${sessionScope.user.userId}'; // JSP에서 sessionScope 값 전달 to javascript
   const sessionPosition = '${sessionScope.user.position}'; // JSP에서 sessionScope 값 전달 to javascript
</script>
<style>
</style>
<title>전자결재</title>
</head>
<body>
	<div id="container">
<jsp:include page = "/WEB-INF/views/include/header.jsp"></jsp:include>
<jsp:include page = "/WEB-INF/views/include/aside.jsp"></jsp:include>
	<main id="contents" >
	 <div class = "main-container">
		<h2>전자결재</h2>
  		<hr class="title-underline" />
		 <form action = "#" class="search-form" name = "userForm" id = "userForm" method = "get" enctype="application/x-www-form-urlencoded">
		   <input type ="hidden" name = "pageNo" id = "pageNo"> <!-- 히든할 것들은 form 바로 밑에 주는 게 암묵적 약속 -->
			 <div class="search-group">
	            <label for = "searchDiv">구분</label>
	            <select name = "searchDiv" id = "searchDiv">  
	      			<option value = "">전체</option>
	                <option value = "10" <c:if test = "${10 == search.searchDiv}">selected</c:if> >상신자ID</option>
	                <option value = "20" <c:if test = "${20 == search.searchDiv}">selected</c:if> >제목</option>
	                <option value = "30" <c:if test = "${30 == search.searchDiv}">selected</c:if> >상태</option>	        
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
	            <input type="button" value="등록" id="moveToRegBtn">
	        </div>
	    </form>
	    
	    <table border="1" id="listTable" class="table">
			<thead>	
			  <tr>
			    <th class="table-head">번호</th>
			    <th class="table-head">결재건 번호</th>
				<th class="table-head">상신자ID</th>
				<th class="table-head">제목</th>
				<th class="table-head">상신일</th>
				<th class="table-head">결재일</th>
				<th class="table-head">마감일</th>
				<th class="table-head">결재상태</th>
			  </tr>
			</thead>
			
			<tbody>
			     <c:choose>
			         <c:when test = "${list.size()>0}">
					    <c:forEach var="vo" items="${list}">
							<tr title="더블클릭하면 상세 정보를 볼 수 있습니다.">
								<td class="table-cell text-center">${vo.getNo() }</td>
							    <td class="table-cell text-center">${vo.approval_doc_no}</td>
								<td class="table-cell text-center">${vo.approval_user_id}</td>
								<td class="table-cell text-left highlight">${vo.approval_doc_title}</td>
								<td class="table-cell text-center">${vo.approval_doc_reg_date}</td>
								<td class="table-cell text-center">${vo.approval_doc_approved_date}</td>
								<td class="table-cell text-center">${vo.approval_doc_closing_date}</td>
								<td class="table-cell text-cetner">
								    <c:choose>
								        <c:when test = "${vo.approval_status == 10}">결재완료</c:when>
								        <c:when test = "${vo.approval_status == 20}">반려</c:when>
								        <c:when test = "${vo.approval_status == 30}">처리중</c:when>		
								        <c:when test = "${vo.approval_status == 35 }">추가 결재 대기중</c:when>						    
								    </c:choose>
                                </td>								    
							</tr>
					    </c:forEach>
				    </c:when>
				    <c:otherwise>   
                        <tr>
                            <td colspan ="99" class="table-cell text-center" >조회된 데이터가 없습니다.</td>
                        </tr>
                    </c:otherwise>             
			    </c:choose>
	         </tbody>
	       </table>
		      		 <% out.print(pageHtml); %>
	   </div>
	</main>
<jsp:include page = "/WEB-INF/views/include/footer.jsp"></jsp:include>
	</div>
</body>
</html>