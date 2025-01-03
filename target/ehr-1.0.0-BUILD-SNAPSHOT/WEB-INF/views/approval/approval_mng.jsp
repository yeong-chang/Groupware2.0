<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel ="stylesheet" href = "/ehr/resources/assets/css/main/main.css?date=<%=new Date()%>">
<script src = "/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
<script src = "/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
<script src = "/ehr/resources/assets/js/approval/approval_mng.js?date=<%=new Date()%>"></script> <!--  서버 전송 -->
<title>결재 관리</title>
<style>
.buttons {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
  gap: 10px;
}

input[type="button"] {
  padding: 10px 15px;
  font-size: 16px;
  cursor: pointer;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  transition: background-color 0.3s;
}

input[type="button"]:hover {
  background-color: #0056b3;
}

form {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.form-group {
  width: 100%;
}

label {
  display: block;
  font-size: 14px;
  margin-bottom: 5px;
}

input[type="text"], input[type="date"], select {
  width: 100%;
  padding: 8px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

select:disabled, input:disabled {
  background-color: #e9ecef;
}

select {
  padding: 8px;
}

@media (max-width: 768px) {
  .buttons {
    align-items: center;
  }
}
  }
</style>
</head>
<body>
<jsp:include page = "/WEB-INF/views/include/header.jsp"></jsp:include>
<jsp:include page = "/WEB-INF/views/include/aside.jsp"></jsp:include>
    <h2>결재 관리</h2>
    <div class = "main-container">
      <div>
       <input type = "button" id = "moveToList" value = "목록">
       <input type = "button" id = "doUpdate"   value = "수정">
       
       <c:choose>
            <c:when test = "${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.userId == vo.approval_user_id}">
                  <input type = "button" id = "doDelete"   value = "삭제">
            </c:when>
            
            <c:otherwise>
                  <input type = "button" id = "doDelete"   value = "삭제" hidden>
            </c:otherwise>
       </c:choose>
       <c:choose>
            <c:when test = "${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.position == 'CEO' || (sessionScope.user.position == 'Department Head' && vo.approval_status == 30)}">
               <input type = "button" id = "doApprove"  value = "결재">
               <input type = "button" id = "doReject"   value = "반려">
            </c:when>
            <c:otherwise>
                <input type = "button" id = "doApprove"  value = "결재" hidden>
               <input type = "button" id = "doReject"   value = "반려" hidden>
            </c:otherwise>
       </c:choose>
      </div>
	      <div>
	        <form action = "#">
	            <div>
	                <label for = "approval_doc_no">결재건 번호</label>
	                <input type = "text" maxlength = "8" name = "approval_doc_no" id = "approval_doc_no" value = "${vo.approval_doc_no }" disabled = "disabled">
	            </div>
	            
	            <div>
	                <label for = "approval_user_id">상신자ID</label>
	                <input type = "text" maxlength = "4" name = "approval_user_id" id = "approval_user_id" value = "${vo.approval_user_id }" disabled = "disabled">
	            </div>
	            
	            <c:choose>
	               <c:when test = "${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.userId == vo.approval_user_id}">
	                   <div>
		                    <label for = "approval_doc_title">제목</label>
		                    <input type = "text" maxlength = "30" name ="approval_doc_title" id= "approval_doc_title" value = "${vo.approval_doc_title }">
                       </div>
	               </c:when>
	               
	               <c:otherwise>
	                   <div>
		                    <label for = "approval_doc_title">제목</label>
		                    <input type = "text" maxlength = "30" name ="approval_doc_title" id= "approval_doc_title" value = "${vo.approval_doc_title }" disabled = "disabled">
                       </div>
	               </c:otherwise>
	            </c:choose>
	            
	            <div>
	                <label for = "approval_doc_reg_date">상신일</label>
	                <input type = "date" id ="approval_doc_reg_date" name = "approval_doc_reg_date" value = "${vo.approval_doc_reg_date}" disabled = "disabled">
	            </div>    
	            
	            <c:choose>
		            <c:when test = "${sessionScope.user.position == 'ADMINISTRATOR'}">
		                <div>
		                <label for = "approval_doc_approved_date">결재일</label>
		                <input type = "date" id ="approval_doc_approved_date" name = "approval_doc_approved_date" value = "${vo.approval_doc_approved_date}">
		                </div>
		            </c:when>
	            
		            <c:otherwise>
		                <div>
		                 <label for = "approval_doc_approved_date">결재일</label>
		                <input type = "date" id ="approval_doc_approved_date" name = "approval_doc_approved_date" value = "${vo.approval_doc_approved_date}" disabled = "disabled">
		                </div>
		            </c:otherwise>
	            </c:choose>
	            
	            
	            <c:choose>
                    <c:when test = "${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.position == 'CEO' || sessionScope.user.userId == vo.approval_user_id}">
                        <div>
		                    <label for = "approval_doc_closing_date">마감일</label>
		                    <input type = "date" id ="approval_doc_closing_date" name = "approval_doc_closing_date" value = "${vo.approval_doc_closing_date }">
		                </div>    
                    </c:when>
                
                    <c:otherwise>   
                        <div>
		                    <label for = "approval_doc_closing_date">마감일</label>
		                    <input type = "date" id ="approval_doc_closing_date" name = "approval_doc_closing_date" value = "${vo.approval_doc_closing_date }" disabled = "disabled">
                        </div>    
                    </c:otherwise>
                </c:choose>
	            
	         
	            <c:choose>
	                <c:when test = "${sessionScope.user.position == 'ADMINISTRATOR' }">
	                    <div>
		                  <label for = "approval_status">상태</label> 
		                  <select id = "approval_status" name = "approval_status">
		                    <option value = "10" ${vo.approval_status == 10 ? 'selected = "selected"': '' }>결재완료</option>
		                    <option value = "20" ${vo.approval_status == 20 ? 'selected = "selected"': '' }>반려</option>
		                    <option value = "30" ${vo.approval_status == 30 ? 'selected = "selected"': '' }>처리중</option>
		                    <option value = "35" ${vo.approval_status == 35 ? 'selected = "selected"': '' }>추가 결재 대기중</option>
	                     </select>
	                   </div>  
	                </c:when>
	                <c:otherwise>
	                    <div>
                         <label for = "approval_status">상태</label> 
                         <select id = "approval_status" name = "approval_status" disabled>
	                        <option value = "10" ${vo.approval_status == 10 ? 'selected = "selected"': '' }>결재완료</option>
	                        <option value = "20" ${vo.approval_status == 20 ? 'selected = "selected"': '' }>반려</option>
	                        <option value = "30" ${vo.approval_status == 30 ? 'selected = "selected"': '' }>처리중</option>
	                        <option value = "35" ${vo.approval_status == 35 ? 'selected = "selected"': '' }>추가 결재 대기중</option>
	                    </select>
                     </div>  
	                </c:otherwise>
	            </c:choose>
	             
	          <c:choose>
	             <c:when test = "${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.userId == vo.approval_user_id}">
		             <div>
	                    <label for = "approval_contents">내용</label>
	                    <input type = "text" maxlength = "30" name ="approval_contents" id= "approval_contents" value = "${vo.approval_contents }">
	                </div>
	             </c:when>
	             
	             <c:otherwise>
		             <div>
	                    <label for = "approval_contents">내용</label>
	                    <input type = "text" maxlength = "30" name ="approval_contents" id= "approval_contents" value = "${vo.approval_contents }" disabled = "disabled">
	                </div>
	             </c:otherwise>
	          </c:choose>
        </form>        
     </div>
     </div>
</body>
</html>