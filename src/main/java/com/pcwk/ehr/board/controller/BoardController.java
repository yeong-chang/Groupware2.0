package com.pcwk.ehr.board.controller;

import com.pcwk.ehr.board.Dao.BoardDaoJdbc;
import com.pcwk.ehr.board.domain.BoardVO;
import com.pcwk.ehr.board.service.BoardService;
import com.pcwk.ehr.cmn.SearchVO;
import com.pcwk.ehr.cmn.StringUtil;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    private boolean isSessionValid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }

    @GetMapping("/announcement.do")
    public String announcementBoardPage(Model model,HttpServletRequest req) throws Exception {
    	BoardVO boardVO = new BoardVO();
        UserVO userVO = new UserVO();
        String view = "board/announcement/announcementBoardList";
        SearchVO search = new SearchVO();
        /*try {
        	 List<BoardVO> boardList = boardService.doRetrieve(search);
             model.addAttribute("boardList", boardList);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		/*model.addAttribute("vo", boardVO);*/



        String pageNoString = StringUtil.nvl(req.getParameter("pageNo"), "1");
        String pageSizeString = StringUtil.nvl(req.getParameter("pageSize"), "10");

        int pageNo = Integer.parseInt(pageNoString);
        int pageSize = Integer.parseInt(pageSizeString);

        //데이터가 null이면, ""
        String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
        String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");

        search.setPageNo(pageNo);
        search.setPageSize(pageSize);
        System.out.println(pageNo);
        System.out.println(pageSize);
        search.setSearchDiv(searchDiv);
        search.setSearchWord(searchWord);
        System.out.println(searchDiv);
        System.out.println(searchWord);
        try {
        List<BoardVO> list = boardService.doRetrieveAnnouncementBoard(search);
            model.addAttribute("boardVO", boardVO);
            model.addAttribute("list", list);
            model.addAttribute("userVO", userVO);
        //총 글수
        int totalCnt = 0;
        if (null != list && list.size() > 0) {
            BoardVO vo = list.get(0);
            totalCnt = vo.getTotalCnt();
            System.out.println(totalCnt);
        }

        model.addAttribute("totalCnt", totalCnt);
        model.addAttribute("search", search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    // 게시글 수정 처리
    @RequestMapping(value = "/updateBoard.do", method = RequestMethod.POST)
    public String updateBoard(@ModelAttribute BoardVO boardVO) {
        int result = boardService.updateBoard(boardVO);

        if (result > 0) {
            // 수정 성공 시, 수정된 게시글 보기
            return "redirect:boardDetail.do?article_no=" + boardVO.getArticle_no();
        } else {
            // 실패 시, 오류 페이지 또는 다시 수정 페이지로 이동
            return "board/error";
        }
    }

    // 게시글 삭제 처리
    @RequestMapping(value = "/deleteBoard.do", method = RequestMethod.POST)
    public String deleteBoard(@RequestParam("article_no") int articleNo) {
        int result = boardService.deleteBoard(articleNo);

        if (result > 0) {
            // 삭제 성공 시, 게시글 목록 페이지로 리다이렉트
            return "redirect:announcement.do";
        } else {
            // 실패 시, 오류 페이지로 이동
            return "board/error";
        }
    }

    @RequestMapping("/boardDetail.do")
    public String getAnnouncementDetail(@RequestParam("article_no") int articleNo, Model model, Model models,HttpSession session) throws SQLException {
        // DB에서 해당 article_no에 해당하는 게시글을 조회
        BoardVO board = boardService.getBoardByArticleNo(articleNo);
        // 모델에 게시글 정보 전달
        model.addAttribute("board", board);

        UserVO user = (UserVO)session.getAttribute("user");
        UserVO outVO = userService.doSelectOne(user);
        models.addAttribute("vo", outVO);
        return "board/boardDetail";  // JSP 페이지로 이동
    }

    @GetMapping("/announcementwrite.do")
    public String announcementWritePage(Model model, HttpSession httpSession, HttpServletRequest req) throws SQLException {
        UserVO user = (UserVO)httpSession.getAttribute("user");
        UserVO outVO = userService.doSelectOne(user);
        model.addAttribute("vo", outVO);
        return "board/announcement/announcementBoardWrite";
    }

    @PostMapping("/announcementboard.do")
    public String announcementBoard(@RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("author") String author) {
        BoardVO boardVO = new BoardVO();
        boardVO.setArticle_user_id(author);
        boardVO.setArticle_title(title);
        boardVO.setArticle_contents(content);

        try {
            int result = boardService.doWriteAnnouncementBoard(boardVO);
            if (result > 0) {
                return "redirect:/board/announcement.do"; 
            } else {
                return "board/announcementBoardWrite";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "게시글 작성에 실패 했습니다"; 
        }
    }
    
    @GetMapping("/free.do")
    public String FreeBoardPage(Model model,HttpServletRequest req)throws Exception {
    	BoardVO boardVO = new BoardVO();
        UserVO userVO = new UserVO();
        String view = "board/free/FreeBoardList";
        SearchVO search = new SearchVO();
        /*try {
       	 List<BoardVO> boardList = boardService.selectFreeBoardList();
            model.addAttribute("boardList", boardList); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
       	model.addAttribute("uservo", userVO);
		model.addAttribute("vo", boardVO);*/
        String pageNoString = StringUtil.nvl(req.getParameter("pageNo"), "1");
        String pageSizeString = StringUtil.nvl(req.getParameter("pageSize"), "10");

        int pageNo = Integer.parseInt(pageNoString);
        int pageSize = Integer.parseInt(pageSizeString);
        //데이터가 null이면, ""
        String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
        String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");

        search.setPageNo(pageNo);
        search.setPageSize(pageSize);
        System.out.println(pageNo);
        System.out.println(pageSize);
        search.setSearchDiv(searchDiv);
        search.setSearchWord(searchWord);
        System.out.println(searchDiv);
        System.out.println(searchWord);
        try {
            List<BoardVO> list = boardService.doRetrieveFreeBoard(search);
            model.addAttribute("boardVO", boardVO);
            model.addAttribute("list", list);
            model.addAttribute("userVO", userVO);
            //총 글수
            int totalCnt = 0;
            if (null != list && list.size() > 0) {
                BoardVO vo = list.get(0);
                totalCnt = vo.getTotalCnt();
                System.out.println(totalCnt);
            }

            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("search", search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @GetMapping("/freewrite.do")
    public String showWritePage(Model model, HttpSession httpSession, HttpServletRequest req) throws SQLException {
        UserVO user = (UserVO)httpSession.getAttribute("user");
        UserVO outVO = userService.doSelectOne(user);
        model.addAttribute("vo", outVO);
        return "board/free/FreeBoardWrite";
    }

    @PostMapping("/freeboard.do")
    public String freeBoard(@RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("author") String author) {
        BoardVO boardVO = new BoardVO();
        boardVO.setArticle_user_id(author);
        boardVO.setArticle_title(title);
        boardVO.setArticle_contents(content);

        try {
            int result = boardService.doWriteFreeBoard(boardVO);

            if (result > 0) {
                return "redirect:/board/announcement.do";
            } else {
                return "board/announcementBoardWrite";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "게시글 작성에 실패 했습니다"; 
        }
    }

    @GetMapping("/departmentwrite.do")
    public String showDepartmentWritePage(Model model, HttpSession httpSession, HttpServletRequest req) throws SQLException {
        UserVO user = (UserVO)httpSession.getAttribute("user");
        UserVO outVO = userService.doSelectOne(user);
        model.addAttribute("vo", outVO);
    	return "board/department/DepartmentBoardWrite";
    }
    
    @PostMapping("/departmentboard.do")
    public String submitBoard(@RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("author") String author,
                              @RequestParam("department") int department) {
        BoardVO boardVO = new BoardVO();
        boardVO.setArticle_user_id(author);
        boardVO.setArticle_title(title);
        boardVO.setArticle_contents(content);
        boardVO.setArticle_board_div(department);

        try {
            int result = boardService.doWriteDepartmentBoard(boardVO);

            if (result > 0) {
                return "redirect:/board/announcement.do"; 
            } else {
                return "board/DepartmentBoardWrite";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "게시글 작성에 실패 했습니다"; 
        }
    }

    @GetMapping("/development.do")
    public String DevelopmentBoardPage(Model model,HttpServletRequest req) throws Exception {
    	BoardVO boardVO = new BoardVO();
        UserVO userVO = new UserVO();
    	String view = "board/department/DevelopmentBoardList";
        SearchVO search = new SearchVO();
    	/* try {
        	 List<BoardVO> boardList = boardService.selectDevelopmentBoardList();
             model.addAttribute("boardList", boardList);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        	
		model.addAttribute("vo", boardVO);*/
        String pageNoString = StringUtil.nvl(req.getParameter("pageNo"), "1");
        String pageSizeString = StringUtil.nvl(req.getParameter("pageSize"), "10");

        int pageNo = Integer.parseInt(pageNoString);
        int pageSize = Integer.parseInt(pageSizeString);

        //데이터가 null이면, ""
        String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
        String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");

        search.setPageNo(pageNo);
        search.setPageSize(pageSize);
        System.out.println(pageNo);
        System.out.println(pageSize);
        search.setSearchDiv(searchDiv);
        search.setSearchWord(searchWord);
        System.out.println(searchDiv);
        System.out.println(searchWord);
        try {
            List<BoardVO> list = boardService.doRetrieveDevelopmentBoard(search);
            model.addAttribute("boardVO", boardVO);
            model.addAttribute("list", list);
            model.addAttribute("userVO", userVO);
            //총 글수
            int totalCnt = 0;
            if (null != list && list.size() > 0) {
                BoardVO vo = list.get(0);
                totalCnt = vo.getTotalCnt();
                System.out.println(totalCnt);
            }

            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("search", search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    
    @GetMapping("/hr.do")
    public String HRBoardPage(Model model,HttpServletRequest req) throws Exception {
        BoardVO boardVO = new BoardVO();
        UserVO userVO = new UserVO();
        String view = "board/department/HRBoardList";
        SearchVO search = new SearchVO();
    	/* try {
        	 List<BoardVO> boardList = boardService.selectDevelopmentBoardList();
             model.addAttribute("boardList", boardList);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		model.addAttribute("vo", boardVO);*/
        String pageNoString = StringUtil.nvl(req.getParameter("pageNo"), "1");
        String pageSizeString = StringUtil.nvl(req.getParameter("pageSize"), "10");

        int pageNo = Integer.parseInt(pageNoString);
        int pageSize = Integer.parseInt(pageSizeString);

        //데이터가 null이면, ""
        String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
        String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");

        search.setPageNo(pageNo);
        search.setPageSize(pageSize);
        System.out.println(pageNo);
        System.out.println(pageSize);
        search.setSearchDiv(searchDiv);
        search.setSearchWord(searchWord);
        System.out.println(searchDiv);
        System.out.println(searchWord);
        try {
            List<BoardVO> list = boardService.doRetrieveHRBoard(search);
            model.addAttribute("boardVO", boardVO);
            model.addAttribute("list", list);
            model.addAttribute("userVO", userVO);
            //총 글수
            int totalCnt = 0;
            if (null != list && list.size() > 0) {
                BoardVO vo = list.get(0);
                totalCnt = vo.getTotalCnt();
                System.out.println(totalCnt);
            }

            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("search", search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @GetMapping("/marketing.do")
    public String DepartmentBoardPage(Model model,HttpServletRequest req) {
        BoardVO boardVO = new BoardVO();
        UserVO userVO = new UserVO();
    	String view = "board/department/MarketingBoardList";
        SearchVO search = new SearchVO();
//    	 try {
//        	 List<BoardVO> boardList = boardService.selectMarketingBoardList();
//             model.addAttribute("boardList", boardList);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		model.addAttribute("vo", boardVO);
        String pageNoString = StringUtil.nvl(req.getParameter("pageNo"), "1");
        String pageSizeString = StringUtil.nvl(req.getParameter("pageSize"), "10");

        int pageNo = Integer.parseInt(pageNoString);
        int pageSize = Integer.parseInt(pageSizeString);

        //데이터가 null이면, ""
        String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"), "");
        String searchWord = StringUtil.nvl(req.getParameter("searchWord"), "");

        search.setPageNo(pageNo);
        search.setPageSize(pageSize);
        System.out.println(pageNo);
        System.out.println(pageSize);
        search.setSearchDiv(searchDiv);
        search.setSearchWord(searchWord);
        System.out.println(searchDiv);
        System.out.println(searchWord);
        try {
            List<BoardVO> list = boardService.doRetrieveMarketingBoard(search);
            model.addAttribute("boardVO", boardVO);
            model.addAttribute("list", list);
            model.addAttribute("userVO", userVO);
            //총 글수
            int totalCnt = 0;
            if (null != list && list.size() > 0) {
                BoardVO vo = list.get(0);
                totalCnt = vo.getTotalCnt();
                System.out.println(totalCnt);
            }

            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("search", search);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return view;
    }
}
