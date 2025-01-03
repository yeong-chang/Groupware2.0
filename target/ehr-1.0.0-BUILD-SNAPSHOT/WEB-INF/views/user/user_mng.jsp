<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<link rel ="stylesheet" href = "/ehr/resources/assets/css/main/main.css?date=<%=new Date()%>">
<script src = "/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
<script src = "/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
<script src = "/ehr/resources/assets/js/user/user_mng.js?date=<%=new Date()%>"></script> <!--  서버 전송 -->
<script>
    const sessionPosition = '${sessionScope.user.position}';
</script>

<title>회원 관리</title>
<style>
  .main-container div {
    margin-bottom: 20px;
  }

  label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
  }

  input[type="text"],
  input[type="date"],
  select {
    width: 100%;
    padding: 10px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }

  input[type="text"]:disabled,
  select:disabled {
    background-color: #e9e9e9;
    cursor: not-allowed;
  }

  input[type="button"] {
    background-color: #007bff;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    margin-right: 10px;
  }

  input[type="button"]:hover {
    background-color: #0056b3;
  }

  .form-section {
    display: flex;
    flex-wrap: wrap;
  }

  .form-section div {
    flex: 1;
    margin-right: 20px;
  }

  .form-section div:last-child {
    margin-right: 0;
  }

  .form-section select,
  .form-section input[type="text"] {
    width: calc(100% - 20px);
  }

  .form-footer {
    text-align: center;
    margin-top: 30px;
  }
</style>

</head>
<body>
<div class = "outer-container">
    <div class="nav-container">     
<jsp:include page = "/WEB-INF/views/include/header.jsp"></jsp:include>
<jsp:include page = "/WEB-INF/views/include/aside.jsp"></jsp:include>
</div>
<div class = "main-container">
     <div>
       <input type = "button" id = "moveToList" value = "돌아가기">
       <input type = "button" id = "doUpdate"   value = "수정">
       
       <c:choose>
        <c:when test = "${sessionScope.user.position == 'ADMINISTRATOR'}">
            <input type = "button" id = "doDelete"   value = "삭제">    
        </c:when>
        
        <c:otherwise>
            <input type = "button" id = "doDelete"   value = "삭제" hidden>
        </c:otherwise>
       </c:choose>
     </div>
     <!-- FORM AREA -->
     <div>
        <form action = "#">
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
                    <option value = "Assistant Manager" ${vo.position == 'Assistant Manager' ? 'selected' : ''}>대리</option>
                    <option value = "Department Head" ${vo.position == 'Department Head' ? 'selected' : ''}>부서장</option>
                    <option value = "CEO" ${vo.position == 'CEO' ? 'selected' : ''}>CEO</option>
                </select>
            </div>
            
             <div>
                <label for = "birthday">생년월일</label>
                <input type = "date" id ="birthday" name = "birthday" max = "2004-01-01" min = "1950-01-01" value = "${vo.birthday }">
            </div>
            
            <div>
                <label for = "hiredate">고용일</label>
                <input type = date id ="hiredate" name = "hiredate" max = "2004-01-01" min = "1950-01-01" value = "${vo.hiredate }">
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
                <select name = "deptNo" id = "deptNo" disabled>
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
                <input type ="text" maxlength = "50" name = "password" id = "password" value = "${vo.password }">
              </div>
              
              <div>
                <label for = "position">직급</label>
                <select name = "position" id = "position" disabled>
                      <option value = "Assistant Manager" ${vo.position == 'Assistant Manager' ? 'selected' : ''}>대리</option>
                    <option value = "Department Head" ${vo.position == 'Department Head' ? 'selected' : ''}>부서장</option>
                    <option value = "CEO" ${vo.position == 'CEO' ? 'selected' : ''}>CEO</option>
                </select>
             </div>
             
              <div>
                <label for = "birthday">생년월일</label>
                <input type = "date" id ="birthday" name = "birthday" max = "2004-01-01" min = "1950-01-01" value = "${vo.birthday }">
              </div>
              
             <div>
                <label for = "hiredate">고용일</label>
                <input type = date id ="hiredate" name = "hiredate" max = "2004-01-01" min = "1950-01-01" value = "${vo.hiredate }" disabled = "disabled">
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
         </div>
     </div>
</body>
</html>