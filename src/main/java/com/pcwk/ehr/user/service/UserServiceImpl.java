package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.dao.UserDao;
import com.pcwk.ehr.user.domain.UserVO;

@Service
public class UserServiceImpl implements UserService {
	
	final Logger log = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserDao userDao;
	
	public UserServiceImpl() {

	}

	@Override
	public int doDelete(UserVO inVO) throws SQLException {	
		return userDao.doDelete(inVO);
	}

	@Override
	public int doUpdate(UserVO inVO) throws SQLException {		
		return userDao.doUpdate(inVO);
	}

	@Override
	public List<UserVO> doRetrieve(DTO dto) {		
		return userDao.doRetrieve(dto);
	}

	@Override
	public int doSave(UserVO inVO) throws SQLException {
		//userId 무조건 마지막 애 +1
		//직급
		if(inVO.getPosition() == null || inVO.getPosition().equals("")) {
			inVO.setPosition("Assistant Manager");
		}
		//상태값
		if(inVO.getStatus() == 0) {
			inVO.setStatus(10);
		}
		//부서번호 받으면 상급자 ID 그 부서의 부서장으로 할당
		switch(inVO.getDeptNo()) {
		case 1000:
			inVO.setSupUserId(1001);
			break;
			
		case 2000:
			inVO.setSupUserId(2001);
			break;
		case 3000:
			inVO.setSupUserId(3001);
			break;
		}
		return userDao.doSave(inVO);
	}

	@Override
	public UserVO doSelectOne(UserVO inVO) throws SQLException, NullPointerException {		
		return userDao.doSelectOne(inVO);
	}

	@Override
	public int validateUserId(String userId) throws SQLException {
		return userDao.validateUserId(userId);
	}
}