package com.pcwk.ehr.chat.service;

import java.util.List;

import com.pcwk.ehr.chat.domain.ChatRoomVO;
import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.user.domain.UserVO;

public interface ChatRoomService {
	List<UserVO> getAllUsers();
	List ShowChatRoom(int userId);
	int CreateChatRoom(ChatRoomVO vo);
	int DeleteChatRoom(int roomId);
	List<ChatVO> getAllChats(int userId, int roomId);
	int GetChatRoomId(int roomId);
}
