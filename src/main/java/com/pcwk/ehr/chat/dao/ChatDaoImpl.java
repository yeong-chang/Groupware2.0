package com.pcwk.ehr.chat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.chat.domain.ChatVO;

import javax.sql.DataSource;

@Repository
public class ChatDaoImpl implements ChatDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;


    @Override
    public int InsertMessage(ChatVO chatVO) {
        int flag = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ChatMessages (MESSAGE_ID, MESSAGE_SENDER_ID, MESSAGE_RECEIVER_ID, MESSAGE_CONTENT, MESSAGE_TIMESTAMP, MESSAGE_ROOM_ID) ")
                .append("VALUES (chat_no_seq.NEXTVAL, ?, ?, ?, SYSDATE, ?)");

        // 쿼리 실행 로직 (예: JDBC 사용)
        Object [] args ={chatVO.getSenderId(),chatVO.getReceiverId(),chatVO.getContent(),chatVO.getRoomId()};
        // PreparedStatement를 사용하여 chatVO의 필드를 쿼리의 ? 자리에 바인딩
        flag = jdbcTemplate.update(sb.toString(), args);
        return flag;  // 성공적으로 삽입된 후, 삽입된 row의 count 또는 ID 반환
    }



}
