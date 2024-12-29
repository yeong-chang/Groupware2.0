package com.pcwk.ehr.approval.controller;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.approval.domain.ApprovalVO;
import com.pcwk.ehr.approval.service.ApprovalService;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.cmn.StringUtil;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;

@Controller
@RequestMapping("approval")
public class ApprovalController {

	@Qualifier("approvalServiceImpl")
	@Autowired
	private ApprovalService approvalService;

	@Autowired
	private UserService userService;

	private boolean isSessionValid(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null && session.getAttribute("user") != null;
	}

	@GetMapping("/approval_reg_index.do")
	public String approvalRegIndex(HttpServletRequest req, Model model) {
		String viewName = "approval/approval_reg";

		if (!isSessionValid(req)) {
			// 세션이 유효하지 않은 경우 로그인 페이지로 리다이렉트
			// return "redirect:/login/login_index.do?alert=needLogin";

			model.addAttribute("loginMessage", "로그인이 필요합니다.");
			return "login/login";
		}

		return viewName;
	}

	/**
	 * 결재건 등록
	 * 
	 * @param req
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/doSavaApprovalDoc.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(HttpSession session, ApprovalVO approvalVO, Model model) throws SQLException {

		String jsonString = "";

		UserVO user = (UserVO) session.getAttribute("user");

		UserVO outVO = userService.doSelectOne(user);

		// 서비스계층 과 연결
		int flag = approvalService.doSave(approvalVO, outVO);

		// jsonString으로 만들기 위한 준비
		String message = "";

		// 서비스 계층에서 받은 결과가 1일 경우, 0일 경우
		if (1 == flag) {
			message = approvalVO.getApproval_doc_no() + "번의 결재건을 등록하였습니다.";
		} else {
			message = approvalVO.getApproval_doc_no() + "번의 결재건 등록을 실패하였습니다.";
		}

		MessageVO messageVO = new MessageVO(flag, message);

		jsonString = new Gson().toJson(messageVO);

		return jsonString;
	}

	/**
	 * 모든 결재 문서 조회
	 * 
	 * @param req
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doRetrieve.do", method = RequestMethod.GET)
	public String doRetrieve(HttpServletRequest req, Model model) throws Exception {
		String viewName = "approval/approval_home";

		SearchVO search = new SearchVO();

		String pageNoString = StringUtil.nvl(req.getParameter("pageNo"), "1");
		String pageSizeString = StringUtil.nvl(req.getParameter("pageSize"), "10");

		int pageNo = Integer.parseInt(pageNoString);
		int pageSize = Integer.parseInt(pageSizeString);

		String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
		String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");

		search.setPageNo(pageNo);
		search.setPageSize(pageSize);
		search.setSearchDiv(searchDiv);
		search.setSearchWord(searchWord);

		List<ApprovalVO> list = approvalService.doRetrieve(search);

		// 총 글수
		int totalCnt = 0;
		if (null != list && list.size() > 0) {
			ApprovalVO vo = list.get(0);
			totalCnt = vo.getTotalCnt();
		}

		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("list", list);
		model.addAttribute("search", search);

		if (!isSessionValid(req)) {
			// 세션이 유효하지 않은 경우 로그인 페이지로 리다이렉트
			// return "redirect:/login/login_index.do?alert=needLogin";

			model.addAttribute("loginMessage", "로그인이 필요합니다.");
			return "login/login";
		}

		return viewName;
	}

	/**
	 * 특정 결재 문서 조회
	 * 
	 * @param approvalDocNo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/doSelectOne.do")
	public String doSelectOne(
			@RequestParam(name = "approval_doc_no", required = true, defaultValue = "1") int approval_doc_no,
			Model model, HttpServletRequest req, HttpSession session) throws Exception {

		String viewName = "approval/approval_mng";

		UserVO user = (UserVO) session.getAttribute("user");

		ApprovalVO inVO = new ApprovalVO();

		inVO.setApproval_doc_no(approval_doc_no);

		ApprovalVO outApprovalVO = approvalService.doSelectOne(inVO);

		// 보안성 + 을 위해 클라이언트단에서와 마찬가지로 백엔드에서도 같은 validation 처리
		if ((!user.getPosition().equals("ADMINISTRATOR"))
				&& (!user.getPosition().equals("Department Head") && (!user.getPosition().equals("CEO")))) {
			if (outApprovalVO.getApproval_user_id() != user.getUserId()) {
				throw new AccessDeniedException("조회 권한이 없습니다.");
			}
		}

		model.addAttribute("vo", outApprovalVO);

		if (!isSessionValid(req)) {
			// 세션이 유효하지 않은 경우 로그인 페이지로 리다이렉트
			// return "redirect:/login/login_index.do?alert=needLogin";

			model.addAttribute("loginMessage", "로그인이 필요합니다.");
			return "login/login";
		}

		return viewName;
	}

	/**
	 * 결재건 삭제
	 * 
	 * @param approvalDocNo
	 * @return
	 */
	@RequestMapping(value = "/doDelete.do", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(@RequestParam(name = "approval_doc_no", required = true) String approvalDocNo)
			throws SQLException {

		String jsonString = "";

		ApprovalVO approvalVO = new ApprovalVO();

		int approvalDocNoInt = Integer.parseInt(approvalDocNo);

		approvalVO.setApproval_doc_no(approvalDocNoInt);

		int flag = approvalService.doDelete(approvalVO);

		String message = "";

		if (flag == 1) {
			message = approvalVO.getApproval_doc_no() + "번의 결재건이 삭제되었습니다.";
		} else {
			message = approvalVO.getApproval_doc_no() + "번의 결재건이 삭제되었습니다.";
		}

		jsonString = new Gson().toJson(new MessageVO(flag, message));

		return jsonString;
	}

	/**
	 * 결제건 수정
	 * 
	 * @param approval
	 * @return
	 */
	@PostMapping("/doUpdate.do")
	@ResponseBody
	public String doUpdate(HttpServletRequest req, ApprovalVO param) throws SQLException {

		String jsonString = "";

		int flag = approvalService.doUpdate(param);

		String message = "";

		if (1 == flag) {
			message = param.getApproval_doc_no() + "번의 결재 문서가 수정되었습니다.";
		} else {
			message = param.getApproval_doc_no() + "번의 결재 문서 수정이 실패하였습니다.";
		}

		jsonString = new Gson().toJson(new MessageVO(flag, message));

		return jsonString;
	}

	@GetMapping("/doApprove.do")
	@ResponseBody
	public String doApprove(HttpSession session, HttpServletRequest req, ApprovalVO param) throws SQLException {
		String jsonString = "";

		UserVO user = (UserVO) session.getAttribute("user");
		ApprovalVO inVO = new ApprovalVO();
		
		inVO.setApproval_user_id(param.getApproval_user_id());
		inVO.setApproval_doc_no(param.getApproval_doc_no());
		
		int sessionUserId = user.getUserId();
		
		int flag = approvalService.doApprove(inVO, sessionUserId);
		
		String message = "";

		if (1 == flag) {
			message = param.getApproval_doc_no() + "번의 문서가 결재되었습니다.";
		} else {
			message = param.getApproval_doc_no() + "번의 문서 결재에 실패하였습니다.";
		}

		jsonString = new Gson().toJson(new MessageVO(flag, message));

		return jsonString;
	}

	@GetMapping("/doReject.do")
	@ResponseBody
	public String doReject(HttpSession session,HttpServletRequest req, ApprovalVO param) throws SQLException {

		String jsonString = "";

		UserVO user = (UserVO) session.getAttribute("user");
		ApprovalVO inVO = new ApprovalVO();
		
		inVO.setApproval_user_id(param.getApproval_user_id());
		inVO.setApproval_doc_no(param.getApproval_doc_no());
		
		int sessionUserId = user.getUserId();
		
		System.out.println("param.getApproval_user_id(): " + param.getApproval_user_id());
		System.out.println("param.getApproval_doc_no(): " + param.getApproval_doc_no());
		System.out.println("sessionUserId: " + sessionUserId);
		
		int flag = approvalService.doReject(inVO, sessionUserId);

		String message = "";

		if (1 == flag) {
			message = param.getApproval_doc_no() + "번의 문서가 반려되었습니다.";
		} else {
			message = param.getApproval_doc_no() + "번의 문서가 반려에 실패하였습니다.";
		}

		jsonString = new Gson().toJson(new MessageVO(flag, message));

		return jsonString;
	}

//	/**
//	 * 결재 상태 변경
//	 * @param approvalDocNo 결재건 번호
//	 * @param status 새로운 결재 상태
//	 * @return 성공/실패 메시지
//	 */
//	@PatchMapping("/updateStatus/{approvalDocNo}")
//	@ResponseBody
//	public String updateApprovalStatus(
//	        @PathVariable String approvalDocNo, 
//	        @RequestParam String status) {
//	    int result = approvalService.updateApprovalStatus(approvalDocNo, status);
//	    if (result == 1) {
//	        return "결재 상태가 변경되었습니다.";
//	    } else {
//	        return "결재 상태 변경 실패. 결재건 번호를 확인해주세요.";
//	    }
//	}
//	

}
