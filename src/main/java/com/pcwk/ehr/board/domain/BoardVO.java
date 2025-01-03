package com.pcwk.ehr.board.domain;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserVO;

public class BoardVO extends DTO {
    private int article_no;
    private String article_user_id;
    private String article_title;
    private String article_contents;
    private int article_board_div;
    private int article_read_cnt;
    private String article_reg_date;
    private String article_mod_date;
    private UserVO userVO; // UserVO 객체 추가

    // Getter and Setter methods
    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    // 나머지 Getter and Setter
    public int getArticle_no() {
        return article_no;
    }

    public void setArticle_no(int article_no) {
        this.article_no = article_no;
    }

    public String getArticle_user_id() {
        return article_user_id;
    }

    public void setArticle_user_id(String article_user_id) {
        this.article_user_id = article_user_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_contents() {
        return article_contents;
    }

    public void setArticle_contents(String article_contents) {
        this.article_contents = article_contents;
    }

    public int getArticle_board_div() {
        return article_board_div;
    }

    public void setArticle_board_div(int article_board_div) {
        this.article_board_div = article_board_div;
    }

    public int getArticle_read_cnt() {
        return article_read_cnt;
    }

    public void setArticle_read_cnt(int article_read_cnt) {
        this.article_read_cnt = article_read_cnt;
    }

    public String getArticle_reg_date() {
        return article_reg_date;
    }

    public void setArticle_reg_date(String article_reg_date) {
        this.article_reg_date = article_reg_date;
    }

    public String getArticle_mod_date() {
        return article_mod_date;
    }

    public void setArticle_mod_date(String article_mod_date) {
        this.article_mod_date = article_mod_date;
    }
}
