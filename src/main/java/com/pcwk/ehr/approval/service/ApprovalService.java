package com.pcwk.ehr.approval.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.approval.domain.ApprovalVO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;


public interface ApprovalService {

	
	List<ApprovalVO> doRetrieve(DTO dto) throws SQLException;
	/**
	 * 결재건 등록
	 * @param approvalVO
	 * @return
	 */
	int doSave(ApprovalVO approvalVO, UserVO inVO) throws SQLException;
	
	/**
	 * 결제건 삭제
	 * @param approvalDocNo
	 * @return
	 */
	int doDelete(ApprovalVO approvalVO) throws SQLException;
	
	/**
	 * 결제건 수정
	 * @param approval
	 * @return
	 */
	int doUpdate(ApprovalVO approvalVO) throws SQLException;
	
	ApprovalVO doSelectOne(ApprovalVO inVO) throws SQLException;
	
	int doApprove(ApprovalVO inVO, int sessionUserId) throws SQLException;
    
    int	doReject(ApprovalVO inVO, int sessionUserId) throws SQLException;
	
}
