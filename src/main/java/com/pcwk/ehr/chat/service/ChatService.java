package com.pcwk.ehr.chat.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.chat.domain.ChatVO;

public interface ChatService {

	int sendChat(ChatVO inVO) throws SQLException;
	
	List<ChatVO> getMessagesByRoomId(int roomId) throws SQLException;
}
