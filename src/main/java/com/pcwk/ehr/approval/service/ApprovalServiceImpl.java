package com.pcwk.ehr.approval.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.approval.dao.ApprovalDao;
import com.pcwk.ehr.approval.domain.ApprovalVO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;

@Service
public class ApprovalServiceImpl implements ApprovalService {
	final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	ApprovalDao approvalDao; 
	
	
	public ApprovalServiceImpl() {
	
	}
	
	//결재일 초기 널, 상태 초기 30(처리중), 결재완료시간 보류, 결재건 번호
	/**
	 *모든 결재건 등록 
	 * @throws SQLException 
	 */
	@Override
	public int doSave(ApprovalVO approvalVO, UserVO outVO) throws SQLException {
		
		approvalVO.setApproval_user_id(outVO.getUserId());
		
		if(approvalVO.getApproval_status() == 0) approvalVO.setApproval_status(30);
		return approvalDao.doSave(approvalVO,outVO);
	}
	
	@Override
	public int doDelete(ApprovalVO approvalVO) throws SQLException {			
		return approvalDao.doDelete(approvalVO);
	}

	@Override
	public int doUpdate(ApprovalVO inVO) throws SQLException {
		return approvalDao.doUpdate( inVO);
	}

	@Override
	public ApprovalVO doSelectOne(ApprovalVO inVO) throws NullPointerException, SQLException {
		return approvalDao.doSelectOne(inVO);
	}

	@Override
	public List<ApprovalVO> doRetrieve(DTO dto) throws SQLException { 
		return approvalDao.doRetrieve(dto);
	}

	@Override
	public int doApprove(ApprovalVO inVO, int sessionUserId) throws SQLException {		
		
		int flag = 2;
		int count = 0;
		if(checkValidity(inVO, sessionUserId)) {
			count = approvalDao.doApprove(inVO);
			if(count == 1) {
				flag = 1;
			} else {
				flag = 2;
			}	
		}
		
		System.out.println("checkValidity: " + checkValidity(inVO, sessionUserId));
		System.out.println("count: " + count);
		System.out.println("flag: " + flag);

		return flag;
	}
	
	public boolean checkValidity(ApprovalVO inVO, int sessionUserId) {
		
		boolean isPossible = false;
		
		if((inVO.getApproval_user_id()>1000 && inVO.getApproval_user_id()<2000) && sessionUserId == 1001 ||
		   (inVO.getApproval_user_id()>1000 && inVO.getApproval_user_id()<2000) && sessionUserId == 9000 ||
		   (inVO.getApproval_user_id()>1000 && inVO.getApproval_user_id()<2000) && sessionUserId == 9999) {
			isPossible = true;
		}else if(
		   (inVO.getApproval_user_id()>2000 && inVO.getApproval_user_id()<3000) && sessionUserId == 2001 ||
		   (inVO.getApproval_user_id()>2000 && inVO.getApproval_user_id()<3000) && sessionUserId == 9000 ||
		   (inVO.getApproval_user_id()>2000 && inVO.getApproval_user_id()<3000) && sessionUserId == 9999) {
			isPossible = true;
		}else if(
		   (inVO.getApproval_user_id()>3000 && inVO.getApproval_user_id()<4000) && sessionUserId == 1001 ||
		   (inVO.getApproval_user_id()>3000 && inVO.getApproval_user_id()<4000) && sessionUserId == 9000 ||
		   (inVO.getApproval_user_id()>3000 && inVO.getApproval_user_id()<4000) && sessionUserId == 9999) {
			isPossible = true;
		} else {
			isPossible = false;
		}		

		return isPossible;
	}

	@Override
	public int doReject(ApprovalVO inVO, int sessionUserId) throws SQLException {		
		
		int flag = 2;
		int count = 0;
		
		if(checkValidity(inVO, sessionUserId)) {
			count = approvalDao.doReject(inVO);
			if(count == 1) {
				flag = 1;
			} else {
				flag = 2;
			}
		}
		
		System.out.println("checkValidity: " + checkValidity(inVO, sessionUserId));
		System.out.println("count: " + count);
		System.out.println("flag: " + flag);
		return count;
	}
}
