package com.pcwk.ehr.board.service;

import com.pcwk.ehr.board.Dao.BoardDao;
import com.pcwk.ehr.board.domain.BoardVO;
import com.pcwk.ehr.cmn.DTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardDao boardDao;

    @Override
    public int doWriteFreeBoard(BoardVO inVO) throws SQLException {
        return boardDao.doWriteBoard(inVO,20);
    }

    @Override
    public int doWriteDepartmentBoard(BoardVO inVO) throws SQLException {
        // department 값을 inVO에서 가져와서 처리하도록 변경
        return boardDao.doWriteBoard(inVO, inVO.getArticle_board_div());  // department 값 전달
    }

    @Override
    public int doWriteAnnouncementBoard(BoardVO inVO) throws SQLException {
        return boardDao.doWriteBoard(inVO,10);
    }

    @Override
    public List<BoardVO> selectMainBoardList() throws SQLException {
        return boardDao.selectBoardList(10);
    }

    @Override
    public List<BoardVO> selectFreeBoardList() throws SQLException {
        return boardDao.selectBoardList(20);
    }

    @Override
    public List<BoardVO> selectDevelopmentBoardList() throws SQLException {
        return boardDao.selectBoardList(30);
    }

    @Override
    public List<BoardVO> selectHRBoardList() throws SQLException {
        return boardDao.selectBoardList(40);
    }

    @Override
    public List<BoardVO> selectMarketingBoardList() throws SQLException {
        return boardDao.selectBoardList(50);
    }

    @Override
    public BoardVO getBoardByArticleNo(int articleNo) {
        return boardDao.getBoardByArticleNo(articleNo);
    }

    @Override
    public int updateBoard(BoardVO boardVO) {
        return boardDao.updateBoard(boardVO);
    }

    @Override
    public int deleteBoard(int articleNo) {
        return boardDao.deleteBoard(articleNo);
    }

    @Override
    public List<BoardVO> selectBoardList(int boardDiv, int pageNo, int pageSize) throws SQLException {
        return boardDao.selectBoardList(boardDiv, pageNo, pageSize);
    }

    @Override
    public List<BoardVO> doRetrieveAnnouncementBoard(DTO dto) {
        return boardDao.doRetrieve(dto,10);
    }

    @Override
    public List<BoardVO> doRetrieveFreeBoard(DTO dto) {
        return boardDao.doRetrieve(dto,20);
    }

    @Override
    public List<BoardVO> doRetrieveDevelopmentBoard(DTO dto) {
        return boardDao.doRetrieve(dto,30);
    }

    @Override
    public List<BoardVO> doRetrieveHRBoard(DTO dto) {
        return boardDao.doRetrieve(dto,40);
    }

    @Override
    public List<BoardVO> doRetrieveMarketingBoard(DTO dto) {
        return boardDao.doRetrieve(dto,50);
    }


}
