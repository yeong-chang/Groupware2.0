package com.pcwk.ehr.chat.dao;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.chat.domain.ChatVO;

public interface ChatDao {
    int InsertMessage(ChatVO chatVO);
    List ShowMessage(int roomId);
}
