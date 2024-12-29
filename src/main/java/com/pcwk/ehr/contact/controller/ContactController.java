package com.pcwk.ehr.contact.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.cmn.StringUtil;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;

@Controller
@RequestMapping("contact")
public class ContactController {
	
	@Autowired
	private UserService userService;
	
	private boolean isSessionValid(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
	    return session != null && session.getAttribute("user") != null;
	}
	
	@RequestMapping(value = "/doRetrieve.do", method = RequestMethod.GET)
	public String doRetrieve(HttpServletRequest req, Model model) throws Exception{
		String viewName = "contact/contact_list";
		
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
		
		List<UserVO> list = userService.doRetrieve(search); 
		
		//총 글수
		int totalCnt = 0;
		if (null != list && list.size() > 0) {
    		UserVO vo = list.get(0);
    		totalCnt = vo.getTotalCnt();
	    }
		
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("list", list);
		model.addAttribute("search", search);
		
		if (!isSessionValid(req)) {
	        // 세션이 유효하지 않은 경우 로그인 페이지로 리다이렉트
	        //return "redirect:/login/login_index.do?alert=needLogin";
	        
	        model.addAttribute("loginMessage", "로그인이 필요합니다.");
	        return "login/login";
	    }
		
		return viewName;
	}
	
}
