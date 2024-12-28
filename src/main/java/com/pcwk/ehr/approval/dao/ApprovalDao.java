package com.pcwk.ehr.approval.dao;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.approval.domain.ApprovalVO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;

public interface ApprovalDao {
	
	//모든 결재문서 조회
	List<ApprovalVO> getAllApprovalDocs() throws SQLException;
  
    //새로운 결재 문서 등록
    int doSave(ApprovalVO approvalVO,UserVO inVO) throws SQLException;

    //결재 문서 삭제
    int doDelete(ApprovalVO approvalVO) throws SQLException;

    //결재 문서 수정
    int doUpdate(ApprovalVO inVO) throws SQLException;
    
    List<ApprovalVO> doRetrieve(DTO dto) throws SQLException;
   
    ApprovalVO doSelectOne(ApprovalVO inVO) throws SQLException, NullPointerException;
    
    int doApprove(ApprovalVO inVO) throws SQLException;
    
    int	doReject(ApprovalVO inVO) throws SQLException;
    
}
