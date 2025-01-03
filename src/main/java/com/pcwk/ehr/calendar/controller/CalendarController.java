package com.pcwk.ehr.calendar.controller;

import com.google.gson.Gson;
import com.pcwk.ehr.calendar.dao.CalendarDao;
import com.pcwk.ehr.calendar.dao.CalendarDaoJdbc;
import com.pcwk.ehr.calendar.domain.CalendarVO;
import com.pcwk.ehr.calendar.service.CalendarService;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;
    @Autowired
    private HttpSession httpSession;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;
    private boolean isSessionValid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }

    // 일정 페이지로 이동
    @GetMapping(value = "/show.do", produces = "applaication/json; charset=UTF-8")
    public String calendar(Model model, Model models,HttpSession httpSession, HttpServletRequest req) throws SQLException {
        UserVO user = (UserVO)httpSession.getAttribute("user");
        UserVO outVO = userService.doSelectOne(user);
        // 일정 데이터를 가져와서 뷰로 전달
        try {
            List<Map<String, Object>> events = calendarService.getCalendarEvents(httpSession);
            List<Map<String, String>> jsonEvents = new ArrayList<>();
            for (Map<String, Object> event : events) {
                Map<String, String> jsonEvent = new HashMap<>();
                jsonEvent.put("title", (String) event.get("SCHEDULE_TITLE"));
                jsonEvent.put("start", ((Timestamp) event.get("SCHEDULE_START_DATE")).toInstant().toString());
                jsonEvent.put("end", ((Timestamp) event.get("SCHEDULE_END_DATE")).toInstant().toString());
                jsonEvents.add(jsonEvent);
            }
            model.addAttribute("events", jsonEvents);  // model에 일정 데이터 추가
        } catch (SQLException e) {
            e.printStackTrace();
        }
        models.addAttribute("vo", outVO);
        return "calendar/calendar";  // 캘린더 뷰 반환
    }

    // 일정 추가 요청 처리
    @PostMapping(value = "/addEvent.do", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, String>> addEvent(@RequestBody CalendarVO inVO) throws SQLException {
        Map<String, String> response = new HashMap<>();
        try {
            // 이벤트 추가 처리 (DB에 저장)
            calendarService.createEvent(inVO);
            response.put("message", "일정이 등록되었습니다.");

            // 성공 시 JSON 응답 반환
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "일정 등록에 실패했습니다.");
            // 실패 시 JSON 응답 반환
            return ResponseEntity.status(500).body(response);
        }
    }

    // 일정 데이터를 가져오기 (JSON 반환)
    @GetMapping(value = "/getEvents.do", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<Map<String, String>>> getEvents(HttpSession httpSession) throws SQLException {
        try {
            // 일정 데이터 가져오기
            List<Map<String, Object>> events = calendarService.getCalendarEvents(httpSession);
            List<Map<String, String>> jsonEvents = new ArrayList<>();

            for (Map<String, Object> event : events) {
                Map<String, String> jsonEvent = new HashMap<>();
                //일정 아이디
                jsonEvent.put("id", String.valueOf(event.get("SCHEDULE_ID")));
                // 일정 제목
                jsonEvent.put("title", (String) event.get("SCHEDULE_TITLE"));
                // 일정 내용
                jsonEvent.put("content", (String) event.get("SCHEDULE_CONTENT"));
                // 시작 날짜 (ISO-8601 형식으로 변환)
                jsonEvent.put("start", ((Timestamp) event.get("SCHEDULE_START_DATE")).toInstant().toString());
                // 종료 날짜 (ISO-8601 형식으로 변환)
                jsonEvent.put("end", ((Timestamp) event.get("SCHEDULE_END_DATE")).toInstant().toString());
                //일정 상태
                jsonEvent.put("status", String.valueOf(event.get("SCHEDULE_STATUS")));
                jsonEvent.put("scheduleUserId", String.valueOf(event.get("SCHEDULE_USER_ID")));

                jsonEvents.add(jsonEvent);
            }

            // 성공 시 JSON 응답 반환
            return ResponseEntity.ok(jsonEvents);

        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 발생 시 HTTP 500 상태 코드와 함께 에러 메시지 반환
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "일정 데이터를 가져오는 중 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(Collections.singletonList(errorResponse));
        }
    }

    //일정 삭제
    @PostMapping(value = "/deleteEvent.do", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, String> deleteEvent(@RequestBody Map<String, Object> request) {
        Map<String, String> response = new HashMap<>();
        try {
            System.out.println("Request payload: " + request); // 디버깅용 로그
            // 요청 데이터에서 scheduleId 가져오기
            Object scheduleIdObj = request.get("scheduleId");
            if (scheduleIdObj == null) {
                response.put("message", "삭제 요청에 scheduleId가 포함되지 않았습니다.");
                return response;
            }
            // scheduleId를 int로 변환
            int scheduleId;
            try {
                scheduleId = Integer.parseInt(scheduleIdObj.toString());
            } catch (NumberFormatException e) {
                response.put("message", "scheduleId는 정수여야 합니다.");
                return response;
            }
            // service 호출
            calendarService.deleteEvent(scheduleId);
            if (scheduleId > 0) {
                response.put("message", "일정이 삭제되었습니다.");
            } else {
                response.put("message", "일정 삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "서버 오류로 일정 삭제에 실패했습니다.");
        }
        return response;
    }

    // 일정 수정
    @PostMapping("/updateEvent.do")
    @ResponseBody
    public Map<String, Object> updateEvent(@RequestBody CalendarVO vo) {
        Map<String, Object> result = new HashMap<>();
        try {
            // DAO 객체를 사용하여 updateEvent 호출
            int updatedRows = calendarService.updateEvent(vo); // 수정된 행 수 반환
            if (updatedRows > 0) {
                result.put("success", true);
                result.put("message", "일정이 수정되었습니다.");
            } else {
                result.put("success", false);
                result.put("message", "일정을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "수정 중 오류 발생: " + e.getMessage());
        }
        return result;
    }

    // 일정 완료
    @PostMapping("/completeEvent.do")
    @ResponseBody
    public Map<String, Object> completeEvent(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 요청에서 scheduleId 추출
            int scheduleId = Integer.parseInt(request.get("scheduleId").toString());
            int result = calendarService.completeEvent(scheduleId);

            if (result > 0) {
                response.put("success", true);
                response.put("message", "일정이 완료되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "일정을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "완료 처리 중 오류 발생: " + e.getMessage());
        }
        return response;
    }
}
