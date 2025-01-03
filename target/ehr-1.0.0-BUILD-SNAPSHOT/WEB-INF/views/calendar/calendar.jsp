<%@ include file="../include/aside.jsp" %>
<%@ include file="../include/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <!-- 날짜 포맷을 위한 JSTL 함수 추가 -->


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <!-- FullCalendar JS CDN을 이용한 방법 -->

    <link rel="stylesheet" type="text/css" href="/ehr/resources/assets/css/calendar/calendar.css">
    <style>
        .top-nav-container {
            position: relative !important;
        }
        .aside-nav-container ul {
    margin-top: 10px;
    margin-left: -33 !important;
}
       /*  .aside-nav-container {
            display: flex !important;
            flex-direction: column !important;
             justify-content: normal !important;
        } */
    </style> 
</head>
<script src = "/ehr/resources/assets/js/jquery_3_7_1.js"></script> <!-- jQuery 스크립트 (AJAX 송신을 위해 필요) -->
<script src = "/ehr/resources/assets/js/cmn/common.js"></script> <!-- 공통 Util -->
<script src = "/ehr/resources/assets/js/user/user_myPage.js"></script> <!--  서버 전송 -->
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.14/index.global.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@fullcalendar/bootstrap5@6.1.14/index.global.min.js"></script>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css" rel="stylesheet" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<body>
<div id="calendar"></div>


<!-- 일정 추가 -->
<div class="modal" tabindex="-1" id="scheduleModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">일정 추가</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="scheduleUserId" >사용자 ID</label>
                    <input type="text" class="form-control" id="scheduleUserId"
                           value = "${vo.userId }" disabled = "disabled">

                </div>
                <div class="mb-3">
                    <label for="scheduleTitle" class="form-label">일정 제목</label>
                    <input type="text" class="form-control" id="scheduleTitle">
                </div>
                <div class="mb-3">
                    <label for="scheduleContent" class="form-label">일정 내용</label>
                    <input type="text" class="form-control" id="scheduleContent">
                </div>
                <div class="mb-3">
                    <label for="startDate" class="form-label">시작 날짜</label>
                    <input type="date" class="form-control" id="startDate">
                </div>
                <div class="mb-3">
                    <label for="endDate" class="form-label">종료 날짜</label>
                    <input type="date" class="form-control" id="endDate">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="button" class="btn btn-primary" id="saveScheduleButton">일정 저장</button>
            </div>
        </div>
    </div>
</div>


<!-- 일정 관리 -->
<div class="modal" tabindex="-1" id="editScheduleModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">일정 관리</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="editScheduleTitle" class="form-label">일정 제목</label>
                    <input type="text" class="form-control" id="editScheduleTitle">
                </div>
                <div class="mb-3">
                    <label for="editScheduleContent" class="form-label">일정 내용</label>
                    <input type="text" class="form-control" id="editScheduleContent">
                </div>
                <div class="mb-3">
                    <label for="editStartDate" class="form-label">시작 날짜</label>
                    <input type="date" class="form-control" id="editStartDate">
                </div>
                <div class="mb-3">
                    <label for="editEndDate" class="form-label">종료 날짜</label>
                    <input type="date" class="form-control" id="editEndDate">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="completeScheduleButton">일정 완료</button>
                <button type="button" class="btn btn-danger" id="deleteScheduleButton">일정 삭제</button>
                <button type="button" class="btn btn-primary" id="updateScheduleButton">수정 저장</button>
            </div>
        </div>
    </div>
</div>


<!-- Bootstrap JS 및 의존성 -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="/ehr/resources/assets/js/calendar/calendar.js"></script> <!-- 여기에 스크립트 파일 경로 -->
<script src = "/ehr/resources/assets/js/user/user_myPage.js"></script> <!--  서버 전송 -->

</body>
</html>
