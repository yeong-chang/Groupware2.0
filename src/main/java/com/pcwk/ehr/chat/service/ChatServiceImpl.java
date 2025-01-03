package com.pcwk.ehr.chat.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.chat.dao.ChatDao;
import com.pcwk.ehr.chat.domain.ChatVO;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	ChatDao chatDao;
	
	@Override
	public int sendChat(ChatVO inVO) throws SQLException {
		return chatDao.sendChat(inVO);
	}

	@Override
	public List<ChatVO> getMessagesByRoomId(int roomId) throws SQLException {
		return chatDao.getMessagesByRoomId(roomId);
	}

}
