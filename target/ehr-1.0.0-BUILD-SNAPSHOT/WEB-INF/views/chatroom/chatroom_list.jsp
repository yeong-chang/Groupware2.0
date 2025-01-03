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
  
  //out.print("****:" + maxNum);
  
  SearchVO paramVO = (SearchVO) request.getAttribute("search");
  pageSize = paramVO.getPageSize();
  pageNo = paramVO.getPageNo();
  
  //out.print("****:" + pageSize);
  //out.print("****:" + pageNo);
  String cp = request.getContextPath();
  //out.print("cp****" + cp);
  String pageHtml = StringUtil.renderingPager(maxNum, pageNo, pageSize, bottomCount, cp + "/user/doRetrieve.do", "pageDoRetrieve");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${CP}/resources/assets/css/user/list.css?date=<%=new Date()%>">
<link rel ="stylesheet" href = "${CP}/resources/assets/css/main/main.css?date=<%=new Date()%>">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script src = "${CP}/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
<script src = "${CP}/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
<script src = "${CP}/resources/assets/js/chatroom/chatroom_list.js?date=<%=new Date()%>"></script> <!--  서버 전송 -->
<script>
    const sessionUserId = '${sessionScope.user.userId}'; // JSP에서 sessionScope 값 전달 to javascript    
</script>
<title>Insert title here</title>
</head>
<body>
	<div id="container">
	<!-- header-->
	<jsp:include page = "/WEB-INF/views/include/header.jsp"></jsp:include>
	<!--// header-------------------------------------------------->
	
	<!-- aside-->
	<jsp:include page = "/WEB-INF/views/include/aside.jsp"></jsp:include>
	<!--// aside--------------------------------------------------->
<div class = "main-container">
  <h2>회원 목록</h2>
  <hr class="title-underline" />
       <div>
            <input type="button" value="조회" id="doRetrieveBtn">
            <input type="button" value="등록" id="moveToRegBtn">
       </div>
        <table border="1" id="listTable" class="table">
            <thead> 
              <tr>
                <th class="table-head">방 번호</th>
                <th class="table-head">ID 1</th>
                <th class="table-head">ID 2</th>
              </tr>
            </thead>
            
            <tbody>
                 <c:choose>
                     <c:when test = "${list.size()>0}">
                        <c:forEach var="vo" items="${list}">
                            <tr title="더블클릭하면 상세 정보를 볼 수 있습니다.">
                                <td class="table-cell text-center">${vo.roomId}</td>
                                <td class="table-cell text-center">${vo.senderId}</td>
                                <td class="table-cell text-center">${vo.receiverId}</td>
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
    </div>
</body>
</html>