package com.pcwk.ehr.user.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.cmn.StringUtil;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	final Logger log = LogManager.getLogger(getClass());
	
	@Qualifier("userServiceImpl")
	@Autowired
	private UserService userService;
	
	private boolean isSessionValid(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
	    return session != null && session.getAttribute("user") != null;
	}
	
	@GetMapping("/user_reg_index.do")
	public String userRegIndex(HttpServletRequest req, Model model) {
		
		String viewName = "user/user_reg";
		
		return viewName;
	}	
	
	@RequestMapping(value = "/doSave.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(UserVO param) throws SQLException{ 
		String jsonString = "";
		
		int flag = userService.doSave(param);
		
		String message = "";
		if(flag == 1) {
			message = param.getName() + "님 등록 성공하였습니다.";
		} else {
			message = param.getName() + "님 등록 실패하였습니다.";
		}
		
		MessageVO messageVO = new MessageVO(flag, message);
		
		jsonString = new Gson().toJson(messageVO);
		
		return jsonString;
	}
	
	@RequestMapping(value = "/doRetrieve.do", method = RequestMethod.GET)
	public String doRetrieve(HttpServletRequest req, Model model) throws Exception{
		String viewName = "user/user_list";
		
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
	
	@GetMapping("/doSelectOne.do")
	public String doSelectOne(
			@RequestParam(name = "userId", required = true, defaultValue = "1111") String inUserId, Model model, HttpServletRequest req) throws Exception {
		String viewName = "user/user_mng";
		
		UserVO inVO = new UserVO();

		int userId = Integer.parseInt(inUserId);
		inVO.setUserId(userId);
		
		UserVO outVO = userService.doSelectOne(inVO);
		
		model.addAttribute("vo", outVO);
		
		if (!isSessionValid(req)) {
	        // 세션이 유효하지 않은 경우 로그인 페이지로 리다이렉트
	        //return "redirect:/login/login_index.do?alert=needLogin";
	        
	        model.addAttribute("loginMessage", "로그인이 필요합니다.");
	        return "login/login";
	    }
		
		return viewName;
	}
	
	@GetMapping("/myPage.do")
	public String myPage(HttpSession httpSession, Model model, HttpServletRequest req) throws NullPointerException, SQLException {
		String viewName = "user/user_mng";
		
		UserVO user = (UserVO)httpSession.getAttribute("user");
		
		UserVO outVO = userService.doSelectOne(user);
		
		model.addAttribute("vo", outVO);
	
		if (!isSessionValid(req)) {
	        // 세션이 유효하지 않은 경우 로그인 페이지로 리다이렉트
	        //return "redirect:/login/login_index.do?alert=needLogin";
	        
	        model.addAttribute("loginMessage", "로그인이 필요합니다.");
	        return "login/login";
	    }
		
		return viewName;
	}
	
	@RequestMapping(value = "/doDelete.do", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(@RequestParam(name = "userId", required = true, defaultValue = "Unknow Id") String inUserId)
			throws SQLException {
		String jsonString = "";

		UserVO inVO = new UserVO();
		     
		int userId = Integer.parseInt(inUserId);
		
		inVO.setUserId(userId);

		int flag = userService.doDelete(inVO);

		String message = "";
		if (1 == flag) {
			message = inVO.getUserId() + "님이 삭제되었습니다.";
		} else {
			message = inVO.getUserId() + "님이 삭제 실패했습니다.";
		}

		jsonString = new Gson().toJson(new MessageVO(flag, message));

		return jsonString;
	}  
	
	@RequestMapping(value = "/doUpdate.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(HttpServletRequest req, UserVO param) throws SQLException {
		String jsonString = "";

		int flag = userService.doUpdate(param);

		String message = "";  

		if (1 == flag) {
			message = param.getName() + "님의 정보가 수정되었습니다.";
		} else {
			message = param.getName() + "님의 정보 수정이 실패하였습니다.";
		}  

		jsonString = new Gson().toJson(new MessageVO(flag, message));

		return jsonString;
	}
}