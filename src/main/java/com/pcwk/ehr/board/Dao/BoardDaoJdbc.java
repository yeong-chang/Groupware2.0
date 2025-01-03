package com.pcwk.ehr.board.Dao;

import com.pcwk.ehr.approval.domain.ApprovalVO;
import com.pcwk.ehr.board.domain.BoardVO;
import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.user.domain.UserVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardDaoJdbc implements BoardDao {

    final Logger log = LogManager.getLogger(BoardDaoJdbc.class);

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public int doWriteBoard(BoardVO inVO, int boardDiv) throws SQLException {
		StringBuilder sb = new StringBuilder(500);

		sb.append("INSERT INTO BOARD (article_no, article_user_id, article_title, article_contents, ")
				.append("article_board_div, article_read_cnt, article_reg_date, article_mod_date) ")
				.append("VALUES (board_article_no_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE, SYSDATE)");

		Object[] params = new Object[]{
				inVO.getArticle_user_id(),
				inVO.getArticle_title(),
				inVO.getArticle_contents(),
				boardDiv
		};

		return jdbcTemplate.update(sb.toString(), params);
	}
	@Override
	public List<BoardVO> selectBoardList(int boardDiv) throws SQLException {
		// SQL 쿼리 작성
		StringBuilder sb = new StringBuilder(500);
		sb.append("SELECT b.article_no, b.article_user_id, b.article_title, b.article_contents, ")
				.append("b.article_board_div, b.article_read_cnt, b.article_reg_date, b.article_mod_date, ")
				.append("u.user_name AS user_name ")
				.append("FROM BOARD b ")
				.append("JOIN gw_user u ON b.article_user_id = u.user_id ")
				.append("WHERE b.article_board_div = ? ")
				.append("ORDER BY b.article_reg_date DESC");

		// JDBC Template의 query 메서드를 사용하여 결과 조회
		return jdbcTemplate.query(sb.toString(), new Object[]{boardDiv}, new RowMapper<BoardVO>() {
			@Override
			public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				// ResultSet에서 데이터를 추출하여 BoardVO 객체에 매핑
				BoardVO boardVO = new BoardVO();
				boardVO.setArticle_no(rs.getInt("article_no"));
				boardVO.setArticle_user_id(rs.getString("article_user_id"));
				boardVO.setArticle_title(rs.getString("article_title"));
				boardVO.setArticle_contents(rs.getString("article_contents"));
				boardVO.setArticle_board_div(rs.getInt("article_board_div"));
				boardVO.setArticle_read_cnt(rs.getInt("article_read_cnt"));
				boardVO.setArticle_reg_date(rs.getString("article_reg_date"));
				boardVO.setArticle_mod_date(rs.getString("article_mod_date"));

				// UserVO 객체를 생성하고, UserVO에 user_name 매핑
				UserVO userVO = new UserVO();
				userVO.setUserId(rs.getInt("article_user_id")); // article_user_id를 사용하여 UserVO 객체를 설정
				userVO.setName(rs.getString("user_name")); // user_name을 UserVO의 name에 매핑

				// BoardVO에 UserVO 객체 설정
				boardVO.setUserVO(userVO);

				return boardVO;
			}
		});
	}
	@Override
	public BoardVO getBoardByArticleNo(int articleNo) {
		String sql = "SELECT * FROM board WHERE article_no = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{articleNo}, (rs, rowNum) -> {
			BoardVO boardVO = new BoardVO();
			boardVO.setArticle_no(rs.getInt("article_no"));
			boardVO.setArticle_title(rs.getString("article_title"));
			boardVO.setArticle_contents(rs.getString("article_contents"));
			boardVO.setArticle_user_id(rs.getString("article_user_id"));
			boardVO.setArticle_reg_date(rs.getString("article_reg_date")); // article_reg_date는 String으로 처리
			boardVO.setArticle_mod_date(rs.getString("article_mod_date")); // 추가된 부분: 수정일
			return boardVO;
		});
	}
	@Override
	public int updateBoard(BoardVO boardVO) {
		String sql = "UPDATE board SET article_title = ?, article_contents = ? WHERE article_no = ?";
		return jdbcTemplate.update(sql, boardVO.getArticle_title(), boardVO.getArticle_contents(), boardVO.getArticle_no());
	}
	@Override
	public int deleteBoard(int articleNo) {
		String sql = "DELETE FROM board WHERE article_no = ?";
		return jdbcTemplate.update(sql, articleNo);
	}
	@Override
	public List<BoardVO> selectBoardList(int boardDiv, int pageNo, int pageSize) throws SQLException {
		// 페이징 처리: OFFSET, LIMIT 사용
		int offset = (pageNo - 1) * pageSize;

		StringBuilder sb = new StringBuilder(500);
		sb.append("SELECT b.article_no, b.article_user_id, b.article_title, b.article_contents, ")
				.append("b.article_board_div, b.article_read_cnt, b.article_reg_date, b.article_mod_date, ")
				.append("u.user_name AS user_name ")
				.append("FROM BOARD b ")
				.append("JOIN gw_user u ON b.article_user_id = u.user_id ")
				.append("WHERE b.article_board_div = ? ")
				.append("ORDER BY b.article_reg_date DESC ")
				.append("LIMIT ? OFFSET ?");

		// JDBC Template의 query 메서드를 사용하여 결과 조회
		return jdbcTemplate.query(sb.toString(), new Object[]{boardDiv, pageSize, offset}, new RowMapper<BoardVO>() {
			@Override
			public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				// ResultSet에서 데이터를 추출하여 BoardVO 객체에 매핑
				BoardVO boardVO = new BoardVO();
				boardVO.setArticle_no(rs.getInt("article_no"));
				boardVO.setArticle_user_id(rs.getString("article_user_id"));
				boardVO.setArticle_title(rs.getString("article_title"));
				boardVO.setArticle_contents(rs.getString("article_contents"));
				boardVO.setArticle_board_div(rs.getInt("article_board_div"));
				boardVO.setArticle_read_cnt(rs.getInt("article_read_cnt"));
				boardVO.setArticle_reg_date(rs.getString("article_reg_date"));
				boardVO.setArticle_mod_date(rs.getString("article_mod_date"));

				// UserVO 객체를 생성하고, UserVO에 user_name 매핑
				UserVO userVO = new UserVO();
				userVO.setUserId(rs.getInt("article_user_id")); // article_user_id를 사용하여 UserVO 객체를 설정
				userVO.setName(rs.getString("user_name")); // user_name을 UserVO의 name에 매핑

				// BoardVO에 UserVO 객체 설정
				boardVO.setUserVO(userVO);

				return boardVO;
			}
		});
	}

	@Override
	public int getBoardCount(int boardDiv) throws SQLException {
		// 게시판의 전체 게시글 개수 조회
		String sql = "SELECT COUNT(*) FROM board WHERE article_board_div = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{boardDiv}, Integer.class);
	}

	@Override
	public List<BoardVO> doRetrieve(DTO dto, int boardDiv) {

		List<BoardVO> boardDocList = new ArrayList<BoardVO>();

		SearchVO BoardVO = (SearchVO) dto;

		StringBuilder search = new StringBuilder(100);

		if("10".equals(BoardVO.getSearchDiv())) { //10일 경우, 상신자ID로 조회
			search.append(" AND article_user_id IN (SELECT user_id FROM GW_USER WHERE article_user_id LIKE '%' || ? || '%') \n");
		} else if("20".equals(BoardVO.getSearchDiv())){ //20일 경우, 제목으로 조회
			search.append("	AND article_user_id IN (SELECT user_id FROM GW_USER WHERE article_title LIKE '%' || ? || '%') \n");
		} else if("30".equals(BoardVO.getSearchDiv())) { //30일 경우, 부서로 조회
			search.append("	AND article_user_id IN (SELECT user_id FROM GW_USER WHERE user_name LIKE '%' || ? || '%') \n");
		}
		System.out.println(BoardVO.getSearchDiv());
		StringBuilder sb = new StringBuilder(500);
		sb.append("SELECT A.*, B.*, u.user_name AS user_name                                                            \n");
		sb.append("  FROM (                                                                                              \n");
		sb.append("      SELECT tt1.rnum no,                                                                              \n");
		sb.append("             tt1.article_no,                                                                          \n");
		sb.append("             tt1.article_user_id,                                                                     \n");
		sb.append("             tt1.article_title,                                                                       \n");
		sb.append("             tt1.article_read_cnt,                                                                    \n");
		sb.append("             tt1.article_reg_date,                                                                    \n");
		sb.append("             tt1.article_mod_date,                                                                    \n");
		sb.append("             tt1.article_board_div,                                                                   \n");
		sb.append("             tt1.article_contents                                                                     \n");
		sb.append("        FROM(                                                                                          \n");
		sb.append("            SELECT rownum rnum, t1.*                                                                   \n");
		sb.append("              FROM (                                                                                   \n");
		sb.append("                  SELECT *                                                                             \n");
		sb.append("                  FROM BOARD WHERE article_board_div = ?  				                                  \n");
		sb.append("                  --WHERE 조건                                                                        \n");
		sb.append(search.toString()); // 여기에 검색 조건 추가
		sb.append("                  ORDER BY article_no DESC                                                           \n");
		sb.append("            ) t1                                                                                     \n");
		sb.append("            WHERE ROWNUM <= (? * (? - 1) + ?)                                                          \n");
		sb.append("        ) tt1                                                                                         \n");
		sb.append("        WHERE rnum >= (? * (? - 1) + 1)                                                                \n");
		sb.append("  ) A                                                                                                  \n");
		sb.append("  CROSS JOIN (                                                                                         \n");
		sb.append("      SELECT COUNT(*) totalCnt                                                                        \n");
		sb.append("      FROM board                                                                                     \n");
		sb.append("      WHERE article_board_div = ?                                                                   \n");
		sb.append("      --WHERE 조건                                                                                   \n");
		sb.append(search.toString()); // 여기에 검색 조건 추가
		sb.append("  ) B                                                                                                  \n");
		sb.append("  LEFT JOIN gw_user u ON A.article_user_id = u.user_id                                               \n"); // 여기에 조인 추가


		Object [] args = null;

		//검색값이 있을 때
		if( !"".equals(BoardVO.getSearchDiv()) || BoardVO.getSearchDiv().length()>0) {
			args = new Object[9];
			args[0] = boardDiv;
			args[1] = BoardVO.getSearchWord();
			args[2] = BoardVO.getPageSize();
			args[3] = BoardVO.getPageNo();
			args[4] = BoardVO.getPageSize();
			args[5] = BoardVO.getPageSize();
			args[6] = BoardVO.getPageNo();
			args[7] = boardDiv;
			args[8] = BoardVO.getSearchWord();
			log.debug(args);
		}else { //검색값이 없을 때
			args = new Object [7];
			args[0] = boardDiv;
			args[1] = BoardVO.getPageSize();
			args[2] = BoardVO.getPageNo();
			args[3] = BoardVO.getPageSize();
			args[4] = BoardVO.getPageSize();
			args[5] = BoardVO.getPageNo();
			args[6] = boardDiv;
			log.debug(args);
		}


		RowMapper<BoardVO> boardDocs = new RowMapper<BoardVO>() {
			@Override
			public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BoardVO boardVO = new BoardVO();

				boardVO.setNo(rs.getInt("no")); //ApprovalVO를 DTO랑 상속해야 setNo 메소드를 쓸 수 있음
				boardVO.setArticle_no(rs.getInt("article_no"));
				boardVO.setArticle_user_id(rs.getString("article_user_id"));
				boardVO.setArticle_title(rs.getString("article_title"));
				boardVO.setArticle_contents(rs.getString("article_contents"));
				boardVO.setArticle_board_div(rs.getInt("article_board_div"));
				boardVO.setArticle_read_cnt(rs.getInt("article_read_cnt"));
				boardVO.setArticle_reg_date(rs.getString("article_reg_date"));
				boardVO.setArticle_mod_date(rs.getString("article_mod_date"));
				boardVO.setTotalCnt(rs.getInt("totalCnt"));
// UserVO 객체를 생성하고, UserVO에 user_name 매핑
				UserVO userVO = new UserVO();
				userVO.setUserId(rs.getInt("article_user_id")); // article_user_id를 사용하여 UserVO 객체를 설정
				userVO.setName(rs.getString("user_name")); // user_name을 UserVO의 name에 매핑

				// BoardVO에 UserVO 객체 설정
				boardVO.setUserVO(userVO);
				return boardVO;
			}
		};

		boardDocList = this.jdbcTemplate.query(sb.toString(),boardDocs, args);

		return boardDocList;
	}


}
