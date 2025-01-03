package com.pcwk.ehr.chat.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@GetMapping("/sendChat_index.do")
	public String sendChatIndex(){
		String viewName = "chat/chat";
		
		return viewName;
	}
		
	@RequestMapping(value = "/sendChat.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String sendChat(HttpSession session,ChatVO param, int receiverId) throws SQLException{
		String jsonString;
		
		UserVO user = (UserVO)session.getAttribute("user");
		param.setSenderId(user.getUserId());
		
		System.out.println("param.getContent(): " + param.getContent());
		System.out.println("param.getSenderId(): " + param.getSenderId());
		System.out.println("param.getReceiverId(): " + param.getReceiverId());
		System.out.println("param.getRoomId() : " + param.getRoomId());
		int flag = chatService.sendChat(param);
		
		String message = "";
		
		if(flag == 1) {
			message = param.getSenderId() + "님 메시지 전송에 성공하였습니다.";
		} else {
			message = param.getSenderId() + "님 메시지 전송에 실패하였습니다.";
		}
		
		MessageVO messageVO = new MessageVO(flag, message);
		  
		jsonString = new Gson().toJson(messageVO);
		
		return jsonString;		
	}
	@RequestMapping(value = "/getMessages.do", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getMessages(@RequestParam("roomId")int roomId) throws SQLException{
		
		List<ChatVO> messages = chatService.getMessagesByRoomId(roomId);
		
		System.out.println("Requested roomId: " + roomId);
		System.out.println("Messages: " + new Gson().toJson(messages));
		return new Gson().toJson(messages);
	}
}
