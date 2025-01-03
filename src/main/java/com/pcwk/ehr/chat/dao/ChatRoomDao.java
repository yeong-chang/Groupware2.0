package com.pcwk.ehr.chat.dao;

import java.util.List;

import com.pcwk.ehr.chat.domain.ChatRoomVO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;

public interface ChatRoomDao {

	int createRoom(ChatRoomVO inVO);
	
	int deleteRoom(ChatRoomVO inVO);
	
	List<ChatRoomVO> doRetrieve(DTO dto);
}
