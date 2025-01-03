package com.pcwk.ehr.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.chat.dao.ChatRoomDao;
import com.pcwk.ehr.chat.domain.ChatRoomVO;
import com.pcwk.ehr.cmn.DTO;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{

	@Autowired
	private ChatRoomDao chatRoomDao;

	@Override
	public int createRoom(ChatRoomVO inVO) {
		return chatRoomDao.createRoom(inVO);
	}

	@Override
	public int deleteRoom(ChatRoomVO inVO) {
		return chatRoomDao.deleteRoom(inVO);
	}

	@Override
	public List<ChatRoomVO> doRetrieve(DTO dto) {
		return chatRoomDao.doRetrieve(dto);
	}
	
	
}
