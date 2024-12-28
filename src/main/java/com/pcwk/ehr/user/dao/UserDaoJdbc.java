package com.pcwk.ehr.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.user.domain.UserVO;

@Repository
public class UserDaoJdbc implements UserDao {

	final Logger log = LogManager.getLogger(UserDaoJdbc.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<UserVO> userMapper = new RowMapper<UserVO>() {

		@Override
		public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserVO outVO = new UserVO();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			outVO.setUserId(rs.getInt("user_id"));
			outVO.setDeptNo(rs.getInt("user_dept_no"));
			outVO.setSupUserId(rs.getInt("user_sup_id"));
			outVO.setName(rs.getString("user_name"));
			outVO.setPassword(rs.getString("user_password"));
			outVO.setPosition(rs.getString("user_position"));
			if(rs.getDate("user_birthday") != null) {
				outVO.setBirthday(formatter.format(rs.getDate("user_birthday")));
			}
			if(rs.getDate("user_hiredate") != null) {
				outVO.setHiredate(formatter.format(rs.getDate("user_hiredate")));
			}
			
			//outVO.setBirthday(rs.getString("user_birthday"));
			//outVO.setHiredate(rs.getString("user_hiredate"));
			outVO.setPhoneNo(rs.getString("user_phone_number"));
			outVO.setStatus(rs.getInt("user_status"));

			log.debug("outVO: " + outVO.toString());

			return outVO;
		}
	};

	public UserDaoJdbc() {

	}

	public String formateDate(String date) {

		return date.split(" ")[0];
	}

	@Override
	public int doDelete(UserVO inVO) throws SQLException {

		int flag = 0;
		StringBuilder sb = new StringBuilder(50);

		sb.append("DELETE FROM gw_user \n");
		sb.append("WHERE user_id = ?   \n");

		Object[] args = { inVO.getUserId() };

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

	@Override
	public int doUpdate(UserVO inVO) throws SQLException {

		int flag = 0;
		StringBuilder sb = new StringBuilder(150);
		sb.append("UPDATE gw_user                  \n");
		sb.append("   SET user_id = ?              \n");
		sb.append("       ,user_dept_no = ?        \n");
		sb.append("       ,user_sup_id = ?         \n");
		sb.append("       ,user_name = ?           \n");
		sb.append("       ,user_password = ?       \n");
		sb.append("       ,user_position = ?       \n");
		sb.append("       ,user_birthday = ?       \n");
		sb.append("       ,user_hiredate = SYSDATE \n");
		sb.append("       ,user_phone_number = ?   \n");
		sb.append("       ,user_status = ?         \n");
		sb.append(" WHERE user_id = ?              \n");

		Object[] args = { inVO.getUserId(), inVO.getDeptNo(), inVO.getSupUserId(), inVO.getName(), inVO.getPassword(),
				inVO.getPosition(), inVO.getBirthday(), inVO.getPhoneNo(), inVO.getStatus(), inVO.getUserId() };

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

	@Override
	public List<UserVO> doRetrieve(DTO dto) {

		List<UserVO> userList = new ArrayList<UserVO>();

		SearchVO inVO = (SearchVO) dto;

		StringBuilder search = new StringBuilder(100);

		if ("10".equals(inVO.getSearchDiv())) { // 회원id
			search.append(" WHERE user_id LIKE '%' || ? || '%' \n");
		} else if ("20".equals(inVO.getSearchDiv())) { // 이름
			search.append("	WHERE user_name LIKE '%' || ? || '%' \n");
		} else if ("30".equals(inVO.getSearchDiv())) { // 이메일
			search.append("	WHERE user_dept_no LIKE '%' || ? || '%' \n");
		}

		StringBuilder sb = new StringBuilder(500);
		sb.append("SELECT A.*, B.*                                                          \n");
		sb.append("  FROM (                                                                 \n");
		sb.append("		SELECT tt1.rnum no,                                                 \n");
		sb.append("			   tt1.user_id,                                                 \n");
		sb.append("			   tt1.user_dept_no,                                            \n");
		sb.append("			   tt1.user_sup_id,                                             \n");
		sb.append("            tt1.user_name,                                               \n");
		sb.append("            tt1.user_password,                                           \n");
		sb.append("			   tt1.user_position,    									    \n");
		sb.append("			   TO_CHAR(tt1.user_birthday, 'YYYY/MM/DD') user_birthday,      \n");
		sb.append("			   TO_CHAR(tt1.user_hiredate, 'YYYY/MM/DD') user_hiredate,      \n");
		sb.append("			   tt1.user_phone_number, 									    \n");
		sb.append("			   tt1.user_status     								  	        \n");
		sb.append("		    FROM(                                                           \n");
		sb.append("				SELECT rownum rnum, t1.*                                    \n");
		sb.append("				  FROM (                                                    \n");
		sb.append("						  SELECT *                                          \n");
		sb.append("						  FROM gw_user                                      \n");
		sb.append(
				"						  --WHERE 조건                                                                    \n");
		// --------------------------------------------------------------------------------------
		sb.append(search.toString());
		// --------------------------------------------------------------------------------------
		sb.append("						 ORDER BY user_id DESC                              \n");
		sb.append("				) t1                                                        \n");
		sb.append("				WHERE ROWNUM <= (? * (? - 1) + ?)                           \n");
		sb.append("		)tt1                                                                \n");
		sb.append("		WHERE rnum >= (? * (? - 1) + 1)                                     \n");
		sb.append("  )A                                                                     \n");
		sb.append("  CROSS JOIN (                                                           \n");
		sb.append("		  SELECT COUNT(*) totalCnt                                          \n");
		sb.append("			FROM gw_user                                                    \n");
		sb.append(
				"		 --WHERE 조건                                                                        			    \n");
		// --------------------------------------------------------------------------------------
		sb.append(search.toString());
		// --------------------------------------------------------------------------------------
		sb.append("  )B                                                                     \n");

		Object[] args = null;

		if (!"".equals(inVO.getSearchDiv()) || inVO.getSearchDiv().length() > 0) {
			args = new Object[7];
			args[0] = inVO.getSearchWord();
			args[1] = inVO.getPageSize();
			args[2] = inVO.getPageNo();
			args[3] = inVO.getPageSize();
			args[4] = inVO.getPageSize();
			args[5] = inVO.getPageNo();
			args[6] = inVO.getSearchWord();
		} else {
			args = new Object[5];
			args[0] = inVO.getPageSize();
			args[1] = inVO.getPageNo();
			args[2] = inVO.getPageSize();
			args[3] = inVO.getPageSize();
			args[4] = inVO.getPageNo();
		}

		RowMapper<UserVO> users = new RowMapper<UserVO>() {

			@Override
			public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserVO outVO = new UserVO();

				outVO.setNo(rs.getInt("no"));
				outVO.setUserId(rs.getInt("user_id"));
				outVO.setDeptNo(rs.getInt("user_dept_no"));
				outVO.setSupUserId(rs.getInt("user_sup_id"));
				outVO.setName(rs.getString("user_name"));
				outVO.setPassword(rs.getString("user_password"));
				outVO.setPosition(rs.getString("user_position"));
				outVO.setBirthday(rs.getString("user_birthday"));
				outVO.setHiredate(rs.getString("user_hiredate"));
				outVO.setPhoneNo(rs.getString("user_phone_number"));
				outVO.setStatus(rs.getInt("user_status"));
				outVO.setTotalCnt(rs.getInt("totalCnt"));

				log.debug("outVO: {}", outVO);

				return outVO;
			}
		};

		userList = jdbcTemplate.query(sb.toString(), users, args);

		return userList;
	}

	@Override
	public int getCount() throws SQLException {

		return 0;
	}

	@Override
	public void deleteAll() throws SQLException {

	}

	@Override
	public int doSave(UserVO inVO) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(200);

		sb.append("INSERT INTO gw_user ( \n");
		sb.append("    user_id,          \n");
		sb.append("    user_dept_no,     \n");
		sb.append("    user_sup_id,      \n");
		sb.append("    user_name,        \n");
		sb.append("    user_password,    \n");
		sb.append("    user_position,    \n");
		sb.append("    user_birthday,    \n");
		sb.append("    user_hiredate,    \n");
		sb.append("    user_phone_number,\n");
		sb.append("    user_status       \n");
		sb.append(") VALUES (            \n");
		sb.append("    ?,                \n");
		sb.append("    ?,                \n");
		sb.append("    ?,                \n");
		sb.append("    ?,                \n");
		sb.append("    ?,                \n");
		sb.append("    ?,                \n");
		sb.append("    ?,                \n");
		sb.append("    SYSDATE,          \n");
		sb.append("    ?,                \n");
		sb.append("    ?)                \n");

		Object[] args = { inVO.getUserId(), inVO.getDeptNo(), inVO.getSupUserId(), inVO.getName(), inVO.getPassword(),
				inVO.getPosition(), inVO.getBirthday(), inVO.getPhoneNo(), inVO.getStatus() };

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

	@Override
	public UserVO doSelectOne(UserVO inVO) throws SQLException, NullPointerException {
		UserVO outVO = null;

		StringBuilder sb = new StringBuilder(200);

		sb.append("SELECT                \n");
		sb.append("    user_id,          \n");
		sb.append("    user_dept_no,     \n");
		sb.append("    user_sup_id,      \n");
		sb.append("    user_name,        \n");
		sb.append("    user_password,    \n");
		sb.append("    user_position,    \n");
		sb.append("    user_birthday,    \n");
		sb.append("    user_hiredate,    \n");
		sb.append("    user_phone_number,\n");
		sb.append("    user_status       \n");
		sb.append("  FROM                \n");
		sb.append("    gw_user           \n");
		sb.append(" WHERE user_id = ?    \n");

		// 인자 받기
		Object[] args = { inVO.getUserId() };

		// 인자로 받은 값 출력해서 체크
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		// 인자들 넣어서 원하는 데이터 조회하기
		outVO = this.jdbcTemplate.queryForObject(sb.toString(), userMapper, args);

		// 조회 데이터가 없을 경우
		if (outVO == null) {
			throw new NullPointerException(inVO.getUserId() + "(아이디)를 확인하세요.");
		}

		return outVO;
	}

	@Override
	public List<UserVO> getAll() throws SQLException {

		return null;
	}

	@Override
	public int idCheck(UserVO inVO) throws SQLException {

		int count = 0;

		StringBuilder sb = new StringBuilder(50);
		sb.append("SELECT COUNT(*) cnt \n");
		sb.append("FROM gw_user        \n");
		sb.append("WHERE user_id = ?   \n");

		Object[] args = { inVO.getUserId() };
		count = jdbcTemplate.queryForObject(sb.toString(), Integer.class, args);

		return count;
	}

	@Override
	public int idPassCheck(UserVO inVO) throws SQLException {

		int count = 0;

		StringBuilder sb = new StringBuilder(50);
		sb.append("SELECT COUNT(*) cnt   \n");
		sb.append("FROM gw_user          \n");
		sb.append("WHERE user_id = ?     \n");
		sb.append("AND user_password = ? \n");

		Object[] args = { inVO.getUserId(), inVO.getPassword() };
		count = jdbcTemplate.queryForObject(sb.toString(), Integer.class, args);

		return count;
	}

	// count가 1일 때 존재하는 ID 있음 = 중복, 0일 때 존재하는 ID없음 = 중복 X
	@Override
	public int validateUserId(String userId) throws SQLException {

		int count = 0;

		StringBuilder sb = new StringBuilder(50);
		sb.append("SELECT COUNT(*)   \n");
		sb.append("FROM GW_USER      \n");
		sb.append("WHERE user_id = ? \n");

		Object[] args = { userId };
		count = jdbcTemplate.queryForObject(sb.toString(), Integer.class, args);

		return count;
	}

}