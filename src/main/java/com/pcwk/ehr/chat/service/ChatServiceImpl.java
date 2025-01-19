package com.pcwk.ehr.chat.service;

import java.sql.SQLException;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.chat.dao.ChatDao;
import com.pcwk.ehr.chat.domain.ChatVO;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatDao chatDao;

    @Override
    public int InsertMessage(ChatVO chatVO) {
        return chatDao.InsertMessage(chatVO);
    }

    @Override
    public List ShowMessage(int roomId) {
        return chatDao.ShowMessage(roomId);
    }
}
