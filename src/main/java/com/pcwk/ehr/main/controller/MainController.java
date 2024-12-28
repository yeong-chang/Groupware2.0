package com.pcwk.ehr.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
public class MainController {
	
	final Logger log = LogManager.getLogger(getClass());

	//필요한 Service
	
	private boolean isSessionValid(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
	    return session != null && session.getAttribute("user") != null;
	}
	
	public MainController() {
		log.debug("┌─────────────────────────────┐");
		log.debug("│ **LoginController()**       │");
		log.debug("└─────────────────────────────┘");
	}
	
	@GetMapping("/main.do")
	public String mainIndex(HttpServletRequest req, Model model){
		String viewName = "main/main";
		
		if (!isSessionValid(req)) {
	        // 세션이 유효하지 않은 경우 로그인 페이지로 리다이렉트
	        //return "redirect:/login/login_index.do?alert=needLogin";
	        
	        model.addAttribute("loginMessage", "로그인이 필요합니다.");
	        return "login/login";
	    }
		
		return viewName;
	}
}
