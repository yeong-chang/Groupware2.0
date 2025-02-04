package com.pcwk.ehr.chat.service;

import java.util.List;

import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.user.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.chat.dao.ChatRoomDao;
import com.pcwk.ehr.chat.domain.ChatRoomVO;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{

	@Autowired
	private ChatRoomDao chatRoomDao;

	@Override
	public List<UserVO> getAllUsers() {
		return chatRoomDao.getAllUsers();
	}

	@Override
	public List ShowChatRoom(int userId) {
		return chatRoomDao.ShowChatRoom(userId);
	}

	@Override
	public int CreateChatRoom(ChatRoomVO vo) {
		return chatRoomDao.CreateChatRoom(vo);
	}

	@Override
	public int DeleteChatRoom(int roomId) {
		return chatRoomDao.DeleteChatRoom(roomId);
	}

	@Override
	public List<ChatVO> getAllChats(int userId, int roomId) {
		return chatRoomDao.getAllChats(userId, roomId);
	}

	@Override
	public int GetChatRoomId(int roomId) {
		return chatRoomDao.GetChatRoomId(roomId);
	}
}
