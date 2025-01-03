package com.pcwk.ehr.calendar.dao;

import com.pcwk.ehr.calendar.domain.CalendarVO;

import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public class CalendarDaoJdbc implements CalendarDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private HttpSession httpSession;
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    public int getNextScheduleId() {
        String sql = "SELECT COALESCE(MAX(SCHEDULE_ID), 0) + 1 FROM CALENDAR";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int createEvent(CalendarVO inVO) throws SQLException {
        StringBuilder sb = new StringBuilder(500);

        // ISO 8601 형식의 문자열을 UTC 시간 기준으로 파싱
        String startDateStr = inVO.getScheduleStartDate();
        String endDateStr = inVO.getScheduleEndDate();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // 입력된 날짜 문자열을 OffsetDateTime으로 파싱 (시간대 정보 포함)
        OffsetDateTime startDate = OffsetDateTime.parse(startDateStr, formatter);
        OffsetDateTime endDate = OffsetDateTime.parse(endDateStr, formatter);


        // 시간을 UTC로 변환
        startDate = startDate.withOffsetSameInstant(ZoneOffset.UTC); // UTC로 변환
        endDate = endDate.withOffsetSameInstant(ZoneOffset.UTC); // UTC로 변환


        startDate = startDate.minusHours(9);  // 9시간 빼기
        endDate = endDate.minusHours(9);


        Timestamp startTimestamp = Timestamp.from(startDate.toInstant());
        Timestamp endTimestamp = Timestamp.from(endDate.toInstant());

        int scheduleId = getNextScheduleId();

        sb.append("INSERT INTO CALENDAR (SCHEDULE_ID, SCHEDULE_USER_ID, SCHEDULE_TITLE, SCHEDULE_CONTENT, ")
                .append("SCHEDULE_START_DATE, SCHEDULE_END_DATE, SCHEDULE_STATUS) ")
                .append("VALUES (?, ?, ?, ?, ?, ?, 10)");

        // 파라미터 설정
        Object[] params = new Object[]{
                scheduleId,
                inVO.getScheduleUserId(),
                inVO.getScheduleTitle(),
                inVO.getScheduleContent(),
                startTimestamp,
                endTimestamp
        };

        return jdbcTemplate.update(sb.toString(), params);
    }

    public List<Map<String, Object>> getCalendarEvents(HttpSession session) throws SQLException {
        // 세션에서 로그인한 사용자 ID를 가져옵니다.
        UserVO user = (UserVO)httpSession.getAttribute("user");
        UserVO outVO = userService.doSelectOne(user);


        StringBuilder sb = new StringBuilder();
        sb.append("SELECT SCHEDULE_ID, SCHEDULE_USER_ID, SCHEDULE_TITLE, SCHEDULE_CONTENT, ")
                .append("SCHEDULE_START_DATE, SCHEDULE_END_DATE, SCHEDULE_STATUS ")
                .append("FROM CALENDAR WHERE SCHEDULE_USER_ID = ?");

        // 로그인한 사용자 ID를 쿼리 파라미터로 전달합니다.
        return jdbcTemplate.queryForList(sb.toString(), outVO.getUserId());
    }


    //삭제 메서드
    @Override
    public int deleteEvent(int scheduleId) throws SQLException {
        System.out.println("Attempting to delete schedule with ID: " + scheduleId); // 디버깅용 로그
        String sql = "DELETE FROM CALENDAR WHERE SCHEDULE_ID = ?";
        return jdbcTemplate.update(sql, scheduleId);
    }

    // 일정 수정 메서드
    @Override
    public int updateEvent(CalendarVO vo) {
        try {
            // ISO-8601 날짜 변환
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            OffsetDateTime startDate = OffsetDateTime.parse(vo.getScheduleStartDate(), formatter);
            OffsetDateTime endDate = OffsetDateTime.parse(vo.getScheduleEndDate(), formatter);

            startDate = startDate.minusHours(9);  // 9시간 빼기
            endDate = endDate.minusHours(9);

            // OffsetDateTime -> Timestamp 변환
            Timestamp startTimestamp = Timestamp.from(startDate.toInstant());
            Timestamp endTimestamp = Timestamp.from(endDate.toInstant());

            String sql = "UPDATE CALENDAR "
                    + "SET SCHEDULE_TITLE = ?, SCHEDULE_CONTENT = ?, SCHEDULE_START_DATE = ?, SCHEDULE_END_DATE = ? "
                    + "WHERE SCHEDULE_ID = ?";

            return jdbcTemplate.update(sql,
                    vo.getScheduleTitle(),
                    vo.getScheduleContent(),
                    startTimestamp,
                    endTimestamp,
                    vo.getScheduleId());
        } catch (Exception e) {
            System.err.println("[ERROR] Update Failed: " + e.getMessage());
            return 0;
        }
    }

    // 일정 완료 메서드
    @Override
    public int completeEvent(int scheduleId) throws SQLException {
        String sql = "UPDATE CALENDAR SET SCHEDULE_STATUS = 20 WHERE SCHEDULE_ID = ?";
        return jdbcTemplate.update(sql, scheduleId);
    }



}
