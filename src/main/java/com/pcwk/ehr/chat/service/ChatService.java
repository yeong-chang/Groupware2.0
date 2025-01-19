package com.pcwk.ehr.chat.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.chat.domain.ChatVO;

public interface ChatService {
    int InsertMessage(ChatVO chatVO);
    List ShowMessage(int roomId);
}
