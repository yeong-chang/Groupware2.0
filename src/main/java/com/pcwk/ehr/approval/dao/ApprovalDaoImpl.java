package com.pcwk.ehr.approval.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.pcwk.ehr.approval.domain.ApprovalVO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.user.domain.UserVO;

@Repository
public class ApprovalDaoImpl implements ApprovalDao {
	final Logger log = LogManager.getLogger(ApprovalDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<ApprovalVO> eApprovalMapper = new RowMapper<ApprovalVO>() {
		
		@Override
		public ApprovalVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ApprovalVO approvalVO = new ApprovalVO();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			approvalVO.setApproval_doc_no(rs.getInt("approval_doc_no"));
            approvalVO.setApproval_user_id(rs.getInt("approval_user_id"));
		    approvalVO.setApproval_doc_title(rs.getString("approval_doc_title"));
		    if (rs.getDate("approval_doc_reg_date") != null) {
		        approvalVO.setApproval_doc_reg_date(formatter.format(rs.getDate("approval_doc_reg_date")));
		    }
		    if (rs.getDate("approval_doc_approved_date") != null) {
		        approvalVO.setApproval_doc_approved_date(formatter.format(rs.getDate("approval_doc_approved_date")));
		    }
		    if (rs.getDate("approval_doc_closing_date") != null) {
		        approvalVO.setApproval_doc_closing_date(formatter.format(rs.getDate("approval_doc_closing_date")));
		    }
		    approvalVO.setApproval_status(rs.getInt("approval_status"));
		    approvalVO.setApproval_contents(rs.getString("approval_contents"));

			return approvalVO;
		}

	};
	
	//결재문서 조회
	@Override
	public List<ApprovalVO> getAllApprovalDocs() {
		List<ApprovalVO> approvalList = new ArrayList<ApprovalVO>();
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT                            \n"); 
		sb.append("     approval_doc_no,              \n");
		sb.append("     approval_user_id,             \n");
		sb.append("     approval_doc_title,           \n");
		sb.append("     approval_doc_reg_date,        \n");
		sb.append("     approval_doc_approved_date,   \n");
		sb.append("     approval_doc_closing_date,    \n");
		sb.append("     approval_status,              \n");
		sb.append("     approval_contents             \n");
		sb.append(" FROM                              \n");
		sb.append("     e_approval                    \n");
		
		approvalList = this.jdbcTemplate.query(sb.toString(), eApprovalMapper);
		
		return approvalList;
	}

	//결재 등록
	@Override
	public int doSave(ApprovalVO approvalVO,UserVO outVO) {
		int flag = 0;
		
		StringBuilder sb = new StringBuilder(200);
		sb.append(" INSERT INTO e_approval (         \n"); 
		sb.append("     approval_doc_no,             \n");
		sb.append("     approval_user_id,            \n");
		sb.append("     approval_doc_title,          \n");
		sb.append("     approval_doc_reg_date,       \n");
		sb.append("     approval_doc_approved_date,  \n");
		sb.append("     approval_doc_closing_date,   \n");
		sb.append("     approval_status,             \n");
		sb.append("     approval_contents            \n");
		sb.append(" ) VALUES (   ?,                  \n");
		sb.append("              ?,                  \n");
		sb.append("              ?,                  \n");
		sb.append("              SYSDATE,            \n");
		sb.append("              ?,                  \n");
		sb.append("              ?,                  \n");
		sb.append("              ?,                  \n");
		sb.append("              ? )                 \n");
		

		//쿼리 실행에 필요한 인자 담기
		Object[] args = { approvalVO.getApproval_doc_no(),approvalVO.getApproval_user_id(),approvalVO.getApproval_doc_title()
				,approvalVO.getApproval_doc_approved_date()
				,approvalVO.getApproval_doc_closing_date(),approvalVO.getApproval_status(), approvalVO.getApproval_contents()
		};
		
		//쿼리문 실행 (쿼리문과 쿼리문에 필요한 인자를 조립) 
		flag = this.jdbcTemplate.update(sb.toString(), args);
		
		//실행 결과를 성공/실패 결과로 뱉어주기
		return flag;
	}

	//결재문서 삭제
	@Override
	public int doDelete(ApprovalVO approvalVO) {

		int flag = 0;
		
		StringBuilder sb = new StringBuilder(100);
		
		sb.append(" DELETE FROM e_approval      \n");
		sb.append(" WHERE approval_doc_no = ?   \n");
		
		Object [] args = {approvalVO.getApproval_doc_no()};
		
		flag = this.jdbcTemplate.update(sb.toString(), args);
		
		return flag;
	}

	//결재 상태 업데이트
	@Override
	public int doUpdate(ApprovalVO inVO) {
		int flag = 0;
		
		StringBuilder sb = new StringBuilder(150);
		sb.append(" UPDATE e_approval                     \n");
		sb.append(" SET  approval_doc_no             = ?  \n");
		sb.append("      ,approval_user_id           = ?  \n");
		sb.append("      ,approval_doc_title         = ?  \n");
		sb.append("      ,approval_doc_reg_date      = ?  \n");
		sb.append("      ,approval_doc_approved_date = ?  \n");
		sb.append("      ,approval_doc_closing_date  = ?  \n");
		sb.append("      ,approval_status            = ?  \n");
		sb.append("      ,approval_contents          = ?  \n");
		sb.append("                                       \n");
		sb.append(" WHERE                                 \n");
		sb.append("      approval_doc_no = ?              \n");

		Object [] args = {inVO.getApproval_doc_no(), inVO.getApproval_user_id(), inVO.getApproval_doc_title()
				,inVO.getApproval_doc_reg_date(), inVO.getApproval_doc_approved_date(), inVO.getApproval_doc_closing_date()
				,inVO.getApproval_status(), inVO.getApproval_contents(),inVO.getApproval_doc_no()};
		
		flag = this.jdbcTemplate.update(sb.toString(),args);
		
		return flag;
	}

	@Override
	public List<ApprovalVO> doRetrieve(DTO dto) {
		
		List<ApprovalVO> approvalDocList = new ArrayList<ApprovalVO>();
		
		SearchVO approvalVO = (SearchVO) dto;
		
		StringBuilder search = new StringBuilder(100);
		
		if("10".equals(approvalVO.getSearchDiv())) { //10일 경우, 상신자ID로 조회
			search.append(" WHERE approval_user_id LIKE '%' || ? || '%' \n");
		} else if("20".equals(approvalVO.getSearchDiv())){ //20일 경우, 제목으로 조회
			search.append("	WHERE approval_doc_title LIKE '%' || ? || '%' \n");
		} else if("30".equals(approvalVO.getSearchDiv())) { //30일 경우, 상태값으로 조회
			search.append("	WHERE approval_status LIKE '%' || ? || '%' \n");
		}
		
		StringBuilder sb = new StringBuilder(500);
		sb.append("SELECT A.*, B.*                                                                                 \n");
		sb.append("  FROM (                                                                                        \n");
		sb.append("		  SELECT tt1.rnum no,                                                                      \n");
		sb.append("			     tt1.approval_doc_no,                                                              \n");
		sb.append("			     tt1.approval_user_id,                                                             \n");
		sb.append("			     tt1.approval_doc_title,                                                           \n");
		sb.append("			     TO_CHAR(tt1.approval_doc_reg_date, 'YYYY/MM/DD') approval_doc_reg_date,           \n");
		sb.append("              TO_CHAR(tt1.approval_doc_approved_date, 'YYYY/MM/DD') approval_doc_approved_date, \n");
		sb.append("              TO_CHAR(tt1.approval_doc_closing_date, 'YYYY/MM/DD') approval_doc_closing_date,   \n");
		sb.append("			     tt1.approval_status,    												           \n");
		sb.append("			     tt1.approval_contents    									                       \n");
		sb.append("		    FROM(                                                                                  \n");
		sb.append("				SELECT rownum rnum, t1.*                                                           \n");
		sb.append("				  FROM (                                                                           \n");
		sb.append("						  SELECT *                                                                 \n");
		sb.append("						  FROM e_approval                                                          \n");
		sb.append("						  --WHERE 조건                                                                                                             \n");
		//--------------------------------------------------------------------------------------
		sb.append(search.toString());
		//--------------------------------------------------------------------------------------
		sb.append("						 ORDER BY approval_doc_no DESC                                             \n");
		sb.append("				) t1                                                                               \n");
		sb.append("				WHERE ROWNUM <= (? * (? - 1) + ?)                                                  \n");
		sb.append("		)tt1                                                                                       \n");
		sb.append("		WHERE rnum >= (? * (? - 1) + 1)                                                            \n");
		sb.append("  )A                                                                                            \n");
		sb.append("  CROSS JOIN (                                                                                  \n");
		sb.append("		  SELECT COUNT(*) totalCnt                                                                 \n");
		sb.append("			FROM e_approval                                                                        \n");
		sb.append("		 --WHERE 조건                                                                                                                                          \n");
		//--------------------------------------------------------------------------------------
		sb.append(search.toString());
		//--------------------------------------------------------------------------------------
		sb.append("  )B                                                                                            \n");
		
		Object [] args = null;
		
		//검색값이 있을 때
		if( !"".equals(approvalVO.getSearchDiv()) || approvalVO.getSearchDiv().length()>0) {
			args = new Object[7];
			args[0] = approvalVO.getSearchWord();
			args[1] = approvalVO.getPageSize();
			args[2] = approvalVO.getPageNo();
			args[3] = approvalVO.getPageSize();
			args[4] = approvalVO.getPageSize();
			args[5] = approvalVO.getPageNo();
			args[6] = approvalVO.getSearchWord();
		}else { //검색값이 없을 때
			args = new Object [5];
			args[0] = approvalVO.getPageSize();
			args[1] = approvalVO.getPageNo();
			args[2] = approvalVO.getPageSize();
			args[3] = approvalVO.getPageSize();
			args[4] = approvalVO.getPageNo();
		}
		
		
		RowMapper<ApprovalVO> approvalDocs = new RowMapper<ApprovalVO>() {
			@Override
			public ApprovalVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ApprovalVO approvalVO = new ApprovalVO();

				approvalVO.setNo(rs.getInt("no")); //ApprovalVO를 DTO랑 상속해야 setNo 메소드를 쓸 수 있음
				approvalVO.setApproval_doc_no(rs.getInt("approval_doc_no"));
	            approvalVO.setApproval_user_id(rs.getInt("approval_user_id"));
			    approvalVO.setApproval_doc_title(rs.getString("approval_doc_title"));
			    approvalVO.setApproval_doc_reg_date(rs.getString("approval_doc_reg_date"));
				approvalVO.setApproval_doc_approved_date(rs.getString("approval_doc_approved_date"));
				approvalVO.setApproval_doc_closing_date(rs.getString("approval_doc_closing_date"));
			    approvalVO.setApproval_status(rs.getInt("approval_status"));
			    approvalVO.setApproval_contents(rs.getString("approval_contents"));
			    approvalVO.setTotalCnt(rs.getInt("totalCnt"));
			
				return approvalVO;
			}
		};
		
		approvalDocList = this.jdbcTemplate.query(sb.toString(),approvalDocs, args);
		
		return approvalDocList;
	}

	@Override
	public ApprovalVO doSelectOne(ApprovalVO inVO) throws SQLException, NullPointerException {
		
		ApprovalVO outVO = null;
		
		StringBuilder sb = new StringBuilder(200);
		
		sb.append("SELECT                          \n");
		sb.append("    approval_doc_no,            \n");
		sb.append("    approval_user_id,           \n");
		sb.append("    approval_doc_title,         \n");
		sb.append("    approval_doc_reg_date,      \n");
		sb.append("    approval_doc_approved_date, \n");
		sb.append("    approval_doc_closing_date,  \n");
		sb.append("    approval_status,            \n");
		sb.append("    approval_contents           \n");
		sb.append("FROM e_approval                 \n");
		sb.append("WHERE approval_doc_no = ?       \n");		
		
		Object [] args = {inVO.getApproval_doc_no()};
		
		outVO = this.jdbcTemplate.queryForObject(sb.toString(), eApprovalMapper, args);
		
		if(outVO == null) {
			throw new NullPointerException(inVO.getApproval_doc_no() + "번 문서에 문제가 생겼습니다. 다시 시도해주세요.");
		}
		
		return outVO;
	}

	@Override
	public int doApprove(ApprovalVO inVO) throws SQLException {
		
		int flag = 0;
		
		StringBuilder sb = new StringBuilder(50);
		sb.append("UPDATE e_approval                                         \n");
	    sb.append("SET approval_status =                                     \n");
	    sb.append("    CASE                                                  \n");
	    sb.append("        WHEN approval_status + 5 = 40 THEN 10             \n");
	    sb.append("        ELSE approval_status + 5                          \n");
	    sb.append("    END,                                                  \n");
	    sb.append("approval_doc_approved_date =                              \n");
	    sb.append("    CASE                                                  \n");
	    sb.append("        WHEN approval_status + 5 = 40 THEN SYSDATE        \n");
	    sb.append("        ELSE approval_doc_approved_date                   \n");
	    sb.append("    END                                                   \n");
	    sb.append("WHERE approval_doc_no = ?                                 \n");

		Object [] args = { inVO.getApproval_doc_no()};
		
		flag = this.jdbcTemplate.update(sb.toString(), args);
		
		
		return flag;
	}

	@Override
	public int doReject(ApprovalVO inVO) throws SQLException {
		
		int flag = 0;
		
		StringBuilder sb = new StringBuilder(50);
		sb.append("UPDATE e_approval         \n");
		sb.append("SET approval_status = 20  \n");
		sb.append("WHERE approval_doc_no = ? \n");
		
		Object [] args = { inVO.getApproval_doc_no()};
		
		flag = this.jdbcTemplate.update(sb.toString(), args);
		
		return flag;
	}
}
