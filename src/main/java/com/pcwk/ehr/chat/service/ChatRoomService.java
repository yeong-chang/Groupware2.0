package com.pcwk.ehr.chat.service;

import java.util.List;

import com.pcwk.ehr.chat.domain.ChatRoomVO;
import com.pcwk.ehr.cmn.DTO;

public interface ChatRoomService {

	int createRoom(ChatRoomVO inVO);
	
	int deleteRoom(ChatRoomVO inVO);
	
	List<ChatRoomVO> doRetrieve(DTO dto);
}
