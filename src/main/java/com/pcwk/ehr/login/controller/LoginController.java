package com.pcwk.ehr.login.controller;


import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.login.service.LoginService;
import com.pcwk.ehr.user.domain.UserVO;

@Controller
@RequestMapping("login")
public class LoginController {

	final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private LoginService loginService;

	public LoginController() {

	}
	
	@GetMapping("/login_index.do")
	public String loginIndex() {
		String viewName = "login/login";
		
		return viewName;
	}
	
	@GetMapping("/logout.do")
	public String logout(HttpSession httpSession, HttpServletResponse response) {
		String viewName = "login/login";
		
		if(null != httpSession.getAttribute("user")) {
			//session 삭제
			httpSession.invalidate();
		}
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
		
		return viewName;
	}
	
	@RequestMapping(value = "login.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody //JSON으로 받는 거면 responsebody 붙여야함, 안 붙이면 화면이 돌아감
	public String login(UserVO user, HttpSession httpSession) throws SQLException{
		
		String jsonString = "";
		
		int flag = loginService.idPassCheck(user);
		
		String message = "";
		
		if(10 == flag) {
			message = "아이디를 확인하세요.";
		} else if(20 == flag) {
			message = "비밀번호를 확인하세요.";
		} else if(30 == flag) {
			UserVO outVO = loginService.doSelectOne(user);
			message = outVO.getName() + "님 환영합니다!";
			
			//session 생성
			httpSession.setAttribute("user", outVO);
		} else {
			flag = 99;
			message = "오류가 발생했습니다.";
		}
		
		jsonString = new Gson().toJson(new MessageVO(flag, message));
		
		return jsonString;
	}
}
