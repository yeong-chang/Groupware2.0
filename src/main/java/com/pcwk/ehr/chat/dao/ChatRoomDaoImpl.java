package com.pcwk.ehr.chat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.chat.domain.ChatRoomVO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.SearchVO;

@Repository
public class ChatRoomDaoImpl implements ChatRoomDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int createRoom(ChatRoomVO inVO) {

		int flag = 0;
		StringBuilder sb = new StringBuilder(100);
		
		sb.append("INSERT INTO messenger (\n");
		sb.append("	    chat_room_id,     \n");
		sb.append("	    sender_id,        \n");
		sb.append("	    receiver_id       \n");
		sb.append("	) VALUES (            \n");
		sb.append("	    ?,                \n");
		sb.append("	    ?,                \n");
		sb.append("	    ?                 \n");
		sb.append("	)                     \n");

		Object [] args = {inVO.getRoomId(),inVO.getSenderId(),inVO.getReceiverId()}; 
		flag = this.jdbcTemplate.update(sb.toString(),args);
		
		return flag;
	}

	@Override
	public int deleteRoom(ChatRoomVO inVO) {
		
		int flag = 0;
		StringBuilder sb = new StringBuilder(100);
		
		
		return 0;
	}

	@Override
	public List<ChatRoomVO> doRetrieve(DTO dto) {
		List<ChatRoomVO> chatRoomList = new ArrayList<ChatRoomVO>();

		SearchVO inVO = (SearchVO) dto;

		StringBuilder sb = new StringBuilder(500);
		sb.append("SELECT A.*, B.*                                                          \n");
		sb.append("  FROM (                                                                 \n");
		sb.append("		SELECT tt1.chat_room_id,                                            \n");
		sb.append("			   tt1.sender_id,                                               \n");
		sb.append("			   tt1.receiver_id                                              \n");
		sb.append("		    FROM(                                                           \n");
		sb.append("				SELECT rownum rnum, t1.*                                    \n");
		sb.append("				  FROM (                                                    \n");
		sb.append("						  SELECT *                                          \n");
		sb.append("						  FROM messenger                                    \n");
		sb.append("						  --WHERE 조건                                                                    \n");
		sb.append("						 ORDER BY chat_room_id DESC                         \n");
		sb.append("				) t1                                                        \n");
		sb.append("				WHERE ROWNUM <= (? * (? - 1) + ?)                           \n");
		sb.append("		)tt1                                                                \n");
		sb.append("		WHERE rnum >= (? * (? - 1) + 1)                                     \n");
		sb.append("  )A                                                                     \n");
		sb.append("  CROSS JOIN (                                                           \n");
		sb.append("		  SELECT COUNT(*) totalCnt                                          \n");
		sb.append("			FROM messenger                                                  \n");
		sb.append("		 --WHERE 조건                                                                        			    \n");
		sb.append("  )B                                                                     \n");

		Object[] args = null;

		if (!"".equals(inVO.getSearchDiv()) || inVO.getSearchDiv().length() > 0) {
			args = new Object[7];
			args[0] = inVO.getSearchWord();
			args[1] = inVO.getPageSize();
			args[2] = inVO.getPageNo();
			args[3] = inVO.getPageSize();
			args[4] = inVO.getPageSize();
			args[5] = inVO.getPageNo();
			args[6] = inVO.getSearchWord();
		} else {
			args = new Object[5];
			args[0] = inVO.getPageSize();
			args[1] = inVO.getPageNo();
			args[2] = inVO.getPageSize();
			args[3] = inVO.getPageSize();
			args[4] = inVO.getPageNo();
		}

		RowMapper<ChatRoomVO> users = new RowMapper<ChatRoomVO>() {

			@Override
			public ChatRoomVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ChatRoomVO outVO = new ChatRoomVO();
				
				outVO.setRoomId(rs.getInt("chat_room_id"));
				outVO.setSenderId(rs.getInt("sender_id"));
				outVO.setReceiverId(rs.getInt("receiver_id"));

				return outVO;
			}
		};

		chatRoomList = jdbcTemplate.query(sb.toString(), users, args);

		return chatRoomList;
	}

}
