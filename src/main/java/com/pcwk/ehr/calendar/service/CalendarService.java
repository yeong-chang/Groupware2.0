package com.pcwk.ehr.calendar.service;

import com.pcwk.ehr.calendar.domain.CalendarVO;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CalendarService {
    int createEvent(CalendarVO inVO)throws SQLException;
    int updateEvent(CalendarVO vo) throws SQLException;
    int deleteEvent(int scheduleId) throws SQLException;
    List<Map<String, Object>> getCalendarEvents(HttpSession session) throws SQLException;
    int completeEvent(int scheduleId) throws SQLException;
}
