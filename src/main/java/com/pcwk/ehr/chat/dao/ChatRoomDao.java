package com.pcwk.ehr.chat.dao;

import java.util.List;

import com.pcwk.ehr.chat.domain.ChatRoomVO;
import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;

import javax.servlet.http.HttpSession;

public interface ChatRoomDao {
	List ShowChatRoom(int userId);
	List<UserVO> getAllUsers();
	List<ChatVO> getAllChats(int userId, int roomId);
	int CreateChatRoom(ChatRoomVO vo);
	int DeleteChatRoom(int roomId);
	int GetChatRoomId(int roomId);
}