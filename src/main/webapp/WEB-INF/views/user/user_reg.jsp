<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<% 
    // 오늘 날짜를 생성
    java.time.LocalDate today = java.time.LocalDate.now();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원등록</title>
<script src = "/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
<script src = "/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
<script src = "/ehr/resources/assets/js/user/user_reg.js?date=<%=new Date()%>"></script> <!--  서버 전송 -->
<script>
    const userPosition = "${sessionScope.user.position}";
</script>
</head>
<body>
	<h2>회원 등록</h2>
	
	<div>
		<input type = "button" id = "doSave" value = "등록">
		<input type = "button" id = "moveToPrevious" value = "돌아가기">
	</div>
	<!-- ------------------- -->
	
	
	<div>
		<form action = "#" method = "post">
		 <c:choose>
            <c:when test = "${sessionScope.user.position == 'ADMINISTRATOR' || sessionScope.user.position == 'CEO'}">
             <div>
                <label for = "userId">사용자 ID</label>
                <input type = "text" maxlength = "4" name = "userId" id = "userId" value = "${vo.userId }">
             </div>
             
             <div>
                <label>부서</label>
                <select name = "deptNo" id = "deptNo">
                    <option value = "1000">SW 개발 (SW development)</option>
                    <option value = "2000">마케팅     (Marketing)</option>
                    <option value = "3000">인사/총무(Human Resource) </option>
                </select>
             </div>      
             
             <div>
                <label for = "supUserId">상급자 ID</label>
                <input type = "text" maxlength = "4" name ="supUserId" id= "supUserId" value = "${vo.supUserId }">
            </div>
            
            <div>
                <label for = "name">이름</label>
                <input type = "text" maxlength = "30" name = "name" id = "name" value = "${vo.name }">
            </div>
            
            <div>
                <label for = "password">비밀번호</label>
                <input type ="text" maxlength = "50" name = "password" id = "password" value = "${vo.password }">
            </div>
            
            <div>
                <label for = "position">직급</label>
                <select name = "position" id = "position">
                    <option value = "Assistant Manager">대리</option>
                    <option value = "Department Head">부서장</option>
                    <option value = "CEO">CEO</option>
                </select>
            </div>
            
             <div>
                <label for = "birthday">생년월일</label>
                <input type = "date" id ="birthday" name = "birthday" max = "2004-01-01" min = "1950-01-01" value = "${vo.birthday }">
            </div>
            
            <div>
                <label for = "hiredate">고용일</label>
                <input type = date id ="hiredate" name = "hiredate" value = "${vo.hiredate }">
            </div>     
            
             <div>
                <label for = "phoneNo">연락처</label>
                <input type = "text" maxlength = "11" name = "phoneNo" id = "phoneNo" value = "${vo.phoneNo }">
            </div>
            
            <div>
                <label for = "status">상태</label>
                <select id = "status" name = "status">
                    <option value = "10" ${vo.status == 10 ? 'selected = "selected"': ''}>재직</option>
                    <option value = "20" ${vo.status == 20 ? 'selected = "selected"': ''}>휴가</option>
                </select>
            </div> 
                   
            </c:when>
                <c:otherwise>
              <div>
                <label for = "userId">사용자 ID</label>
                <input type = "text" maxlength = "4" name = "userId" id = "userId" value = "${vo.userId }" disabled = "disabled">
              </div>
              
              <div>           
                <label>부서</label>
                <select name = "deptNo" id = "deptNo">
                    <option value = "1000">SW 개발 (SW development)</option>
                    <option value = "2000">마케팅     (Marketing)</option>
                    <option value = "3000">인사/총무(Human Resource) </option>
                </select>
              </div>
                
              <div>
                <label for = "supUserId">상급자 ID</label>
                <input type = "text" maxlength = "4" name ="supUserId" id= "supUserId" value = "${vo.supUserId }" disabled = "disabled">
              </div>  
              
              <div>
                <label for = "name">이름</label>
                <input type = "text" maxlength = "30" name = "name" id = "name" value = "${vo.name }">
              </div>
              
              <div>
                <label for = "password">비밀번호</label>
                <input type ="password" maxlength = "50" name = "password" id = "password" value = "${vo.password }">
              </div>
              
              <div>
                <label for = "position">직급</label>
                <select name = "position" id = "position" disabled>
                    <option value = "Assistant Manager">대리</option>
                    <option value = "Department Head">부서장</option>
                    <option value = "CEO">CEO</option>
                </select>
             </div>
             
              <div>
                <label for = "birthday">생년월일</label>
                <input type = "date" id ="birthday" name = "birthday" max = "2004-01-01" min = "1950-01-01" value = "${vo.birthday }">
              </div>
              
             <div>
                <label for = "hiredate">고용일</label>
                <input type = date id ="hiredate" name = "hiredate" value="<%= today %>" disabled = "disabled">
            </div>     
            
            <div>
                <label for = "phoneNo">연락처</label>
                <input type = "text" maxlength = "11" name = "phoneNo" id = "phoneNo" value = "${vo.phoneNo }">
            </div>
            
            <div>
                <label for = "status">상태</label>
                <select id = "status" name = "status" disabled>
                    <option value = "10" ${vo.status == 10 ? 'selected = "selected"': ''}>재직</option>
                    <option value = "20" ${vo.status == 20 ? 'selected = "selected"': ''}>휴가</option>
                </select>
            </div> 
            </c:otherwise> 
            
            </c:choose>          
		</form>
	</div>
</body>
</html>