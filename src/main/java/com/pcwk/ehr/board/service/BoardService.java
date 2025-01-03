package com.pcwk.ehr.board.service;

import com.pcwk.ehr.board.domain.BoardVO;
import com.pcwk.ehr.cmn.DTO;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {
    int doWriteFreeBoard(BoardVO inVO) throws SQLException;
    int doWriteDepartmentBoard(BoardVO inVO) throws SQLException;
    int doWriteAnnouncementBoard(BoardVO inVO) throws SQLException;
    List<BoardVO> selectMainBoardList()throws SQLException;
    List<BoardVO> selectFreeBoardList()throws SQLException;
    List<BoardVO> selectDevelopmentBoardList()throws SQLException;
    List<BoardVO> selectHRBoardList()throws SQLException;
    List<BoardVO> selectMarketingBoardList()throws SQLException;
    // 게시글 상세 조회
    BoardVO getBoardByArticleNo(int articleNo);
    // 게시글 수정
    int updateBoard(BoardVO boardVO);
    // 게시글 삭제
    int deleteBoard(int articleNo);
    List<BoardVO> selectBoardList(int boardDiv, int pageNo, int pageSize) throws SQLException;
    List<BoardVO> doRetrieveAnnouncementBoard(DTO dto);
    List<BoardVO> doRetrieveFreeBoard(DTO dto);
    List<BoardVO> doRetrieveDevelopmentBoard(DTO dto);
    List<BoardVO> doRetrieveHRBoard(DTO dto);
    List<BoardVO> doRetrieveMarketingBoard(DTO dto);
}
