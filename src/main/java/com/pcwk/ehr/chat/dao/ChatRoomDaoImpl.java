package com.pcwk.ehr.chat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pcwk.ehr.board.domain.BoardVO;
import com.pcwk.ehr.user.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.chat.domain.ChatRoomVO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.SearchVO;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@Repository
public class ChatRoomDaoImpl implements ChatRoomDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	@Override
	public List<ChatRoomVO> ShowChatRoom(int userId) {

		// SQL 쿼리 작성
		StringBuilder sb = new StringBuilder(100);
		sb.append("SELECT chat_room_id, sender_id, receiver_id \n");
		sb.append("FROM messenger \n");
		sb.append("WHERE sender_id = ? OR receiver_id = ? \n");  // sender_id나 receiver_id가 일치하는 경우
		sb.append("ORDER BY chat_room_id ASC \n");

		// SQL 쿼리를 실행하고 결과를 ChatRoomVO 객체 리스트로 매핑
		List<ChatRoomVO> chatRoomList = jdbcTemplate.query(sb.toString(), new Object[] { userId, userId }, new RowMapper<ChatRoomVO>() {
			@Override
			public ChatRoomVO mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
				ChatRoomVO chatRoomVO = new ChatRoomVO();
				chatRoomVO.setRoomId(rs.getInt("chat_room_id"));
				chatRoomVO.setSenderId(rs.getInt("sender_id"));
				chatRoomVO.setReceiverId(rs.getInt("receiver_id"));
				return chatRoomVO;
			}
		});

		return chatRoomList;  // List<ChatRoomVO> 반환
	}

	@Override
	public List<UserVO> getAllUsers() {
		String sql = "SELECT USER_ID, USER_NAME FROM GW_USER";  // users 테이블에서 id와 name을 가져오는 SQL 쿼리

		// 쿼리를 실행하고 결과를 UserVO 객체로 매핑하여 리스트로 반환
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			UserVO user = new UserVO();
			user.setUserId(rs.getInt("USER_ID"));  // DB에서 id 값 가져오기
			user.setName(rs.getString("USER_NAME"));  // DB에서 name 값 가져오기
			return user;
		});
	}

	@Override
	public int CreateChatRoom(ChatRoomVO vo) {
		int flag = 0;
		StringBuilder sb = new StringBuilder(100);

		sb.append("INSERT INTO messenger (\n");
		sb.append("	    chat_room_id,     \n");
		sb.append("	    sender_id,        \n");
		sb.append("	    receiver_id       \n");
		sb.append("	) VALUES (            \n");
		sb.append("	    chat_room_no_seq.NEXTVAL, \n");
		sb.append("	    ?,                \n");
		sb.append("	    ?                 \n");
		sb.append("	)                     \n");

		Object [] args = {vo.getSenderId(),vo.getReceiverId()};
		flag = this.jdbcTemplate.update(sb.toString(),args);

		return flag;
	}

	@Override
	public int DeleteChatRoom(int roomId) {
		int flag = 0;
		StringBuilder sb = new StringBuilder();
		ChatRoomVO vo = new ChatRoomVO();
		sb.append("DELETE FROM messenger \n");
		sb.append("WHERE chat_room_id = ? \n"); // chat_room_id를 기준으로 삭제

		Object [] args = {roomId};
		flag = this.jdbcTemplate.update(sb.toString(),args);

		return flag;
	}
}
