package com.pcwk.ehr.approval.domain;

import com.pcwk.ehr.cmn.DTO;

public class ApprovalVO extends DTO {

	private int approval_doc_no; // 결재건 번호
	private int approval_user_id; // 상신자
	private String approval_doc_title; // 제목
	private String approval_doc_reg_date; // 상신일 
	private String approval_doc_approved_date; // 결재일
	private String approval_doc_closing_date; // 마감일
	private int approval_status; // 결재상태
	private String approval_contents; //내용
	
	public ApprovalVO() {

	}
	
	public ApprovalVO(int approval_doc_no, int approval_user_id, String approval_doc_title,
			String approval_doc_reg_date, String approval_doc_approved_date, String approval_doc_closing_date,
			int approval_status, String approval_contents) {
		super();
		this.approval_doc_no = approval_doc_no;
		this.approval_user_id = approval_user_id;
		this.approval_doc_title = approval_doc_title;
		this.approval_doc_reg_date = approval_doc_reg_date;
		this.approval_doc_approved_date = approval_doc_approved_date;
		this.approval_doc_closing_date = approval_doc_closing_date;
		this.approval_status = approval_status;
		this.approval_contents = approval_contents;
	}
	public int getApproval_doc_no() {
		
		return approval_doc_no;
	}
	
	public void setApproval_doc_no(int approval_doc_no) {
		
		this.approval_doc_no = approval_doc_no;
	}
	
	public int getApproval_user_id() {
		
		return approval_user_id;
	}
	
	public void setApproval_user_id(int approval_user_id) {
		
		this.approval_user_id = approval_user_id;
	}
	
	public String getApproval_doc_title() {
		return approval_doc_title;
	}
	
	public void setApproval_doc_title(String approval_doc_title) {
		
		this.approval_doc_title = approval_doc_title;
	}
	
	public String getApproval_doc_reg_date() {
		
		return approval_doc_reg_date;
	}
	
	public void setApproval_doc_reg_date(String approval_doc_reg_date) {
		
		this.approval_doc_reg_date = approval_doc_reg_date;
	}
	
	public String getApproval_doc_approved_date() {
		
		return approval_doc_approved_date;
	}
	
	public void setApproval_doc_approved_date(String approval_doc_approved_date) {
		
		this.approval_doc_approved_date = approval_doc_approved_date;
	}
	
	public String getApproval_doc_closing_date() {
		
		return approval_doc_closing_date;
	}
	
	public void setApproval_doc_closing_date(String approval_doc_closing_date) {
		
		this.approval_doc_closing_date = approval_doc_closing_date;
	}
	
	public int getApproval_status() {
		
		return approval_status;
	}
	
	public void setApproval_status(int approval_status) {
		
		this.approval_status = approval_status;
	}
	
	public String getApproval_contents() {
		return approval_contents;
	}
	
	public void setApproval_contents(String approval_contents) {
		
		this.approval_contents = approval_contents;
	}

	@Override
	public String toString() {
		return "ApprovalVO [approval_doc_no=" + approval_doc_no + ", approval_user_id=" + approval_user_id
				+ ", approval_doc_title=" + approval_doc_title + ", approval_doc_reg_date=" + approval_doc_reg_date
				+ ", approval_doc_approved_date=" + approval_doc_approved_date + ", approval_doc_closing_date="
				+ approval_doc_closing_date + ", approval_status=" + approval_status + ", approval_contents="
				+ approval_contents + "]";
	}
}