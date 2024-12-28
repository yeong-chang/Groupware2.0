package com.pcwk.ehr.user.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;

public interface UserDao {

	int doDelete(UserVO inVO) throws SQLException;
	
	int doUpdate(UserVO inVO) throws SQLException;
	
	List<UserVO> doRetrieve(DTO dto);
	
	List<UserVO> getAll() throws SQLException;
	
	int getCount() throws SQLException;
	
	void deleteAll() throws SQLException;
	
	int doSave(UserVO inVO) throws SQLException;
	
	UserVO doSelectOne(UserVO inVO) throws SQLException, NullPointerException;
	
	int idCheck(UserVO inVO) throws SQLException;
	
	int idPassCheck(UserVO inVO) throws SQLException;
	
	int validateUserId(String userId) throws SQLException;
}
