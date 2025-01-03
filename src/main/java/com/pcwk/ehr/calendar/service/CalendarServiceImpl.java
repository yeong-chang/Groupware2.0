package com.pcwk.ehr.calendar.service;

import com.pcwk.ehr.calendar.dao.CalendarDao;
import com.pcwk.ehr.calendar.domain.CalendarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Autowired
    private CalendarDao calendarDao;

    @Override
    public int createEvent(CalendarVO inVO) throws SQLException {
        return calendarDao.createEvent(inVO);
    }

    @Override
    public int updateEvent(CalendarVO vo) throws SQLException {
        return calendarDao.updateEvent(vo);
    }

    @Override
    public int deleteEvent(int scheduleId) throws SQLException {
        return calendarDao.deleteEvent(scheduleId);
    }

    @Override
    public List<Map<String, Object>> getCalendarEvents(HttpSession session) throws SQLException {
        return calendarDao.getCalendarEvents(session);
    }

    @Override
    public int completeEvent(int scheduleId) throws SQLException {
        return calendarDao.completeEvent(scheduleId);
    }
}
