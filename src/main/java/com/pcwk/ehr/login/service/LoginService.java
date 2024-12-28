package com.pcwk.ehr.login.service;

import java.sql.SQLException;

import com.pcwk.ehr.user.domain.UserVO;

public interface LoginService {

	int idPassCheck(UserVO inVO) throws SQLException;
	
	UserVO doSelectOne(UserVO inVO) throws NullPointerException, SQLException;
}
