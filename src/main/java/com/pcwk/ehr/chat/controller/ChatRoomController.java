package com.pcwk.ehr.chat.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@GetMapping("/create_chatroom_index.do")
	public String createRoomIndex() {
		String viewName = "chatroom/chatroom_reg";
		
		return viewName;
	}
	
	@RequestMapping(value = "/doRetrieve.do", method = RequestMethod.GET)
	public String doRetrieve(HttpServletRequest req, Model model) throws Exception{
		String viewName = "chatroom/chatroom_list";
		
		SearchVO search = new SearchVO();
		
		String pageNoString = StringUtil.nvl(req.getParameter("pageNo"), "1");
		String pageSizeString = StringUtil.nvl(req.getParameter("pageSize"), "10");
		
		int pageNo = Integer.parseInt(pageNoString);
		int pageSize = Integer.parseInt(pageSizeString);
		
		//데이터가 null이면, ""
		String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
		String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");
		
		search.setPageNo(pageNo);
		search.setPageSize(pageSize);
		search.setSearchDiv(searchDiv);
		search.setSearchWord(searchWord);
	
		List<ChatRoomVO> list = chatRoomService.doRetrieve(search); 
		
		//총 글수
		int totalCnt = 0;
		if (null != list && list.size() > 0) {
			ChatRoomVO vo = list.get(0);
    		totalCnt = vo.getTotalCnt();
	    }
		
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("list", list);
		model.addAttribute("search", search);
		
		return viewName;
	}
	
	@RequestMapping(value = "/createRoom.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String createRoom(ChatRoomVO param) throws SQLException{
		String jsonString = "";
		
		int flag = chatRoomService.createRoom(param);
		
		System.out.println("flag: " + flag);
		String message = "";
		
		if(flag == 1) {
			message = param.getRoomId() + "번 방을 생성하였습니다.";
		} else {
			message = param.getRoomId() + "번 방 생성에 실패하였습니다.";
		}
		
		MessageVO messageVO = new MessageVO(flag, message);
		jsonString = new Gson().toJson(messageVO);
		
		return jsonString;
	}
	
}
