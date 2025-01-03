package com.pcwk.ehr.board.Dao;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.board.domain.BoardVO;
import com.pcwk.ehr.cmn.DTO;

public interface BoardDao {

	int doWriteBoard(BoardVO inVO, int boardDiv) throws SQLException;
	List<BoardVO> selectBoardList(int boardDiv) throws SQLException;
	// 게시글 상세 조회
	BoardVO getBoardByArticleNo(int articleNo);
	// 게시글 수정
	int updateBoard(BoardVO boardVO);
	// 게시글 삭제
	int deleteBoard(int articleNo);
	List<BoardVO> selectBoardList(int boardDiv, int pageNo, int pageSize) throws SQLException;
	int getBoardCount(int boardDiv) throws SQLException;
	List<BoardVO> doRetrieve(DTO dto, int boardDiv);
}
