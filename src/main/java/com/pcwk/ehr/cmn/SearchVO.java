package com.pcwk.ehr.cmn;

public class SearchVO extends DTO {

	// 검색 구분: 사용자ID(10), 이름(20), email(30)
	private String searchDiv;
	// 검색어:
	private String searchWord;

	public SearchVO() {

	}

	public SearchVO(String searchDiv, String searchWord) {
		super();
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
	}

	public String getSearchDiv() {
		return searchDiv;
	}

	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	@Override
	public String toString() {
		return "SearchVO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + ", getPageSize()=" + getPageSize()
				+ ", getPageNo()=" + getPageNo() + "]";
	}


	
}
