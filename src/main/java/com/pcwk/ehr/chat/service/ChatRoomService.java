package com.pcwk.ehr.chat.service;

import java.util.List;

import com.pcwk.ehr.chat.domain.ChatRoomVO;
import com.pcwk.ehr.user.domain.UserVO;

public interface ChatRoomService {
	List<UserVO> getAllUsers();
	List ShowChatRoom(int userId);
	int CreateChatRoom(ChatRoomVO vo);
	int DeleteChatRoom(ChatRoomVO vo);
}
