package com.pcwk.ehr.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	private boolean isSessionValid(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
	    return session != null && session.getAttribute("user") != null;
	}
	
	@GetMapping("/admin_reg_index.do")
	public String adminRegIndex(HttpServletRequest req, Model model) {
		
		String viewName = "admin/admin_reg";
		
		if (!isSessionValid(req)) {
	        // 세션이 유효하지 않은 경우 로그인 페이지로 리다이렉트
	        //return "redirect:/login/login_index.do?alert=needLogin";
	        
	        model.addAttribute("loginMessage", "로그인이 필요합니다.");
	        return "login/login";
	    }
		return viewName;
	}
	
}
