package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;

public interface UserService {

	int doDelete(UserVO inVO) throws SQLException;
	
	int doUpdate(UserVO inVO) throws SQLException;

	List<UserVO> doRetrieve(DTO dto);
	
	int doSave(UserVO inVO) throws SQLException;
	
	UserVO doSelectOne(UserVO inVO) throws SQLException, NullPointerException;
	
	int validateUserId(String userId) throws SQLException;
}
