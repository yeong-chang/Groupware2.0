package com.pcwk.ehr.chat.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.chat.domain.ChatVO;
import org.springframework.web.bind.annotation.PostMapping;

public interface ChatService {
    int InsertMessage(ChatVO chatVO);

}
