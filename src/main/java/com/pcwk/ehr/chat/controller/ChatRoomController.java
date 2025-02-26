package com.pcwk.ehr.chat.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.chat.service.ChatService;
import com.pcwk.ehr.user.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.pcwk.ehr.chat.domain.ChatRoomVO;
import com.pcwk.ehr.chat.service.ChatRoomService;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.cmn.StringUtil;

@Controller
@RequestMapping("chatroom")
public class ChatRoomController {

	@Autowired
	private ChatRoomService chatRoomService;

	// 채팅방에 들어갈 때
	@GetMapping("{roomId}")
	public String enterChatRoom(@PathVariable("roomId") int roomId, Model model, HttpSession session) {
		String viewName = "chat/chat";

		// 세션에서 현재 로그인한 사용자 정보 가져오기
		UserVO user = (UserVO) session.getAttribute("user");

		// roomId를 사용하여 채팅방 정보 등을 모델에 담기
		model.addAttribute("roomId", roomId);

		try {/*
			int chatRoom = chatRoomService.GetChatRoomId(roomId);*/
			List<ChatVO> getChat =chatRoomService.getAllChats(user.getUserId(), roomId);

			if (getChat.isEmpty()) {
				System.out.println("채팅 목록이 비어있습니다.");
			}
			model.addAttribute("getChat",getChat);

		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/chatroom/show.do";
		}

		return viewName;
	}


	@GetMapping("show.do")
	public String ShowChatRoom(Model model,HttpSession session) {
		String viewName = "chatroom/chatroom_list";
		UserVO user = (UserVO) session.getAttribute("user");
		try {
			// 서비스에서 채팅방 목록을 가져옴
			List<ChatRoomVO> chatRoomList = chatRoomService.ShowChatRoom(user.getUserId());
			// 모델에 chatRoomList 추가
			model.addAttribute("chatRoomList", chatRoomList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewName;
	}

	@GetMapping("create_chatroom_index.do")
	public String showChatRoomForm(Model model) {
		List<UserVO> userList = chatRoomService.getAllUsers(); // 유저 목록을 가져오는 서비스 호출
		model.addAttribute("userList", userList);  // userList를 모델에 추가
		return "chatroom/chatroom_reg";
	}

	@PostMapping("createChatRoom.do")
	public String createChatRoom(@RequestParam("userId1") String userId1, HttpSession session) {
		// 세션에서 로그인한 유저의 ID를 받아오기
		UserVO user = (UserVO) session.getAttribute("user");

		// 받아온 값으로 채팅방 생성 로직 실행
		// 예를 들어, 채팅방 생성 서비스 호출
		try {
			// 서비스에서 채팅방 생성 로직 처리
			ChatRoomVO chatRoomVO = new ChatRoomVO();
			chatRoomVO.setSenderId(user.getUserId());  // 로그인한 사용자의 senderId
			chatRoomVO.setReceiverId(Integer.parseInt(userId1)); // 상대방의 userId1

			// 채팅방 생성 서비스 호출
			chatRoomService.CreateChatRoom(chatRoomVO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 채팅방 생성 후 리다이렉트 또는 이동할 페이지로
		return "redirect:/chatroom/show.do";  // 채팅방 목록 페이지로 리다이렉트
	}


	@PostMapping("deletechatroom.do")
	public String deleteChatRoom(@RequestParam("roomId") int roomId) {
		try {
					chatRoomService.DeleteChatRoom(roomId);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/chatroom/show.do"; // 오류 메시지를 전달하고 목록 페이지로 리다이렉트
		}
		System.out.println("Deleting room with ID: " + roomId);
		return "redirect:/chatroom/show.do"; // 성공적으로 삭제 후 채팅방 목록 페이지로 리다이렉트

	}

}
