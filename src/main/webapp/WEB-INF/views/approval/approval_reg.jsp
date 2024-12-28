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
<script src = "/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
<script src = "/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
<script src ="/ehr/resources/assets/js/approval/approval_reg.js?date=<%=new Date()%>"></script>
<title>결재건 등록</title>
</head>
<body>
    <h2>결재건 등록</h2>
     <div>
          <input type = "button" id = "doSave" value = "등록">
          <input type = "button" id = "moveToPrevious" value = "목록">
     </div>
	    <form action ="#" method = "post">
      
	    <div>
	        <label for = "approval_doc_no"></label>
	        <input type = "text" maxlength = "8" name = "approval_doc_no" id = "approval_doc_no" value = "0" style = "display: none">
	    </div>
	    
	    <div>
	        <label for ="approval_user_id">상신자</label>
	        <input type ="text" value = "${sessionScope.user.userId}" name ="approval_user_id" id ="approval_user_id" disabled = "disabled">
	    </div> 
	    
	    <div>
	       <label for = "approval_doc_reg_date">등록일</label>
	       <input type ="date" name ="approval_doc_reg_date" id = "approval_doc_reg_date" value="<%= today %>" disabled = "disabled">
	    </div>
	    
	    <div>
           <label for ="approval_doc_title">제목</label>
           <input type = "text" maxlength = "30" name ="approval_doc_title" id ="approval_doc_title">
        </div>
        
	     <div>
	       <label for = "approval_doc_approved_date"></label>
	       <input type ="date" name ="approval_doc_approved_date" id = "approval_doc_approved_date" style = "display: none">
	    </div>
	    
	    <div>
	       <label for = "approval_doc_closing_date">마감일</label>
	       <input type = "date" name ="approval_doc_closing_date" id ="approval_doc_closing_date">
	    </div>
	    
	   <div>
	       <label for = "approval_status"></label>
	       <input type = "text" name = "approval_status" id = "approval_status" style = "display: none">
	    </div>
	    
	    <div>
	    	<label for = approval_contents>내용</label>
	    	<input type = "text" name = "approval_contents" id = "approval_contents">
	    </div>
	    
    </form>
</body>
</html>