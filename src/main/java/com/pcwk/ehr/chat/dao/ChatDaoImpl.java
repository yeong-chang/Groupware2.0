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
                .append("VALUES (?, ?, ?, ?, ?, ?)");

        // 쿼리 실행 로직 (예: JDBC 사용)
        Object [] args ={chatVO.getMessageId(),chatVO.getSenderId(),chatVO.getReceiverId(),chatVO.getContent(),chatVO.getTimestamp(),chatVO.getRoomId()};
        // PreparedStatement를 사용하여 chatVO의 필드를 쿼리의 ? 자리에 바인딩
        flag = jdbcTemplate.update(sb.toString(), args);
        return flag;  // 성공적으로 삽입된 후, 삽입된 row의 count 또는 ID 반환
    }

    @Override
    public List<ChatVO> ShowMessage(int roomId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT MESSAGE_ID, MESSAGE_SENDER_ID, MESSAGE_RECEIVER_ID, MESSAGE_CONTENT, MESSAGE_TIMESTAMP, MESSAGE_ROOM_ID ")
                .append("FROM ChatMessages ")
                .append("WHERE roomId = ? ")
                .append("ORDER BY timestamp ASC");

        // 쿼리 실행 및 결과를 ChatVO 리스트로 매핑하는 로직
        List<ChatVO> MessageList = jdbcTemplate.query(sb.toString(), new Object[]{roomId}, new RowMapper<ChatVO>() {

            @Override
            public ChatVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ChatVO chatVO = new ChatVO();
                chatVO.setMessageId(rs.getInt("MESSAGE_ID"));
                chatVO.setSenderId(rs.getInt("MESSAGE_SENDER_ID"));
                chatVO.setReceiverId(rs.getInt("MESSAGE_RECEIVER_ID"));
                chatVO.setContent(rs.getString("MESSAGE_CONTENT"));
                chatVO.setTimestamp(rs.getString("MESSAGE_TIMESTAMP"));
                chatVO.setRoomId(rs.getInt("MESSAGE_ROOM_ID"));
                return chatVO;
            }
        });


        return MessageList;  // 실제 데이터 반환
    }

}
