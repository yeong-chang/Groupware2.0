package com.pcwk.ehr.chat.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.pcwk.ehr.chat.domain.ChatRoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.chat.service.ChatService;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.user.domain.UserVO;

@Controller
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatService chatService;


    @MessageMapping("/sendMessage.do")//클라이언트에서 /app/chat로 메시지 전송시 사용
    @SendTo("/topic/messages.do")//구독중인/topic/messages.do로 메시지 브로드 케스트
    public String InsertChat(HttpSession session, ChatRoomVO chatRoomVO, ModelMap model) {

        // 세션에서 현재 로그인한 사용자 정보 가져오기
        UserVO user = (UserVO) session.getAttribute("user");



        ChatVO chatVO = new ChatVO();
        chatVO.setSenderId(user.getUserId());
        chatVO.setRoomId(chatRoomVO.getRoomId());
        chatVO.setReceiverId(chatRoomVO.getReceiverId());

        model.addAttribute("chatVO", chatVO);

        chatVO.setContent(chatVO.getContent());


        chatService.InsertMessage(chatVO);

        return "chatroom";
    }
}
